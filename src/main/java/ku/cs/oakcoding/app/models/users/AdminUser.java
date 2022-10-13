/**
 * @file AdminUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.complaints.Report;
import ku.cs.oakcoding.app.models.complaints.Resolver;

public final class AdminUser
        extends User
        implements Resolver<Report> {

    private final DataFile dataFile = DataFile.USER_PROFILE;

    public AdminUser(Roles role,
                     String firstName,
                     String lastName,
                     String userName,
                     String password,
                     ProfileImageState profileImageState) {

        super(role, firstName, lastName, userName, password,profileImageState);
    }

    public DataFile getDataFile (){
        return dataFile;
    }
}
