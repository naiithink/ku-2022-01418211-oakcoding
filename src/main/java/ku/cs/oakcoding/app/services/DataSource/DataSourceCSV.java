package ku.cs.oakcoding.app.services.DataSource;

public interface DataSourceCSV <T>{

    T readData();

    void writeData(T t);

    void clearData();

}