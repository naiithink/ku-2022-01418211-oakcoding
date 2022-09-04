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
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ku.cs.oakcoding.app.helpers.construct.OakPatterns;

public final class OakAppConfigs {
    private OakAppConfigs instance;
    private OakAppConfigs() {}

    /**
     * @warning access to get the instance might be too strong
     */
    public OakAppConfigs getInstance() {
        if (instance == null) {
            synchronized (OakAppConfigs.class) {
                if (instance == null) {
                    instance = new OakAppConfigs();
                }
            }
        }

        return instance;
    }

    private class EnsureType<T> {

        private T unknownType;

        public EnsureType(T unknownType) {
            this.unknownType = unknownType;
        }

        public Class<?> getTypeOfInitializer() {
            return unknownType.getClass();
        }
    }

    private static volatile Properties configProperty;

    private static volatile boolean useExperimentalFeature;

    private static volatile Pattern patternAppConstruct;

    static {
        String assertConfigFilePathString = OakSystemDefaults.USER_DIR.value()
                                            + OakSystemDefaults.FILE_SEPARATOR.value()
                                            + OakAppDefaults.CONFIG_FILE.value();
        Path assertConfigFilePath = Paths.get(assertConfigFilePathString);

        if (Files.exists(assertConfigFilePath, LinkOption.NOFOLLOW_LINKS) == false) {
            System.err.println("ERROR: App configuration file is missing");
            System.exit(1);
        }

        try (InputStream in = Files.newInputStream(assertConfigFilePath, LinkOption.NOFOLLOW_LINKS)) {
            configProperty = new Properties();

            configProperty.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        useExperimentalFeature = Boolean.parseBoolean(configProperty.getProperty("useExperimentalFeature"));
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T getProperty(String key) {
        T result = null;
        String preResult = new String();

        if ((preResult = nonCommentRegionOf(key)) == null) {
            return null;
        }

        if (isLanguageConstruct(preResult) == true) {
            EnsureType<T> ensureType = new EnsureType<T>(result);
            Class<?> resultType = ensureType.getTypeOfInitializer();
            Class<?> configKeyClass = null;

            try {
                configKeyClass = Class.forName(languageConstructRegion(preResult));
            } catch (ClassNotFoundException e) {
                //** @warning */ 
            }

            // /.*\=/
            String configValue = configProperty.getProperty(languageConstructRegion(preResult));

            if (configKeyClass.isAssignableFrom(resultType) == false) {
                result = null;
            }

            /**
             * @todo get a value of an enum constant via method invoked from a string.
             */
            // > if (configKeyClass.isEnum()) {
            // >     T[] enumConstants = configKeyClass.getEnumConstants();
            // >     result = Enum.valueOf(resultType.getClass(), configValue);
            // > }
        }

        return result;
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
