/**
 * @file FactoryDataSourceCSV.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.services.data_source.csv.*;

public class FactoryDataSourceCSV {

    public FactoryDataSourceCSV() {
    }

    /**
     * @todo Avoid using raw type DataSourceCSV; DataSourceCSV<T>
     */
    public static DataSourceCSV getDataSource(DataFile file,String fileName) {
        DataSourceCSV dataSourceCSV = null;

        switch (file) {
            case USER -> {
                dataSourceCSV = new DataSourceListCSV("data", fileName);
            }
            case USERPROFILE -> {
                dataSourceCSV = new UserSourceCSV(fileName);
            }
            case SUSPENDED -> {
                dataSourceCSV = new DataSourceListCSV("data", fileName);
            }
            case COMPLAINT -> {
                dataSourceCSV = new DataSourceListCSV("data", fileName);
            }
            case PICTURE -> {
                dataSourceCSV = new PictureRefactoring(fileName);
            }

        }

        return dataSourceCSV;

    }
}
