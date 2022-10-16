package ku.cs.oakcoding.app.services.data_source;

import java.nio.file.Files;
import java.nio.file.Path;

public interface DataSource<T> {

    boolean writeAll(T data);

    T readAll();

    default boolean pathExists(Path path) {
        return Files.exists(path);
    }
}
