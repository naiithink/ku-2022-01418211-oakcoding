package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.*;
import ku.cs.oakcoding.app.models.picture.ProfileImage;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public class TestWriter {
    public static void main(String[] args) throws IOException {
        ProfileImage profileImage = new ProfileImage();
        ConsumerUser consumerUser = new ConsumerUser(Roles.CONSUMER,
                "INK",
                "panachai",
                        "ingfosbreak",
                "191245ing",
                ProfileImageState.CUSTOM);

        ConsumerUser consumerUser1 = new ConsumerUser(Roles.CONSUMER,
                "BOOM",
                "natthawit",
                "quarXZ",
                "191245ing",
                ProfileImageState.DEFAULT);

        UsersList usersList = new UsersList();
        usersList.addUser(consumerUser);
        usersList.addUser(consumerUser1);

        System.out.println(consumerUser.getDataFile());
        System.out.println(consumerUser1.getDataFile());
        DataSourceCSV<UsersList> dataSourceListCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER,"users.csv");
        dataSourceListCSV.writeData(usersList);
        UsersList newUsersList = dataSourceListCSV.readData();
        Set<User> hashSet = newUsersList.getUsers();
        for (User user : hashSet){
            System.out.println(user.toString());
            if (user instanceof ConsumerUser){
                System.out.println("yeah");
            }
        }

        DataSourceCSV<Path> pictureData = FactoryDataSourceCSV.getDataSource(DataFile.PICTURE,"ingfosbreak");
        DataSourceCSV<Path> pictureData1 = FactoryDataSourceCSV.getDataSource(DataFile.PICTURE,"quarXZ");
        System.out.println(pictureData.readData());
        System.out.println(pictureData1.readData());

//        String path = "src/main/resources/images/building.jpg";
//        Path path1 = Paths.get(path);
//        DataSourceCSV dataSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.PICTURE,"ingfosbreak");
//        dataSourceCSV.writeData(path1);






    }
}
