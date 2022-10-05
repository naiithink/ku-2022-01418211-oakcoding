/**
 * @file AdminUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

public final class AdminUser
        extends User {

    public AdminUser(Roles role,
                     String firstName,
                     String lastName,
                     Path profileImagePath,
                     String userName,
                     String password) {

        super(role, firstName, lastName, profileImagePath, userName, password);
    }
}
