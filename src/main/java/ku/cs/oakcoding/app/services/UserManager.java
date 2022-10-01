package ku.cs.oakcoding.app.services;

import java.util.Set;

import ku.cs.oakcoding.app.models.User;

public final class UserManager {
    private UserManager() {}

    // data/users.csv
    private static Set<String> registeredUser;

    public static boolean isRegistered(String username) {
        return registeredUser.contains(username);
    }

    public static User register {}

    public static User login(String username,
                             String password) {

        if (isRegistered(username)
            && passwordCheck(username, password)) {

            User result = getUser(username);

            return result;
        }

        return null;
    }

    public static User getUser(String username) {
        if (isRegistered(username)) {
            // ResourceObject userInfoObject = ResourceManager.getData(String username);

            // get role
            // users.csv->ROLE
            // Roles userRole = userInfoObject.getField(String "ROLE");

            // get info

            // String firstName = userInfoObject.getField(String "FIRST_NAME");
            // String lastName = userInfoObject.getField(String "LAST_NAME");
            // Boolean hasCustomImage = Boolean.valuOf(userInfoObject.getField(String "PROFILE_IMAGE_STATUS").toLowerCase());

            // User result = new User(userRole, firstName, lastName, hasCustomImage);

            return result;
        }
    }

    /**
     * 
     */
    public static Boolean passwordCheck(String username,
                                        String password) {


        
    }

    public static void changePassword(String username,
                                      String password,
                                      String newPassword){


    }

    public static String writePicture(){


        return "picturePath";
    }
}
