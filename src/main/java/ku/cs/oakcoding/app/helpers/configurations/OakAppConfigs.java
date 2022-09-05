/**
 * @file OakAppConfigs.java
 * @version 1.0.0-dev
 * 
 * @danger This file MUST be reviewed for type-safety
 * 
 * Configuration File Syntax
 *  - Lines start with @link ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs.OakSpecialAppConfigToken#COMMENT.token() @unlink are ignored.
 *  - The file consists of key-value pairs, delimited by @link ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs.OakSpecialAppConfigToken#FIELD_DELIMITER.token() @unlink.
 *    A key begins with the name of the section, a '.' token, followed by an optional sequence of '.' delimited unlimited depth subsection, a '.', ends with a configuration name.
 *    A value is a string literal or the token 'NULL'.
 *    Both keys and values are case-sensitive.
 * 
 *    section[.subsection1[.subsection2[.subsectionN]]].name,value
 * 
 *    e.g.,
 *      # App language comment line
 *      app.locale.language,en_US
 */

package ku.cs.oakcoding.app.helpers.configurations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ku.cs.oakcoding.app.helpers.construct.OakPatterns;

public final class OakAppConfigs {
    private static OakAppConfigs instance;
    private OakAppConfigs() {}

    private static volatile Properties configProperty;

    private static volatile boolean useExperimentalFeature;

    private static volatile Pattern patternAppConstruct;

    static {
        if (instance == null) {
            synchronized (OakAppConfigs.class) {
                if (instance == null) {
                    instance = new OakAppConfigs();
                }
            }
        }

        try (InputStream in = instance.getClass()
                                      .getClassLoader()
                                      .getResourceAsStream(OakAppDefaults.CONFIG_FILE.get("app.default.configFile"))) {

            Objects.requireNonNull(in);

            configProperty = new Properties();

            configProperty.load(in);
        } catch (IOException | NullPointerException e) {
            System.err.println("ERROR: App configuration file is missing");
            System.exit(1);
        }

        useExperimentalFeature = Boolean.parseBoolean(configProperty.getProperty("useExperimentalFeature"));
    }

    public static synchronized OakAppConfigs getInstance() {
        return instance;
    }

    // public abstract void restoreToDefault(byte[] authCode);

    // public abstract boolean resetToDefault(byte[] authCode);

    /**
     * @todo consider moving to another type
     */
    private static String nonCommentRegionOf(String line) {
        Matcher match = OakPatterns.getPatternSpecialComment().matcher(line);

        return match.find() ? match.group(1)
                            : null;
    }

    private static Pattern getPatternAppConstruct() {
        if (patternAppConstruct == null) {
            synchronized (OakAppConfigs.class) {
                if (patternAppConstruct == null) {
                    patternAppConstruct = Pattern.compile("(?<=app\\.construct\\.).*");
                }
            }
        }

        return patternAppConstruct;
    }

    private static boolean isLanguageConstruct(String key) {
        return getPatternAppConstruct().matcher(key).find();
    }

    private static String languageConstructRegion(String key) {
        Matcher m = getPatternAppConstruct().matcher(key);

        return m.find() ? m.group(1)
                        : null;
    }

    public static boolean containsKey(String key) {
        return configProperty.containsKey(key);
    }

    public static String getProperty(String key) {
        if (containsKey(key) == false
            && OakAppDefaults.valueOfKey(key) != null) {

            configProperty.put(key, OakAppDefaults.valueOfKey(key));
        }

        return configProperty.getProperty(key);
    }

    /**
     * @todo do we even need to alter configs?
     */
    // public static void setProperty(String key, String token) {
    //     configProperty.setProperty(key, token);
    // }

    public static boolean useExperimentalFeature() {
        return useExperimentalFeature;
    }
}
