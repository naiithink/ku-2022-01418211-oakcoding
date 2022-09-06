package ku.cs.oakcoding.app.services.User;

import java.io.*;
import java.util.ArrayList;

import ku.cs.oakcoding.app.models.User.User;
import ku.cs.oakcoding.app.models.User.UserList;
import ku.cs.oakcoding.app.services.DataSourceCSV;


/**
 *
 * this class if for Read and Write SINGLE Item.
 *
 * */
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
    public boolean checkID(String username,String password,String typeCheck){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileReader reader = null;
        BufferedReader buffer = null;

        if (typeCheck.equals("Only")) {
            try {
                reader = new FileReader(file);
                buffer = new BufferedReader(reader);
                String line = "";
                while ((line = buffer.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(username)) {
                        return true;
                    }
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
            return false;
        }

        else if (typeCheck.equals("All")) {
            try {
                reader = new FileReader(file);
                buffer = new BufferedReader(reader);
                String line = "";
                while ((line = buffer.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equals(username) && data[1].equals(password)) {
                        return true;
                    }
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
            return false;
        }

        else {
            System.err.println("Wrong type of dataCheck");
            return false;
        }

    }

    @Override
    public User readThatData(String username, String password){
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
                if (data[0].equals(username) && data[1].equals(password)){
                    User user = new User(data[0].trim(),
                                         data[1].trim(),
                                         data[2].trim(),
                                         data[3].trim(),
                                         data[4].trim());
                    return user;
                }
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
    public void writeData(User userObject) {
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);


                String line = userObject.getUsername() + ","
                        + userObject.getPassword() + ","
                        + userObject.getFirstname() + ","
                        + userObject.getLastname() + ","
                        + userObject.getPicturePath();
                writer.append(line);
                writer.newLine();


            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

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
