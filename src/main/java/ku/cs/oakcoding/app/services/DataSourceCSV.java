package ku.cs.oakcoding.app.services;

import java.util.ArrayList;

public interface DataSourceCSV<T> {
    boolean checkID(String username,String password, String typeCheck);
    T readThatData(String username,String password);
    void writeData(T t);

    void clearData();
}
