/**
 * @file StaffUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.models.picture.ProfileImage;
import ku.cs.oakcoding.app.services.data_source.callback.FileCallBack;

import java.nio.file.Path;

public final class StaffUser
        extends User {

    private final FileCallBack fileCallBack = FileCallBack.STAFF;

    public StaffUser(Roles role,
                     String firstName,
                     String lastName,
                     ProfileImage profileImagePath,
                     String userName,
                     String password) {

        super(role, firstName, lastName, profileImagePath, userName, password);
    }

    public FileCallBack getFileCallBack(){
        return fileCallBack;
    }




}
