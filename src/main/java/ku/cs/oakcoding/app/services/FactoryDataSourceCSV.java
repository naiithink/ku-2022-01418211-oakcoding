package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceListCSV;
import ku.cs.oakcoding.app.services.data_source.CSV.PictureSourceCSV;

public class FactoryDataSourceCSV {


    public FactoryDataSourceCSV(){}

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
