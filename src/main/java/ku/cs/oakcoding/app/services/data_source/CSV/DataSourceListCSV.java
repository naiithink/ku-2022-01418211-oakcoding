package ku.cs.oakcoding.app.services.data_source.csv;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.services.DataBase;

public class DataSourceListCSV implements DataSourceCSV<DataList> {
    private String directoryName;
    private String fileName;

    public DataSourceListCSV(String directoryName, String fileName) {
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
    public DataList readData(){
        DataList users = new DataList();
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
                String[] dataTrim = trimData(data);
                String key = DataBase.getKey(dataTrim);
                User newObj = (User) DataBase.readData(dataTrim);
                users.addUserMap(key,newObj);
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
    public void writeData(DataList usersMap){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);

            for (Map.Entry<String, User> entry : usersMap.getUsersMap().entrySet()){
                String line = DataBase.writeData(entry.getValue());
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

    public String [] trimData(String [] data){
        String [] dataTrim = new String[data.length];

        for (int i = 0; i < data.length; i++){
            dataTrim[i] = data[i].substring(1, data[i].length() - 1);
        }


        return dataTrim;

    }

}
