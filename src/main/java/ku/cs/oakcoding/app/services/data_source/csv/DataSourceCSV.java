/**
 * @file DataSourceCSV.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.csv;

public interface DataSourceCSV<T> {

    T readData();

    void writeData(T t);

    void clearData();
}
