package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakResourcePrefix {
    private OakResourcePrefix() {}

    private static FileSystem jarFS;

    private static Path rootResourcePath;

    private static Path defaultPrefix;

    private static Path prefix;

    static {
        try {
            final URI uri = OakResourcePrefix.class.getClassLoader().getResource(OakAppDefaults.CONFIG_FILE.get("app.default.config.file")).toURI();

            if (uri.getScheme().equals("jar")) {
                Map<String, String> env = new ConcurrentHashMap<>();
                env.put("create", "true");

                jarFS = FileSystems.newFileSystem(uri, env);

                OakLogger.log(Level.INFO, "Running in JAR mode");
            }

            rootResourcePath = Paths.get(uri);
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Cannot create file system, got 'IOException'");
        } catch (URISyntaxException e) {
            OakLogger.log(Level.SEVERE, "Cannot create file system, got 'URISyntaxException'");
        }

        try (InputStream in = Files.newInputStream(rootResourcePath)) {
            Objects.nonNull(in);

            Properties assertProp = new Properties();

            assertProp.load(in);

            if (assertProp.get(OakAppDefaults.CONFIG_FILE.get("app.default.config.file.test.key")).equals(OakAppDefaults.CONFIG_FILE.get("app.default.config.file.test.value"))) {
                defaultPrefix = rootResourcePath.getParent();
                prefix = defaultPrefix;
            }
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Cannot get prefix path, got 'IOException'");

            System.exit(1);
        }
    }

    public static Path getPrefix() {
        return prefix;
    }

    public static void setPrefix(Path newPrefix) {
        if (newPrefix.equals(newPrefix) == false) {
            prefix = newPrefix;
        }
    }

    public static void restoreToDefaultPrefix() {
        prefix = defaultPrefix;
    }

    public static Path resolve(String other) {
        return prefix.resolve(other);
    }

    public static String getAbsolutePathString(Path path) {
        return path.toAbsolutePath().toString();
    }
}
