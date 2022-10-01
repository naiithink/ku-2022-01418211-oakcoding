package ku.cs.oakcoding.app.helpers.resources;

import java.nio.file.Files;
import java.nio.file.Path;

public class DataSources {

    DataSource readData(Path path) {
        long lines = Files.lines(path).count();

    }
}
