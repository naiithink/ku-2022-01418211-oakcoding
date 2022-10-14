/**
 * @file DataSourceListCSV.java
 * <p>
 * Reviews:
 * - Naming
 * 1. (CASE) naiithink, 2022-10-05
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
import ku.cs.oakcoding.app.models.users.UserInfo;
import ku.cs.oakcoding.app.models.users.UsersList;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UsersMap;
import ku.cs.oakcoding.app.services.DataBase;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;

/**
 * DataSourceListCSV
 *
 * @todo Verbose name 'directoryName' -> 'dirName'
 */
public class UsersListCSV implements DataSourceCSV<UsersList> {

    private final String dirName = "data";

    private String fileName;

    public UsersListCSV(String fileName) {
        this.fileName = fileName;
        checkFileIsExisted(dirName, MakeFileType.DIRECTORY);
        checkFileIsExisted(dirName + File.separator + fileName, MakeFileType.FILE);
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
    public UsersList readData() {
        UsersList users = new UsersList();

        try {

                UsersMap usersMap = (UsersMap) FactoryDataSourceCSV.getDataSource(DataFile.USER_INFO,"users.csv").readData();
                for (Map.Entry<String, UserInfo> entry : usersMap.getUsersMap().entrySet()) {
                    DataSourceCSV<User> userSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, entry.getKey());
                    users.addUser(userSourceCSV.readData());
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return users;

    }

    /**
     * @todo Use try-with-resources?
     */
    @Override
    public void writeData(UsersList usersSet) {

        try {

            UsersMap usersMap = new UsersMap();
            for (User entry : usersSet.getUsers()) {
                UserInfo userInfo = new UserInfo(entry.getUserName(),entry.getRole(), entry.getRegisteredTime());
                usersMap.addUserMap(entry.getUserName(), userInfo);

                DataSourceCSV userProfileCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, entry.getUserName());
                userProfileCSV.writeData(entry);
            }

            DataSourceCSV userInfoCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER_INFO,"users.csv");
            userInfoCSV.writeData(usersMap);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @todo Use try-with-resources?
     */
    @Override
    public void clearData() { // fix later
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
