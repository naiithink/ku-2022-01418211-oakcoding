/**
 * @file BanListCSV.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.models.UsersList;
import ku.cs.oakcoding.app.models.ban.Ban;
import ku.cs.oakcoding.app.models.ban.BanList;
import ku.cs.oakcoding.app.services.DataBase;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

/**
 * BanListCSV
 * 
 * @todo Verbose name 'directoryName' -> 'dirName'
 */
public class BanListCSV implements DataSourceCSV<BanList> {

    private final String dirName = "data";

    private String fileName;

    public BanListCSV(String fileName) {
        this.fileName = fileName;
        checkFileIsExisted(dirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + fileName,MakeFileType.FILE);
    }

    /**
     * @todo Use API?
     *       <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
     *       - Files::exists(Path, LinkOption...)
     *       - Files::createFile(Path, FileAttribute<?>...); FileAttribute<?> can be omitted
     */
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

    /**
     * @todo Use try-with-resources?
     *       <https://dev.java/learn/catching-and-handling-exceptions/#anchor_6>
     */
    @Override
    public BanList readData() {
        BanList suspendedUsers = new BanList();
        String filePath = dirName + File.separator + fileName;
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
                suspendedUsers.addBan( (Ban) DataBase.readData(dataTrim,DataFile.SUSPENDED));
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
        return suspendedUsers;

    }

    /**
     * @todo Use try-with-resources?
     */
    @Override
    public void writeData(BanList banMap) {
        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file, true);
            writer = new BufferedWriter(fileWriter);

            for (Ban entry : banMap.getUsersSet()) {
                String line = DataBase.writeData(entry,entry.getDataFile());
                writer.append(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @todo Use try-with-resources?
     */
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

    /**
     * @todo Assert null before trimming
     */
    public String[] trimData(String[] data) {
        String[] dataTrim = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            dataTrim[i] = data[i].substring(1, data[i].length() - 1);
        }

        return dataTrim;

    }

}
