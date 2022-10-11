/**
 * @file DataSourceListCSV.java
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

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.services.DataBase;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.helpers.hotspot.ModelCallBack;

/**
 * DataSourceListCSV
 * 
 * @todo Verbose name 'directoryName' -> 'dirName'
 */
public class DataSourceListCSV implements DataSourceCSV<DataList> {

    private String dirName;

    private String fileName;

    public DataSourceListCSV(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
        checkFileIsExisted(dirName,MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + fileName,MakeFileType.FILE);
    }

    /**
     * @todo Use API?
     *       <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html>
     *       - Files::exists(Path, LinkOption...)
     *       - Files::createFile(Path, FileAttribute<?>...); FileAttribute<?> can be omitted
     * 
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
     * @todo Why nesting those <?>::close statements?
     * @todo Use try-with-resources?
     *       <https://dev.java/learn/catching-and-handling-exceptions/#anchor_6>
     */
    @Override
    public DataList readData() {
        DataList users = new DataList();
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
                DataSourceCSV userSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.USERPROFILE,dataTrim[0]);
                users.addUser((User) userSourceCSV.readData());
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

    /**
     * @todo Use try-with-resources?
     */
    @Override
    public void writeData(DataList usersSet) {
        String filePath = dirName + File.separator + fileName;
        File file = new File(filePath);

        FileWriter fileWriter = null;
        BufferedWriter writer = null;

        try {
            fileWriter = new FileWriter(file, true);
            writer = new BufferedWriter(fileWriter);

            for (User entry : usersSet.getUsers()) {
                String line = DataBase.writeData(entry, ModelCallBack.USER);
                DataSourceCSV userSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.USERPROFILE, entry.getUserName());
                userSourceCSV.writeData(entry);
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
