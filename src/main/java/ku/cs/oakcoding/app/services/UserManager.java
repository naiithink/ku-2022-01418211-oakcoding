package ku.cs.oakcoding.app.services;

import java.util.Map;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.DataList;
import ku.cs.oakcoding.app.models.User;

public final class UserManager {
    private UserManager() {}

    // data/users.csv
    private static Map<String,User> registeredUser = ((DataList)(FactoryDataSourceCSV.getDataSource(DataFile.USER).readData())).getUsersMap();

    public static boolean isRegistered(String username) {
        return registeredUser.containsKey(username);
    }

    public static void register (){}

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

            User result = (User) registeredUser.get(username);

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
        return null;
    }


    /**
     * 
     */
    public static Boolean passwordCheck(String username,
                                        String password) {

        if (isRegistered(username)) {
            User checker = (User) registeredUser.get(username);
            return (checker.verifyPassword(password));

        }

        return false;
        
    }

    public static boolean changePassword(String username,
                                      String newPassword){

        if (isRegistered(username)){
            User user = getUser(username);
            if (!(user.getPassword().equals(newPassword))){
                return true;
            }
        }
        return false;


    }

    public static String writePicture(){


        return "picturePath";
    }
}
