package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class OakResourceFiles {

    private OakResourceFiles() {}

    private static final String STRING_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT;

    private static final Path PATH_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT;

    private static final String STRING_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO;

    private static final Path PATH_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO;

    static {
        STRING_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT = System.getProperty("user.dir");
        PATH_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT = Paths.get(STRING_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT);
        STRING_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO = OakResourceFiles.class.getClassLoader().getResource("config.properties").getPath();
        PATH_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO = Paths.get(STRING_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO);
    }

    public OakResourceFile newOakResourceFile(String digestAlgorithm,
                                              String relativePathString) {
        String absolutePathString = STRING_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT + relativePathString;
        (digestAlgorithm, absolutePathString);
    }

    public static String getExternalRootPrefixString() {
        return STRING_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT;
    }

    public static Path getExternalRootPrefixPath() {
        return PATH_EXTERNAL_ABSOLUTE_PREFIX_TO_PROJECT_ROOT;
    }

    public static Path getInternalResourcePrefixPath() {
        return PATH_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO;
    }

    public static String getInternalResourcePrefixString() {
        return STRING_INTERNAL_ABSOLUTE_PREFIX_TO_RESOURCE_REPO;
    }

    /**
     * @note test
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(OakResourceFiles.getExternalRootPrefixString());
        System.out.println(OakResourceFiles.getInternalResourcePrefixPath());

        try {
            Path path = OakResourceFiles.getExternalRootPrefixPath();
            Stream<Path> list = Files.walk(path, 1);
            Iterator<Path> pathIter = list.iterator();

            while (pathIter.hasNext()) {
                System.out.println(pathIter.next());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("---");

        try {
            Path path = OakResourceFiles.getInternalResourcePrefixPath();
            Stream<Path> list = Files.walk(path, 1);
            Iterator<Path> pathIter = list.iterator();

            while (pathIter.hasNext()) {
                System.out.println(pathIter.next());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
