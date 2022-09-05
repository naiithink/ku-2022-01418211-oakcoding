/**
 * @file OakAppDefaults.java
 * @version 1.0.0-dev
 */

package ku.cs.oakcoding.app.helpers.configurations;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Default reference to OakCoding's application configuration entries.
 */
public enum OakAppDefaults {

    NAME                            ("app.name",                    "OakCoding"),
    DEVELOPER                       ("app.developer",               "ku.cs.oakcoding"),
    VERSION                         ("app.version",                 "NIL"),
    LOG_FILE_DIR                    ("app.resource.logFile.dir",    "logs"),
    USE_TEMP_DIR                    ("app.resource.useTmpDir",      "true"),
    USE_EXPERIMENTAL_FEATURES       ("app.useExperimentalFeature",  "false");

    private final String key;

    private final String value;

    public static ConcurrentHashMap<String, String> CONFIG_FILE;

    static {
        CONFIG_FILE = new ConcurrentHashMap<>();
        CONFIG_FILE.put("app.default.configFile", "config.properties");
    }

    private OakAppDefaults(String key,
                           String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

    public static String valueOfKey(String key) {
        for (OakAppDefaults e : OakAppDefaults.values()) {
            if (key.equals(e.key)) {
                return e.value;
            }
        }

        return null;
    }
}
