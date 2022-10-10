package ku.cs.oakcoding.app.services.data_source.csv;

import javafx.scene.chart.PieChart;
import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.services.DataBase;

import java.io.*;
import java.util.Map;

public class UserSourceCSV implements DataSourceCSV<User>{

    private final String dirName = "data";
    private final String subDirName = "users";
    private String fileFolder;

    private final String fileName = "info.csv";

    public UserSourceCSV(String fileFolder) {
        this.fileFolder = fileFolder;
        checkFileIsExisted(dirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName + File.separator + fileFolder,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName,MakeFileType.FILE);

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
    public User readData() {
        String filePath = dirName + File.separator + fileFolder + File.separator + fileName;
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
                User user = (User) DataBase.readData(dataTrim);
                return user;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

            try {
                buffer.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    @Override
    public void writeData(User user) throws IOException {
        String filePath = dirName + File.separator + subDirName + File.separator + fileFolder + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file);
            writer = new BufferedWriter(fileWriter);

            String line = DataBase.writeData(user, user.getFileCallBack());
            writer.append(line);
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearData() {
        String filePath = dirName + File.separator + fileFolder + File.separator + fileName;
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
