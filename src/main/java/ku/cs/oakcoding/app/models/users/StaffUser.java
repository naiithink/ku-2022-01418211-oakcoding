/**
 * @file StaffUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import java.nio.file.Path;

import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.Resolver;

public final class StaffUser
        extends User
        implements Resolver<Complaint> {

    private final Roles role = Roles.STAFF;

    public StaffUser(final String UID,
                     final String userName,
                     final String firstName,
                     final String lastName,
                     final boolean usingDefaultProfileImage,
                     final String profileImageExtension,
                     final Path profileImagePath) {

        super(UID, userName, Roles.STAFF, firstName, lastName, usingDefaultProfileImage, profileImageExtension, profileImagePath);
    }
}
