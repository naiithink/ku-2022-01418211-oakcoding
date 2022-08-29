package ku.cs.oakcoding.app.helpers.file;

import java.nio.file.FileSystems;

public record OakDefaultFileSystemRecords() {

    static final String DEFAULT_SEPARATOR;

    static {
        DEFAULT_SEPARATOR = FileSystems.getDefault().getSeparator();
    }
}
