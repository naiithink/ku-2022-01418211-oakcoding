package ku.cs.oakcoding.app.helpers.resource;

import java.nio.file.Path;

public final class Resource {

    private static final Path RESOURCE_PATH;

    static {
        RESOURCE_PATH = PrefixAnchor.getInternalResourcePrefixPath();
    }

    private Resource() {}

    public static String request(String resource) {
        // to be implemented
        return null;
    }
}
