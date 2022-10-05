/**
 * @file UserManager.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import java.util.Map;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.User;

public final class UserManager {
    private UserManager() {}

    // data/users.csv
    private static Map<String, User> registeredUser = ((DataList) (FactoryDataSourceCSV.getDataSource(DataFile.USER)
                                                                                       .readData())
    ).getUsersMap();

    public static boolean isRegistered(String userName) {
        return registeredUser.containsKey(userName);
    }

    public static void register() {
    }

    public static User login(String userName,
            String password) {

        if (isRegistered(userName)
                && passwordCheck(userName, password)) {

            User result = getUser(userName);

            return result;
        }

        return null;
    }

    public static User getUser(String userName) {
        if (isRegistered(userName)) {

            User result = (User) registeredUser.get(userName);

            // ResourceObject userInfoObject = ResourceManager.getData(String userName);

            // get role
            // users.csv->ROLE
            // Roles userRole = userInfoObject.getField(String "ROLE");

            // get info

            // String firstName = userInfoObject.getField(String "FIRST_NAME");
            // String lastName = userInfoObject.getField(String "LAST_NAME");
            // Boolean hasCustomImage = Boolean.valuOf(userInfoObject.getField(String
            // "PROFILE_IMAGE_STATUS").toLowerCase());

            // User result = new User(userRole, firstName, lastName, hasCustomImage);

            return result;
        }
        return null;
    }

    /**
     * 
     */
    public static Boolean passwordCheck(String userName,
            String password) {

        if (isRegistered(userName)) {
            User checker = (User) registeredUser.get(userName);
            return (checker.verifyPassword(password));

        }

        return false;

    }

    public static boolean changePassword(String userName, String newPassword) {

        if (isRegistered(userName)) {
            User user = getUser(userName);
            if (!(user.getPassword().equals(newPassword))) {
                return true;
            }
        }
        return false;

    }

    /**
     * @todo Implement method
     */
    public static String writePicture() {

        return "picturePath";
    }
}
