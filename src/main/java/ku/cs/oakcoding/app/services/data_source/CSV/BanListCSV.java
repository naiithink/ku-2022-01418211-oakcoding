package ku.cs.oakcoding.app.services.data_source.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import ku.cs.oakcoding.app.models.ban.Ban;
import ku.cs.oakcoding.app.models.ban.BanList;
import ku.cs.oakcoding.app.services.DataBase;

public class BanListCSV implements DataSourceCSV<BanList>{
    private String directoryName;
    private String fileName;

    public BanListCSV(String directoryName, String fileName) {
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
    public BanList readData(){
        BanList banList = new BanList();
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
                String key = dataTrim[0];
                Ban newBan = new Ban(dataTrim[0],dataTrim[1]);
                banList.addBanMap(key,newBan);

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
        return banList;

    }

    @Override
    public void writeData(BanList banMap){
        String filePath = directoryName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file,true);
            writer = new BufferedWriter(fileWriter);

            for (Map.Entry<String, Ban> entry : banMap.getUsersMap().entrySet()){
                String line = DataBase.writeData(entry);
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
