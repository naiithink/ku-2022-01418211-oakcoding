package ku.cs.oakcoding.app.services;

import java.io.*;
import java.nio.Buffer;
import ku.cs.oakcoding.app.models.User;

public class UserDataSourceCSV implements DataSourceCSV<User> {
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
    public void writeData(User userObject) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);


                String line = userObject.getFirstname() + ","
                        + userObject.getLastname() + ","
                        + userObject.getUsername() + ","
                        + userObject.getPassword() + ","
                        + userObject.getPicturePath();
                writer.append(line);
                writer.newLine();


            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
