package ku.cs.oakcoding.app.helpers.resources;

import java.nio.file.FileAlreadyExistsException;

public class OakResourceAlreadyExistsException
        extends FileAlreadyExistsException {

    public OakResourceAlreadyExistsException(String file) {
        super(file);
    }

    public OakResourceAlreadyExistsException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
