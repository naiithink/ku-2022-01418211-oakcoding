package ku.cs.oakcoding.app.helpers.file;

import java.nio.file.Path;

import ku.cs.oakcoding.app.helpers.resources.OakResourceAlreadyExistsException;

public abstract class OakFiles {

    public OakFile newOakFile(Path filePath) throws OakResourceAlreadyExistsException {
        return new OakFile(filePath);
    }
}
