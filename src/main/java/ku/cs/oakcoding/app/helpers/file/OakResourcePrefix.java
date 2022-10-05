package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakResourcePrefix {
    private OakResourcePrefix() {}

    private static Path defaultPrefix;

    private static Path prefix;

    static {
        try (InputStream in = OakResourcePrefix.class.getClassLoader().getResourceAsStream(OakAppDefaults.CONFIG_FILE.get("app.default.config.file"))) {
            Objects.nonNull(in);

            Properties assertProp = new Properties();

            assertProp.load(in);

            if (assertProp.get("index").equals("resource_prefix")) {
                defaultPrefix = Paths.get(OakResourcePrefix.class.getClassLoader().getResource(OakAppDefaults.CONFIG_FILE.get("app.default.config.file")).toURI()).getParent();
                prefix = defaultPrefix;
            }
        } catch (IOException | URISyntaxException e) {
            OakLogger.log(Level.SEVERE, "Cannot get prefix path");

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
