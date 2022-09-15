/**
 * @file OakSystemDefaults.java
 * @version 1.0.0-complete
 */

package ku.cs.oakcoding.app.helpers.configurations;

/**
 * Obtaining system's default properties.
 * 
 * Getting a value: OakSystemDefaults.PROPERTY_NAME.value()
 *
 * @see <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/System.html#getProperties()>
 */
public enum OakSystemDefaults {

    JAVA_VERSION        (Properties.JAVA_VERSION_VALUE),
    JAVA_HOME           (Properties.JAVA_HOME_VALUE),
    JAVA_CLASS_PATH     (Properties.JAVA_CLASS_PATH_VALUE),
    JAVA_LIBRARY_PATH   (Properties.JAVA_LIBRARY_PATH_VALUE),
    JAVA_IO_TMPDIR      (Properties.JAVA_IO_TMPDIR_VALUE),
    OS_NAME             (Properties.OS_NAME_VALUE),
    OS_ARCH             (Properties.OS_ARCH_VALUE),
    OS_VERSION          (Properties.OS_VERSION_VALUE),
    FILE_SEPARATOR      (Properties.FILE_SEPARATOR_VALUE),
    PATH_SEPARATOR      (Properties.PATH_SEPARATOR_VALUE),
    LINE_SEPARATOR      (Properties.LINE_SEPARATOR_VALUE),
    USER_NAME           (Properties.USER_NAME_VALUE),
    USER_HOME           (Properties.USER_HOME_VALUE),
    USER_DIR            (Properties.USER_DIR_VALUE);

    private final String value;

    private OakSystemDefaults(String value) {
        this.value = value;
    }

    /**
     * Gets an available system's default property value.
     * 
     * @return  the system's default property value available in this type
     */
    public String value() {
        return value;
    }

    private static class Properties {
        private static final String JAVA_VERSION_VALUE       = System.getProperty("java.version");
        private static final String JAVA_HOME_VALUE          = System.getProperty("java.home");
        private static final String JAVA_CLASS_PATH_VALUE    = System.getProperty("java.class.path");
        private static final String JAVA_LIBRARY_PATH_VALUE  = System.getProperty("java.library.path");
        private static final String JAVA_IO_TMPDIR_VALUE     = System.getProperty("java.io.tmpdir");
        private static final String OS_NAME_VALUE            = System.getProperty("os.name");
        private static final String OS_ARCH_VALUE            = System.getProperty("os.arch");
        private static final String OS_VERSION_VALUE         = System.getProperty("os.version");
        private static final String FILE_SEPARATOR_VALUE     = System.getProperty("file.separator");
        private static final String PATH_SEPARATOR_VALUE     = System.getProperty("path.separator");
        private static final String LINE_SEPARATOR_VALUE     = System.getProperty("line.separator");
        private static final String USER_NAME_VALUE          = System.getProperty("user.name");
        private static final String USER_HOME_VALUE          = System.getProperty("user.home");
        private static final String USER_DIR_VALUE           = System.getProperty("user.dir");
    }
}
