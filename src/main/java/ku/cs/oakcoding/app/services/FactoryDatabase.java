package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.services.data_source.DataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.DataSourceListCSV;
import ku.cs.oakcoding.app.services.data_source.PictureSourceCSV;

public class FactoryDatabase {


    public FactoryDatabase(){}

    public static DataSourceCSV getDataSource(Roles roles){
        DataSourceCSV dataSourceCSV = null;

        switch(roles){
            case USER, ADMIN, STAFF, COMPLAINT ->{
                dataSourceCSV = new DataSourceListCSV("data","user.csv");
            }
            case PICTURE -> {
                dataSourceCSV = new PictureSourceCSV("picture");
            }
        }


        return dataSourceCSV;

    }
}
