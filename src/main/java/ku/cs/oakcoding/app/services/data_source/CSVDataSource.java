package ku.cs.oakcoding.app.services.data_source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public class CSVDataSource
        implements DataSource<CSVBase<CSV>> {

    private static final String CONTENT_NL_REPLACEMENT = "\\\\033\\[1E";

    private String tableName;

    private String primaryKey;

    private String resourceName;

    private Path path;

    public CSVDataSource(String tableName, String primaryKey, String resourceName, Path path) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.resourceName = resourceName;
        this.path = path;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public boolean writeAll(CSVBase<CSV> data) {
        try (BufferedWriter out = Files.newBufferedWriter(path)) {

            out.write(data.toCSV(true, '"'));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to write to data source");
            return false;
        }

        return true;
    }

    @Override
    public CSV readAll() {
        if (Objects.isNull(this.path)) {
            OakLogger.log(Level.SEVERE, "Path is null");
            return null;
        } else if (pathExists(this.path) == false) {
            OakLogger.log(Level.SEVERE, "Attempting to read non existing file, null is returned");
            return null;
        }

        CSV res = null;
        String[] header = new String[] {};

        StringBuffer line = null;

        try (BufferedReader in = Files.newBufferedReader(this.path, StandardCharsets.UTF_8)) {

            if (in.ready()) {
                line = new StringBuffer(in.readLine());

                if (line.isEmpty()) {
                    OakLogger.log(Level.SEVERE, "Attempting to read empty file, null is returned");
                    return null;
                }

                header = line.toString().split(String.valueOf(CSV.getDelimiter()));

                for (int i = 0, lim = header.length; i < lim; ++i)
                    if (header[i].charAt(0) == '"' && header[i].charAt(header[i].length() - 1) == '"')
                        header[i] = header[i].substring(1, header[i].length() - 1);
            }

            res = new CSV(tableName, header, primaryKey, CONTENT_NL_REPLACEMENT);

            while (in.ready()) {
                line = new StringBuffer(in.readLine());

                if (line.isEmpty())
                    continue;

                String[] fields = line.toString().split(String.valueOf(CSV.getDelimiter()));

                for (int i = 0, lim = fields.length; i < lim; ++i)
                    if (fields[i].charAt(0) == '"' && fields[i].charAt(fields[i].length() - 1) == '"')
                        fields[i] = fields[i].substring(1, fields[i].length() - 1);

                res.addRecord(fields);
            }
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to read resource");
            return null;
        }

        return res;
    }
}
