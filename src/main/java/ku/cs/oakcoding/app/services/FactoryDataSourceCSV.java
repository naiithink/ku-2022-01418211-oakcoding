package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceListCSV;
import ku.cs.oakcoding.app.services.data_source.csv.PictureSourceCSV;

public class FactoryDataSourceCSV {


    public FactoryDataSourceCSV(){}

    public static DataSourceCSV getDataSource(DataFile file){
        DataSourceCSV dataSourceCSV = null;

        switch(file){
            case USER ->{
                dataSourceCSV = new DataSourceListCSV("data","users.csv");
            }
            case SUSPENDED -> {
                dataSourceCSV = new DataSourceListCSV("data", "suspended.csv");
            }
            case COMPLAINT ->{
                dataSourceCSV = new DataSourceListCSV("data","complaints.csv");
            }
            case PICTURE -> {
                dataSourceCSV = new PictureSourceCSV("picture");
            }

        }


        return dataSourceCSV;

    }
}