package ku.cs.oakcoding.app.services.DataSource;

import ku.cs.oakcoding.app.models.User.Status;
import ku.cs.oakcoding.app.models.User.User;
import ku.cs.oakcoding.app.models.User.UserList;
import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;

import java.io.*;
import java.util.Map;

public class UserDataSourceCSV implements DataSourceCSV<UserList> {
    private String directoryName;
    private String fileName;

    public UserDataSourceCSV(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdir();
        }

        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public UserList readData(){
        UserList users = new UserList();
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);
            String line = "";
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                User newUser = new User(Status.valueOf(data[0].trim()),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim());

                users.addUserMap(data[1].trim(),newUser);
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
        return users;

    }

    @Override
    public void writeData(UserList usersMap){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);

            for (Map.Entry<String,User> entry : usersMap.getUsersMap().entrySet()){
                String line = entry.getValue().getBAN_STATUS() + ","
                        + entry.getValue().getUsername() + ","
                        + entry.getValue().getPassword() + ","
                        + entry.getValue().getFirstname() + ","
                        + entry.getValue().getLastname() + ","
                        + entry.getValue().getPicturePath();
                writer.append(line);
                writer.newLine();
            }

            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearData(){
        String filePath = directoryName + File.separator + fileName;
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

}

