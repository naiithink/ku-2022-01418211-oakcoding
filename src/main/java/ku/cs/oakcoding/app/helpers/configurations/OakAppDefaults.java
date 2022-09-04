/**
 * @file OakAppDefaults.java
 * @version 1.0.0-dev
 */

package ku.cs.oakcoding.app.helpers.configurations;

/**
 * Default reference to OakCoding's application configuration entries.
 */
public enum OakAppDefaults {

    APP_NAME            ("OakCoding"),
    DEVELOPER_NAME      ("ku.cs.oakcoding"),
    CONFIG_FILE         ("config.properties");

    private final String value;

    private OakAppDefaults(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
