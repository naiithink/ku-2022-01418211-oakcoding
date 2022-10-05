package ku.cs.oakcoding.app.services.data_source.csv;

public interface DataSourceCSV <T>{

    T readData();

    void writeData(T t);

    void clearData();

}
