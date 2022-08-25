package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.models.User;
public interface DataSourceCSV<T> {

    void writeData(T t);
}
