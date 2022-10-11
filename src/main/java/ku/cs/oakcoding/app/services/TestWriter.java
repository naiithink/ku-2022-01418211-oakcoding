package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.ConsumerUser;
import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.StaffUser;
import ku.cs.oakcoding.app.models.picture.ProfileImage;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

import java.io.IOException;

public class TestWriter {
    public static void main(String[] args) throws IOException {
        ProfileImage profileImage = new ProfileImage();
        ConsumerUser consumerUser = new ConsumerUser(Roles.CONSUMER,
                "INK",
                "panachai",
                        "ingfosbreak",
                "191245ing",
                profileImage);

        StaffUser consumerUser1 = new StaffUser(Roles.STAFF,
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




    }
}
