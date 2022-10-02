package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceListCSV;
import ku.cs.oakcoding.app.services.data_source.CSV.PictureSourceCSV;

public class FactoryDataSourceCSV {


    public FactoryDataSourceCSV(){}

    public static DataSourceCSV getDataSource(DataFile file){
        DataSourceCSV dataSourceCSV = null;

        switch(file){
            case User ->{
                dataSourceCSV = new DataSourceListCSV("data","user.csv");
            }
            case Complaint ->{
                dataSourceCSV = new DataSourceListCSV("data","complaint.csv");
            }
            case Picture -> {
                dataSourceCSV = new PictureSourceCSV("picture");
            }
        }


        return dataSourceCSV;

    }
}
