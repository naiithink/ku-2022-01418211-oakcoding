package ku.cs.oakcoding.app.helpers.resources;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.security.NoSuchAlgorithmException;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.configurations.OakSpecialAppToken;
import ku.cs.oakcoding.app.helpers.configurations.OakSystemDefaults;

public final class OakTempResources {
    private OakTempResources() {}

    private static final String TIME_STAMP_PREFIX;

    private static final Path SYSTEM_DEFAULT_TEMP_DIR_PATH;

    private static final Path APP_DEFAULT_TEMP_DIR_PATH;

    static {
        TIME_STAMP_PREFIX = String.valueOf(System.currentTimeMillis());
        SYSTEM_DEFAULT_TEMP_DIR_PATH = Paths.get(OakSystemDefaults.JAVA_IO_TMPDIR.value());

        final String APP_DEFAULT_TEMP_DIR_PREFIX = new String(OakAppDefaults.DEVELOPER_NAME.value() + OakSpecialAppToken.AsChar.DIR_NAME_DELIMITER.value() + TIME_STAMP_PREFIX + OakSpecialAppToken.AsChar.DIR_NAME_DELIMITER.value());
        APP_DEFAULT_TEMP_DIR_PATH = Paths.get(SYSTEM_DEFAULT_TEMP_DIR_PATH.resolve(APP_DEFAULT_TEMP_DIR_PREFIX).toUri().getPath());
    }

    public static OakTempResource getOakTempResource(Path path) throws FileNotFoundException,
                                                                       NoSuchAlgorithmException {
        return new OakTempResource(path);
    }

    public static Path getSystemDefaultTempDirPath() {
        return SYSTEM_DEFAULT_TEMP_DIR_PATH;
    }

    public static Path getAppDefaultTempDirPath() {
        return APP_DEFAULT_TEMP_DIR_PATH;
    }

    public static boolean createNewAppTempDir(final String prefix,
                                              FileAttribute<?>... attrs) {
        return false;
    }

    public static void main(String[] args) {
        System.out.println(OakTempResources.getSystemDefaultTempDirPath());
        System.out.println(OakTempResources.getAppDefaultTempDirPath());
    }

}
