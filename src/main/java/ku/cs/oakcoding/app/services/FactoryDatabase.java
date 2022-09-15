package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
import ku.cs.oakcoding.app.services.DataSource.PictureSourceCSV;
import ku.cs.oakcoding.app.services.DataSource.UserDataSourceCSV;

public class FactoryDatabase {


    public FactoryDatabase(){}

    public static DataSourceCSV getDataSource(DataType DataSourceType){
        DataSourceCSV dataSourceCSV = null;

        switch(DataSourceType){
            case USER ->{
                dataSourceCSV = new UserDataSourceCSV("data","user.csv");
            }
            case ADMIN -> {

            }
            case STAFF -> {

            }
            case COMPLAINT -> {

            }
            case PICTURE -> {
                dataSourceCSV = new PictureSourceCSV("picture");
            }
            default -> {
                ;
            }
        }


        return dataSourceCSV;

    }
}
