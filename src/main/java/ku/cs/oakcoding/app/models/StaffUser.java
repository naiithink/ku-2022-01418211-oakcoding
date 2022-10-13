/**
 * @file StaffUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.Resolver;

public final class StaffUser
        extends User
        implements Resolver<Complaint> {

    private final DataFile dataFile = DataFile.USERPROFILE;

    public StaffUser(Roles role,
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
