/**
 * @file OakAppDefaults.java
 * @version 1.0.0-dev
 */

package ku.cs.oakcoding.app.helpers.configurations;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;

/**
 * Default reference to OakCoding's application configuration entries.
 */
public enum OakAppDefaults {

    APP_NAME                        ("app.name",                    "OakCoding"),
    DEVELOPER                       ("app.developer",               "ku.cs.oakcoding"),
    VERSION                         ("app.version",                 "NIL"),
    APP_INSTALL_DIR                 ("app.dir.install",             "../../data"),
    APP_INSTALL_DIR_TEMPLATE        ("app.dir.data.default",        "default/data"),
    APP_DEFAULT_IMAGE_DIR           ("app.default.images",          "default/images"),
    APP_DEFAULT_PROFILE_IMAGE       ("app.users.default_profile",   "default-profile.png"),
    APP_DEFAULT_USER_DIR_TEMPLATE   ("app.users.default.dir",       "default/user"),
    APP_DEFAULT_USER_INFO_TEMPLATE  ("app.users.default.file.info", "default/user/info.csv"),
    APP_DEFAULT_ORG_DIR_TEMPLATE    ("app.orgs.default.dir",        "default/org"),
    APP_DEFAULT_ORG_INFO_TEMPLATE   ("app.orgs.default.file.info",  "default/org/info.csv"),
    APP_DEFAULT_DEP_DIR_TEMPLATE    ("app.deps.default.dir",        "default/dep"),
    APP_DEFAULT_DEP_INFO_TEMPLATE   ("app.deps.default.file.info",  "default/dep/info.csv"),
    APP_USER_INFO                   ("app.users.file.info",         "info.csv"),
    APP_USER_ENTRIES                ("app.users_entries.file",      "users.csv"),
    APP_SESSION_FILE                ("app.sessions.file",           "sessions.csv"),
    APP_USER_DIR                    ("app.users.dir",               "users"),
    APP_ORG_DIR                     ("app.orgs.dir",                "orgs"),
    APP_ORG_FILE                    ("app.orgs.file",               "orgs.csv"),
    APP_DEP_DIR                     ("app.deps.dir",                "deps"),
    APP_DEP_FILE                    ("app.deps.file",               "deps.csv"),
    APP_USER_PROFILE_IMAGE_NAME     ("app.users.profile",           "profile"),
    ID_SEPARATOR                    ("app.id.separator",            "-"),
    LOG_FILE_DIR                    ("app.resource.log.dir",        "logs"),
    USE_TEMP_DIR                    ("app.resource.useTmpDir",      "true"),
    USE_EXPERIMENTAL_FEATURES       ("app.useExperimentalFeatures", "false"),
    FONT_DIR                        ("app.resource.fonts.dir",      "fonts"),
    IMAGE_DIR                       ("app.resource.images.dir",     "images"),
    FXML_DIR                        ("dir.fxml",                    "fxml"),
    FXML_INDEX_DIR                  ("index.dir.fxml",              "indices"),
    FXML_INDEX_FILE                 ("index.file.fxml",             "fxml.index.properties"),
    PASSWORD_FILE                   ("app.passwd.file",             "passwd.csv");

    private final String key;

    private final String value;

    public static ConcurrentHashMap<String, String> CONFIG_FILE;

    public static final Map<String, String[]> DEFAULT_DATA_DIR;

    static {
        CONFIG_FILE = new ConcurrentHashMap<>();
        CONFIG_FILE.put("app.default.config.file", "config.properties");
        CONFIG_FILE.put("app.default.config.file.test.key", "index");
        CONFIG_FILE.put("app.default.config.file.test.value", "resource_prefix");

        DEFAULT_DATA_DIR = new HashMap<>();
        DEFAULT_DATA_DIR.put("dir", new String[] { "default", "default/images", "users" });
        DEFAULT_DATA_DIR.put("file", new String[] { "users.csv", "complaints.csv" });
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

    public static ConcurrentHashMap<String, String> getDefaultConfig() {
        ConcurrentHashMap<String, String> defaults = new ConcurrentHashMap<>();

        for (OakAppDefaults e : OakAppDefaults.values()) {
            defaults.put(e.key, e.value);
        }

        return defaults;
    }

    public static void main(String[] args) throws IOException {
        Files.createFile(OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_INSTALL_DIR.value));
    }
}
