/**
 * @file AdminUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import java.nio.file.Path;

public final class AdminUser
        extends User {

    private final Roles ROLE = Roles.ADMIN;

    public AdminUser(final String UID,
                     final String userName,
                     final String firstName,
                     final String lastName,
                     final boolean usingDefaultProfileImage,
                     final String profileImageExtension,
                     final Path profileImagePath) {

        super(UID, userName, Roles.ADMIN, firstName, lastName, usingDefaultProfileImage, profileImageExtension, profileImagePath);
    }
}
