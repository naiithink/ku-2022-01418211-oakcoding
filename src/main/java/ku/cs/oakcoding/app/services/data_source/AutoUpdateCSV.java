package ku.cs.oakcoding.app.services.data_source;

import java.io.Console;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AutoUpdateCSV implements CSVBase<CSV> {

    private CSVBase<CSV> data;

    private CSVDataSource dataSource;

    private Path path;

    public AutoUpdateCSV(String tableName, String primaryKey, String resourceName, Path path) throws FileNotFoundException {
        if (!Files.exists(path) || !Files.isRegularFile(path))
            throw new FileNotFoundException(path.toString());

        this.path = path;
        this.dataSource = new CSVDataSource(tableName, primaryKey, resourceName, path);
        this.data = this.dataSource.readAll();
    }

    public void startInteractiveShell() {
        Console console = System.console();

        String input = null;
        String[] inputArgs = null;

        while (true) {
            input = console.readLine("%s", ">>> ");
            inputArgs = input.split(" ");

            switch (inputArgs[0]) {
                case "exit":
                    break;

                case "add":
                    String[] record = new String[inputArgs.length - 1];

                    for (int i = 1, lim = inputArgs.length; i < lim; ++i)
                        record[i - 1] = inputArgs[i];

                    System.out.println(addRecord(record));
                    break;

                case "remove":
                    System.out.println(removeRecordWhere(inputArgs[1]));
                    break;

                case "print":
                    System.out.println(this.data);
                    break;

                case "printCSV":
                    System.out.println(this.data.toCSV(false, '\0'));
                    break;

                default:
                    continue;
            }
        }
    }

    @Override
    public int addRecord(String[] record) {
        int res = this.data.addRecord(record);
        this.dataSource.writeAll(this.data);

        return res;
    }

    @Override
    public <U extends List<String>> int addRecord(U record) {
        int res = this.data.addRecord(record);
        this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public int addRecords(String[][] records) {
        int res = this.data.addRecords(records);
        this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public <U extends List<List<String>>> int addRecords(U records) {
        int res = this.data.addRecords(records);
        this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public boolean removeRecordWhere(String primaryKeyEqualsTo) {
        boolean res;

        if (res = this.data.removeRecordWhere(primaryKeyEqualsTo))
            this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public int removeRecordWhere(String column, String equalsTo, int limit) {
        int res = this.data.removeRecordWhere(column, equalsTo, limit);
        this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public List<String> getRecordEntryList(String primaryKey) {
        return this.data.getRecordEntryList(primaryKey);
    }

    @Override
    public List<List<String>> getRecordsWhere(String column, String equalsTo, int limit) {
        return this.data.getRecordsWhere(column, equalsTo, limit);
    }

    @Override
    public String getDataWhere(String primaryKey, String column) {
        return this.data.getDataWhere(primaryKey, column);
    }

    @Override
    public boolean editRecord(String primaryKey, String column, String newValue, boolean overridePrimaryKey) {
        boolean res;

        if (res = this.data.editRecord(primaryKey, column, newValue, overridePrimaryKey))
            this.dataSource.writeAll(data);

        return res;
    }

    @Override
    public String toCSV(boolean upperCaseHeader, char enclosure) {
        return this.data.toCSV(upperCaseHeader, enclosure);
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
        AutoUpdateCSV data = new AutoUpdateCSV("test", "name", "test", Paths.get("/Users/naiithink/projects/Gen/oakcoding/data/users/naiithink/info.csv"));

        System.out.println(data);

        System.out.println(data.getDataWhere("naiithink", "FIRST_NAME"));
        // data.startInteractiveShell();
    }
}
