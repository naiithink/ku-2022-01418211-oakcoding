/**
 * @file StaffUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.picture.ProfileImage;

public final class StaffUser
        extends User {

    private final DataFile dataFile = DataFile.USERPROFILE;

    public StaffUser(Roles role,
                     String firstName,
                     String lastName,
                     String userName,
                     String password,
                     ProfileImage profileImagePath) {

        super(role, firstName, lastName, userName, password,profileImagePath);
    }

    public DataFile getDataFile (){
        return dataFile;
    }




}
