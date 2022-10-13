package ku.cs.oakcoding.app.services.data_source.csv;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.users.UserInfo;
import ku.cs.oakcoding.app.models.users.UsersMap;
import ku.cs.oakcoding.app.services.DataBase;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class UserInfoCSV implements DataSourceCSV<UsersMap>{
    private final String dirName = "data";

    private String fileName;

    public UserInfoCSV(String fileName) {
        this.fileName = fileName;
        checkFileIsExisted(dirName, MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + fileName, MakeFileType.FILE);

    }

    private void checkFileIsExisted(String filePath, MakeFileType makeFileType) {

        if (makeFileType == MakeFileType.DIRECTORY) {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }

        }

        if (makeFileType == MakeFileType.FILE) {
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }


    @Override
    public UsersMap readData() {
        UsersMap usersMap = new UsersMap();
        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;
        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            if ((line = buffer.readLine()) != null) {
                String [] data = line.split(",");
                String [] dataTrim = trimData(data);
                UserInfo newUser = (UserInfo) DataBase.readData(dataTrim,DataFile.USER_INFO);
                usersMap.addUserMap(newUser.userName(), newUser);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                if (buffer != null) {
                    buffer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return usersMap;
    }

    @Override
    public void writeData(UsersMap usersMap) throws IOException {
        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            for (Map.Entry<String, UserInfo> entry : usersMap.getUsersMap().entrySet()){
                String line = DataBase.writeData(entry.getValue(),DataFile.USER_INFO);
                writer.append(line);
                writer.newLine();

            }
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearData() {
        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;
        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            String line = "";
            writer.append(line);
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String[] trimData(String[] data) {
        String[] dataTrim = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            dataTrim[i] = data[i].substring(1, data[i].length() - 1);
        }

        return dataTrim;

    }
}
