/**
 * @file UserManager.java
 * <p>
 * Reviews:
 * - Naming
 * 1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import java.nio.file.Path;
import java.util.Map;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.*;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

public final class UserManager {
    private UserManager() {
    }

    // data/users.csv
    private static ObservableMap<String, UserInfo> registeredUser = FXCollections.observableMap(((UsersMap)FactoryDataSourceCSV.getDataSource(DataFile.USER_INFO,"users.csv")
                                                                    .readData())
                                                                    .getUsersMap());


    public static boolean isRegistered(String userName) {
        return registeredUser.containsKey(userName);
    }

    public static <T extends User> void register(Roles role,
                                                 String firstName,
                                                 String lastName,
                                                 String userName,
                                                 String password,
                                                 String confirmPassword,
                                                 ProfileImageState profileImageState) {

        if (!isRegistered(userName) && password.equals(confirmPassword)) {
            DataSourceCSV userCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER);

            T newUser = switch (role) {
                case CONSUMER -> (T) new ConsumerUser(role, firstName, lastName, userName, password, profileImageState);
                case STAFF -> (T) new StaffUser(role, firstName, lastName, userName, password, profileImageState);
                default -> {
                    OakLogger.log(Level.SEVERE, "Attempting to register new user with an unknown or invalid role");
                    System.exit(1);
                }
            };

            T newUser = new User(role,
                    registeredUser.put(userName, newUser);
            userCSV.clearData();

            DataList newData = new DataList(registeredUser);
            userCSV.writeData(newData);

        }

    }

    public static <T extends User> T login(String userName,
                                           String password) {

        if (isRegistered(userName)
                && passwordCheck(userName, password)) {

            User result = getUser(userName);

            return result;
        }

        return null;
    }

    public static <T extends User> T getUser(String userName) {
        if (isRegistered(userName)) {

            T result = (T) registeredUser.get(userName);

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
