package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.*;
import ku.cs.oakcoding.app.models.picture.ProfileImage;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestWriter {
    public static void main(String[] args) throws IOException {
        ProfileImage profileImage = new ProfileImage();
        ConsumerUser consumerUser = new ConsumerUser(Roles.CONSUMER,
                "INK",
                "panachai",
                        "ingfosbreak",
                "191245ing",
                profileImage);

        ConsumerUser consumerUser1 = new ConsumerUser(Roles.CONSUMER,
                "BOOM",
                "natthawit",
                "quarXZ",
                "191245ing",
                profileImage);

        DataList dataList = new DataList();
        dataList.addUser(consumerUser);
        dataList.addUser(consumerUser1);

        System.out.println(consumerUser.getDataFile());
        System.out.println(consumerUser1.getDataFile());
        DataSourceCSV dataSourceListCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER,"users.csv");
        dataSourceListCSV.writeData(dataList);

        DataList newDataList = (DataList) dataSourceListCSV.readData();
        Set<User> hashSet = newDataList.getUsers();
        for (User user : hashSet){
            System.out.println(user.toString());
            if (user instanceof ConsumerUser){
                System.out.println("yeah");
            }
        }






    }
}
