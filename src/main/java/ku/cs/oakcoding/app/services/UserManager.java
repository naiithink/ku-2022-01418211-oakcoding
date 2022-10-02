package ku.cs.oakcoding.app.services;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.models.users.DataList;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceCSV;

public final class UserManager {
    private UserManager() {}

    // data/users.csv
    private static TreeMap<String,Object> registeredUser = ((DataList)(FactoryDataSourceCSV.getDataSource(Roles.USER).readData())).getUsersMap();

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

    public static void changePassword(String username,
                                      String password,
                                      String newPassword){


    }

    public static String writePicture(){


        return "picturePath";
    }
}
