/**
 * @file UserManager.java
 * <p>
 * Reviews:
 * - Naming
 * 1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.ProfileImageState;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.StaffUser;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UserInfo;
import ku.cs.oakcoding.app.models.users.UsersList;
import ku.cs.oakcoding.app.models.users.UsersMap;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

public final class UserManager {
    private UserManager() {
    }

    // data/users.csv
    private static ObservableMap<String, UserInfo> registeredUser = FXCollections
            .observableMap(((UsersMap) FactoryDataSourceCSV.getDataSource(DataFile.USER_INFO, "users.csv")
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
            Path profileImagePath) {

        T newUser = null;

        try {
            if (!isRegistered(userName) && password.equals(confirmPassword)) {
                DataSourceCSV userCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER, "users.csv");

                ProfileImageState profileImageState;

                if (Objects.isNull(profileImagePath)) {
                    profileImageState = ProfileImageState.DEFAULT;
                } else {
                    profileImageState = ProfileImageState.CUSTOM;
                    DataSourceCSV profileImage = FactoryDataSourceCSV.getDataSource(DataFile.PICTURE, userName);

                    profileImage.writeData(profileImagePath);

                    switch (role) {
                        case CONSUMER:
                            newUser = (T) new ConsumerUser(role, firstName, lastName, userName, password, profileImageState, true);
                            break;
                        case STAFF:
                            newUser = (T) new StaffUser(role, firstName, lastName, userName, password, profileImageState);
                            break;
                        default:
                            OakLogger.log(Level.SEVERE,
                                    "Attempting to register new user with an unknown or invalid role");
                            System.exit(1);
                    }
                }

                UsersList usersList = (UsersList) userCSV.readData();
                usersList.addUser(newUser);
                userCSV.clearData();
                userCSV.writeData(usersList);
            }
        } catch (

        IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while saving image");
            System.exit(1);
        }
    }

    public static <T extends User> T login(String userName,
            String password) {

        if (isRegistered(userName)
                && passwordCheck(userName, password) && isActive(userName)) {

            return getUser(userName);
        }

        return null;
    }

    public static boolean isActive(String userName) {
        UserInfo userInfo = registeredUser.get(userName);
        return userInfo.isActive();
    }

    public static <T extends User> T getUser(String userName) {
        T newUser = null;

        if (isRegistered(userName)) {

            UserInfo userInfo = registeredUser.get(userName);
            Roles role = userInfo.role();

            switch (role) {
                case CONSUMER:
                    newUser = (T) FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, userName);
                    break;

                case STAFF:
                    newUser = (T) FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, userName);
                    break;

                case ADMIN:
                    newUser = (T) FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, userName);
                    break;

                default:
                    OakLogger.log(Level.SEVERE, "Attempting to login with no ID");
                    System.exit(1);
            }
            ;
            return newUser;

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

        }
        return null;
    }

    /**
     *
     */
    public static Boolean passwordCheck(String userName,
            String password) {

        if (isRegistered(userName)) {
            DataSourceCSV userDataSource = FactoryDataSourceCSV.getDataSource(DataFile.USER_PROFILE, userName);

            User checker = (User) userDataSource.readData();
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
