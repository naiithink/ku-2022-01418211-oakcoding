package ku.cs.oakcoding.app.services.data_source;

import java.util.List;

public interface CSVBase<T> {

    int addRecord(String[] record);

    <U extends List<String>> int addRecord(U record);

    int addRecords(String[][] records);

    <U extends List<List<String>>> int addRecords(U records);

    boolean removeRecordWhere(String primaryKeyEqualsTo);

    int removeRecordWhere(String column, String equalsTo, int limit);

    List<String> getRecordEntryList(String primaryKey);

    List<List<String>> getRecordsWhere(String column, String equalsTo, int limit);

    String getDataWhere(String primaryKey, String column);

    boolean editRecord(String primaryKey, String column, String newValue, boolean overridePrimaryKey);

    String toCSV(boolean upperCaseHeader, char enclosure);
}
