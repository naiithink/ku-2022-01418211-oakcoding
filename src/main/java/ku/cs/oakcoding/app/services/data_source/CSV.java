package ku.cs.oakcoding.app.services.data_source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public class CSV
        implements CSVBase<CSV>,
                   TableBaseData {

    // Begin static members

    private static Logger logger = Logger.getLogger(CSV.class.getName());

    private static boolean allowLoggerConfig = true;

    private static final char DELIMITER = ',';

    public static char getDelimiter() {
        return DELIMITER;
    }

    public static void setLogger(Logger newLogger) {
        if (!allowLoggerConfig) {
            logger.log(Level.SEVERE, "Configuring Logger is not allowed at the time.");
            return;
        }

        logger = newLogger;
    }

    public static void addHandlerToLogger(Handler... handlers) {
        if (!allowLoggerConfig) {
            logger.log(Level.SEVERE, "Configuring Logger is not allowed at the time.");
            return;
        }

        for (Handler handler : handlers)
            logger.addHandler(handler);
    }

    // End static members

    private final String CONTENT_NL_REPLACEMENT = "\\\\n";

    private String tableName;

    private String primaryKey;

    private int primaryKeyIndex;

    private List<String> header;

    private final Map<String, List<String>> data;

    public <T extends List<String>> CSV(String tableName, T header, String primaryKey) {

        if (header.contains(primaryKey) == false) {
            System.err.println("the given header does not contain primaryKey");
            System.exit(1);
        }

        this.tableName = tableName;
        this.header = new ArrayList<>();
        this.data = new HashMap<>();

        int i = 0;
        for (String filDes : header) {
            if (primaryKey.equals(filDes)) {
                this.primaryKeyIndex = i;
                break;
            }

            i++;
        }

        for (String fieldDescriptor : header) {
            this.header.add(fieldDescriptor);
        }

        this.primaryKey = this.header.get(this.primaryKeyIndex);
        this.header = Collections.unmodifiableList(this.header);

        allowLoggerConfig = false;
    }

    public CSV(String tableName, String[] header, String primaryKey, String contentNLReplacement) {
        for (int i = 0, u = header.length; i < u; ++i) {
            if (header[i].equals(primaryKey)) {
                this.primaryKeyIndex = i;
                break;
            } else if (i == u) {
                System.err.println("the given header does not contain primaryKey");
                System.exit(1);
            }
        }

        this.tableName = tableName;
        this.header = new ArrayList<>();
        this.data = new HashMap<>();

        for (String column : header) {
            this.header.add(column);
        }

        this.header = Collections.unmodifiableList(this.header);

        allowLoggerConfig = false;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public void renameTable(String tableName) {
        setTableName(tableName);
    }

    @Override
    public String getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;

        for (String column : this.header) {
            if (column.equals(primaryKey)) {
                this.primaryKeyIndex = this.header.indexOf(column);
                break;
            }
        }
    }

    @Override
    public void changePrimaryKey(String primaryKey) {
        setPrimaryKey(primaryKey);
    }

    @Override
    public void changePrimaryKey(String oldPrimaryKey, String newPrimaryKey) {
        if (this.primaryKey.equals(oldPrimaryKey) == false) {
            System.err.println("Failed to check the current primary key");
            return;
        }

        setPrimaryKey(primaryKey);
    }

    @Override
    public boolean containsKey(String primaryKey) {
        return this.data.containsKey(primaryKey);
    }

    @Override
    public int getColumnIndex(String column) {
        return this.header.indexOf(column);
    }

    @Override
    public String[] getTableHeader() {
        return header.toArray(new String[] {});
    }

    @Override
    public int addRecord(String[] record) {
        if (Objects.isNull(record)) {
            System.err.println("record is null");
            return 0;
        } else if (record.length != header.size()) {
            System.err.println("record length does not match the header");
            return 0;
        }

        int n = 0;
        String recordKey = record[this.primaryKeyIndex];

        this.data.put(recordKey, new ArrayList<>());
        List<String> recordFields = this.data.get(recordKey);

        for (String field : record) {
            recordFields.add(field.replaceAll("\\n", this.CONTENT_NL_REPLACEMENT));
            n++;
        }

        return n;
    }

    @Override
    public <T extends List<String>> int addRecord(T record) {
        if (Objects.isNull(record)) {
            System.err.println("record is null");
            return 0;
        } else if (record.size() != header.size()) {
            System.err.println("record length does not match the header");
            return 0;
        }

        int n = 0;
        String recordKey = record.get(this.primaryKeyIndex);

        this.data.put(recordKey, new ArrayList<>());
        List<String> recordFields = this.data.get(recordKey);

        for (String field : record) {
            recordFields.add(field.replaceAll("\\n", this.CONTENT_NL_REPLACEMENT));
            n++;
        }

        return n;
    }

    @Override
    public int addRecords(String[][] records) {
        if (Objects.isNull(records)) {
            OakLogger.log(Level.SEVERE, "Attempting to write null records");
            return 0;
        }

        int res = 0;
        for (String[] record : records) {
            addRecord(record);
            res++;
        }

        return res;
    }

    @Override
    public <T extends List<List<String>>> int addRecords(T records) {
        if (Objects.isNull(records)) {
            OakLogger.log(Level.SEVERE, "Attempting to write null records");
            return 0;
        }

        int res = 0;
        for (List<String> record : records) {
            addRecord(record);
            res++;
        }

        return res;
    }

    @Override
    public boolean removeRecordWhere(String primaryKeyEqualsTo) {
        if (Objects.isNull(primaryKeyEqualsTo)) {
            System.err.println("Invalid argument");
            return false;
        } else if (this.data.containsKey(primaryKeyEqualsTo) == false) {
            System.err.println("Non existing record");
            return false;
        }

        this.data.remove(primaryKeyEqualsTo);

        return true;
    }

    @Override
    public int removeRecordWhere(String column, String equalsTo, int limit) {
        if (Objects.isNull(column) || Objects.isNull(equalsTo)) {
            System.err.println("Invalid argument");
            return 0;
        }

        int res = 0;
        int index = this.header.indexOf(column);
        Set<String> markedPrimaryKeys = new HashSet<>();

        Iterator<Entry<String, List<String>>> recordEntries = this.data.entrySet().iterator();

        while (recordEntries.hasNext()) {
            Entry<String, List<String>> record = recordEntries.next();

            if (this.data.get(record.getKey()).get(index).equals(equalsTo)
                || this.data.get(record.getKey()).get(index).equals(equalsTo.replaceAll(this.CONTENT_NL_REPLACEMENT, "\n"))) {

                markedPrimaryKeys.add(record.getKey());
                res++;
            }

            if (limit > 0 && res >= limit)
                break;
        }

        for (String primaryKey : markedPrimaryKeys) {
            this.data.remove(primaryKey);
        }

        return res;
    }

    @Override
    public List<String> getRecordEntryList(String primaryKey) {
        return this.data.get(primaryKey);
    }

    public String[] getRecordEntryArray(String primaryKey) {
        return (String[]) this.data.get(primaryKey).toArray();
    }

    @Override
    public List<List<String>> getRecordsWhere(String column, String equalsTo, int limit) {
        List<List<String>> res = new ArrayList<>();

        int index = this.header.indexOf(column);

        for (String key : this.data.keySet()) {
            if (this.data.get(key).get(index).equals(equalsTo)) {
                res.add(this.data.get(key));
            }
        }

        return res;
    }

    @Override
    public String getDataWhere(String primaryKey, String column) {
        if (this.data.containsKey(primaryKey) == false) {
            System.err.println("Key does not exist.");
            return null;
        }

        return this.data.get(primaryKey).get(getColumnIndex(column));
    }

    @Override
    public boolean editRecord(String primaryKey, String column, String newValue, boolean overridePrimaryKey) {
        boolean isUpperCased = false;

        if (this.data.containsKey(primaryKey) == false) {
            System.err.println("Record does not exist.");
            return false;
        } else if (getColumnIndex(column) == this.primaryKeyIndex && !overridePrimaryKey) {
            System.err.println("Attempting to edit primary key, check argument to allow overriding.");
            return false;
        } else if (this.header.contains(column) == false
                   && (isUpperCased = this.header.contains(column.toUpperCase())) == false) {

            OakLogger.log(Level.SEVERE, "Attempting to edit non existing column");
            return false;
        }

        this.data.get(primaryKey).set(this.header.indexOf(isUpperCased ? column.toUpperCase()
                                                                       : column), newValue);

        return true;
    }

    @Override
    public String toCSV(boolean upperCaseHeader, char enclosure) {
        StringBuffer resBuf = new StringBuffer();

        String terminal = this.header.get(this.header.size() - 1);
        for (String filDes : this.header) {
            if (upperCaseHeader)
                resBuf.append(enclosure + filDes.toUpperCase() + enclosure);
            else
                resBuf.append(enclosure + filDes + enclosure);

            if (filDes.equals(terminal))
                resBuf.append('\n');
            else
                resBuf.append(',');
        }

        int terminalIndex = this.header.size() - 1;
        for (String pm : this.data.keySet()) {
            int i = 0;
            for (String elem : this.data.get(pm)) {
                resBuf.append(enclosure + elem + enclosure);

                if (i == terminalIndex)
                    resBuf.append('\n');
                else
                    resBuf.append(',');

                i++;
            }
        }

        return resBuf.toString();
    }

    @Override
    public String toString() {
        StringBuffer resBuf = new StringBuffer();

        resBuf.append(this.tableName + "[PK=" + "'" + this.header.get(this.primaryKeyIndex) + "'] " + "{\n");
        resBuf.append("\t" + this.header.toString() + "\n");

        for (String pm : this.data.keySet()) {
            resBuf.append("\t" + this.data.get(pm).toString() + "\t\n");
        }

        resBuf.append("}");

        return resBuf.toString();
    }
}
