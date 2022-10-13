/**
 * @file ConsumerUser.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;

public final class ConsumerUser
        extends User {

    private final DataFile dataFile = DataFile.USER_PROFILE;

    public ConsumerUser(Roles role,
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

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileImage=" + profileImageState +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", dataFile=" + dataFile +
                '}';
    }


}
