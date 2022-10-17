package ku.cs.oakcoding.app.services.data_source;

import java.util.Set;

public interface TableBaseData {

    String getTableName();

    void setTableName(String tableName);

    void renameTable(String tableName);

    Set<String> getPrimaryKeySet();

    String getPrimaryKey();

    void setPrimaryKey(String primaryKey);

    void changePrimaryKey(String primaryKey);

    void changePrimaryKey(String oldPrimaryKey, String newPrimaryKey);

    boolean containsKey(String primaryKey);

    int getColumnIndex(String column);

    String[] getTableHeader();
}
