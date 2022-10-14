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
                dataSourceCSV = new UsersListCSV(fileName);
            }
            case USER_PROFILE -> {
                dataSourceCSV = new UserProfileCSV(fileName);
            }
            case SUSPENDED -> {
                dataSourceCSV = new UsersListCSV(fileName);
            }
            case COMPLAINT -> {
                dataSourceCSV = new UsersListCSV(fileName);
            }
            case PICTURE -> {
                dataSourceCSV = new PictureSource(fileName);
            }

        }

        return dataSourceCSV;

    }
}
