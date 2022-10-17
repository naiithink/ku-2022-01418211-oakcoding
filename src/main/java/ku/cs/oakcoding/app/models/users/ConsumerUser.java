/**
 * @file ConsumerUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import java.nio.file.Path;

public final class ConsumerUser
        extends User {

    public ConsumerUser(final String UID,
                        String userName,
                        Roles role,
                        String firstName,
                        String lastName,
                        boolean usingDefaultProfileImage,
                        String profileImageExtension,
                        Path profileImagePath) {

        super(UID, userName, role, firstName, lastName, usingDefaultProfileImage, profileImageExtension, profileImagePath);
    }
}
