package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.Constants.DataType;
import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
import ku.cs.oakcoding.app.services.DataSource.PictureSourceCSV;
import ku.cs.oakcoding.app.services.DataSource.DataSourceListCSV;

public class FactoryDatabase {


    public FactoryDatabase(){}

    public static DataSourceCSV getDataSource(DataType DataSourceType){
        DataSourceCSV dataSourceCSV = null;

        switch(DataSourceType){
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
