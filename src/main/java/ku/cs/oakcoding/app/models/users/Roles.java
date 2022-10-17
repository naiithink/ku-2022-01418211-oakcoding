/**
 * @file Roles.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

public enum Roles {
    ADMIN               ("Admin"),
    STAFF               ("Staff"),
    CONSUMER            ("Consumer");

    private final String prettyPrinted;

    private Roles(String prettyPrinted) {
        this.prettyPrinted = prettyPrinted;
    }

    public String getPrettyPrinted() {
        return prettyPrinted;
    }
}
