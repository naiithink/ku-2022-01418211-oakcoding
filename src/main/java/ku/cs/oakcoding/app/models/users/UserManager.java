/**
 * @file UserManager.java
 * <p>
 * Reviews:
 * - Naming
 * 1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResource;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.file.OakUserResource;
import ku.cs.oakcoding.app.helpers.hotspot.OakHotspot;
import ku.cs.oakcoding.app.helpers.id.OakID;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.PasswordManager;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public final class UserManager {

    private final ObservableMap<String, UserEntry> briefUserTable;

    private final ObservableSet<FullUserEntry> userEntrySet;

    private AutoUpdateCSV briefUserFile;

    private AutoUpdateCSV sessionFile;

    public UserManager(AutoUpdateCSV briefUserFile, AutoUpdateCSV sessionFile, UserEntry... briefUserEntries) {
        this.briefUserTable = FXCollections.observableHashMap();
        this.briefUserFile = briefUserFile;
        this.sessionFile = sessionFile;
        this.userEntrySet = FXCollections.observableSet();

        for (UserEntry entry : briefUserEntries) {
            this.briefUserTable.put(entry.getUID(), entry);
        }
    }

    public UserManager(AutoUpdateCSV briefUserFile, AutoUpdateCSV sessionFile, Collection<UserEntry> briefUserEntries) {
        this.briefUserTable = FXCollections.observableHashMap();
        this.briefUserFile = briefUserFile;
        this.sessionFile = sessionFile;
        this.userEntrySet = FXCollections.observableSet();

        for (UserEntry entry : briefUserEntries) {
            this.briefUserTable.put(entry.getUID(), entry);
        }
    }

    public String getUIDOf(final String userName) {
        Set<String> briefUserTableKeys =  briefUserTable.keySet();
        for (String key : briefUserTableKeys)
            if (briefUserTable.get(key).getUserName().equals(userName))
                return key;

        return null;
    }

    public boolean isRegistered(String userName) {
        Iterator<Entry<String, UserEntry>> entries = briefUserTable.entrySet().iterator();

        while (entries.hasNext()) {
            Entry<String, UserEntry> entry = entries.next();

            if (userName.equals(entry.getValue().getUserName()))
                return true;
        }

        return false;
    }

    public boolean isActive(String userName) {
        return briefUserTable.get(getUIDOf(userName)).getIsActive();
    }

    public boolean userExists(String UID) {
        return this.briefUserTable.containsKey(UID);
    }

    public Set<FullUserEntry> getAllUsersSet(AdminUser adminUser) {
        if (Objects.isNull(adminUser)) {
            OakLogger.log(Level.SEVERE, "Attempting to get all users list with out AdminUser access, null is returned");
            return null;
        }

        return userEntrySet;
    }

    public ObservableSet<FullUserEntry> getAllUsersSetProperty(AdminUser adminUser) {
        if (Objects.isNull(adminUser)) {
            OakLogger.log(Level.SEVERE, "Attempting to get all users list property with out AdminUser access, null is returned");
            return null;
        }

        return userEntrySet;
    }

    @SuppressWarnings("unchecked")
    public <T extends User> UserManagerStatus register(final T dispatcher,
                                                       final String userName,
                                                       final Roles role,
                                                       final String firstName,
                                                       final String lastName,
                                                       final Path sourceProfileImagePath,
                                                       final String password,
                                                       final String confirmPassword) {

        if (isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "User already exist");
            return UserManagerStatus.USER_NAME_ALREADY_EXIST;
        } else if (!OakHotspot.validUserName(userName)) {
            OakLogger.log(Level.SEVERE, "userName must be in all lowercase");
            return UserManagerStatus.USER_NAME_CONTAINS_UPPER_CASE;
        } else if (role.equals(Roles.ADMIN)) {
            OakLogger.log(Level.SEVERE, "Attempting to register an AdminUser account");
            return UserManagerStatus.INVALID_ACCESS;
        } else if (role.equals(Roles.STAFF)
                   && (Objects.isNull(dispatcher)
                       || !(dispatcher instanceof AdminUser))) {

            OakLogger.log(Level.SEVERE, "Attempting to register a StaffAccount without AdminUser access");
            return UserManagerStatus.INVALID_ACCESS;
        }

        T newUser = null;
        Path newUserDirPath = null;

        if (!password.equals(confirmPassword)) {
            OakLogger.log(Level.SEVERE, "Password does not match");
            return null;
        }

        newUserDirPath = OakUserResource.newUserDirectory(userName);

        if (Objects.isNull(newUserDirPath)) {
            OakLogger.log(Level.SEVERE, "Cannot create user directory");
            return UserManagerStatus.FAILURE;
        }

        boolean usingDefaultProfileImage = true;
        Path profileImagePath = null;
        String profileImageExtension = null;
        Pattern extensionPattern = Pattern.compile("[^\\\\]*\\.(\\w+)$");
        Matcher extensionMatcher = null;

        if (Objects.isNull(sourceProfileImagePath)) {
            profileImagePath = OakUserResource.getDefaultProfileImagePath();

            OakLogger.log(Level.INFO, "Using default profile image");

            extensionMatcher = extensionPattern.matcher(profileImagePath.toString());

            if (extensionMatcher.find() && extensionMatcher.groupCount() == 1)
                profileImageExtension = extensionMatcher.group(1);

            usingDefaultProfileImage = true;
        } else {
            try {
                extensionMatcher = extensionPattern.matcher(sourceProfileImagePath.toString());

                OakLogger.log(Level.INFO, "Profile image Content-Type: " + Files.probeContentType(sourceProfileImagePath));

                if (extensionMatcher.find() && extensionMatcher.groupCount() == 1)
                    profileImageExtension = extensionMatcher.group(1);

                OakLogger.log(Level.INFO, "Profile image extension: " + profileImageExtension);

                profileImagePath = OakResource.renameFile(OakResource.copyFile(sourceProfileImagePath, newUserDirPath.resolve(sourceProfileImagePath.getFileName())), newUserDirPath.resolve(OakAppDefaults.APP_USER_PROFILE_IMAGE_NAME.value() + "." + profileImageExtension));
            } catch (IOException e) {
                OakLogger.log(Level.SEVERE, "Got 'IOException' while saving uploaded profile image");
                return UserManagerStatus.FAILURE;
            }

            usingDefaultProfileImage = false;
        }

        newUser = (T) switch (role) {
            case CONSUMER -> new ConsumerUser(OakID.generate(ConsumerUser.class.getSimpleName()), userName, Roles.CONSUMER, firstName, lastName, usingDefaultProfileImage, profileImageExtension, profileImagePath);
            case STAFF -> new StaffUser(OakID.generate(StaffUser.class.getSimpleName()), userName, firstName, lastName, usingDefaultProfileImage, profileImageExtension, profileImagePath);
            case ADMIN -> null;
        };

        AutoUpdateCSV userInfo;

        try {
            userInfo = new AutoUpdateCSV(userName, "NAME", userName, OakUserResource.getUserDirOf(userName).resolve(OakAppDefaults.APP_USER_INFO.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Got 'FileNotFoundException' while attempting to create CSV info file for new user");
            return UserManagerStatus.FAILURE;
        }

        userInfo.addRecord(newUser.toCSVList());

        UserEntry userEntry = new UserEntry(newUser.getUID(), newUser.getRole(), newUser.getUserName(), true, 0);

        briefUserTable.put(newUser.getUID(), userEntry);

        briefUserFile.addRecord(new String[] { newUser.getUID(), newUser.getUserName(), newUser.getRole().name(), String.valueOf(true), String.valueOf(0) });

        sessionFile.addRecord(new String[] { newUser.getUID(), "0" });

        PasswordManager.addPassword(getUIDOf(userName), password);

        return UserManagerStatus.SUCCESSFUL;
    }

    public <T extends User> T login(String userName,
                                    String password) {

        if (!isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "User does not exist");
            return null;
        } else if (!OakHotspot.validUserName(userName) && !briefUserTable.get(getUIDOf(userName)).getRole().equals(Roles.ADMIN)) {
            OakLogger.log(Level.SEVERE, "userName must be in all lowercase");
            return null;
        }

        if (!isActive(userName)) {
            briefUserTable.get(getUIDOf(userName)).addLoginAttempt();
            briefUserFile.editRecord(getUIDOf(userName), "LOGIN_ATTEMPT", String.valueOf(briefUserTable.get(getUIDOf(userName)).getLoginAttempt()), false);
            OakLogger.log(Level.SEVERE, "User is being suspended");
            return null;
        } else if (!PasswordManager.verifyPassword(getUIDOf(userName), password)) {
            OakLogger.log(Level.INFO, "Wrong password");
            return null;
        }

        sessionFile.editRecord(getUIDOf(userName), "TIME", String.valueOf(Instant.now().toEpochMilli()), false);

        T user = getUser(userName);

        if (userEntrySet.isEmpty() && (user instanceof AdminUser)) {
            Set<Entry<String, UserEntry>> briefEntries = briefUserTable.entrySet();

            for (Entry<String, UserEntry> entry : briefEntries) {
                if (entry.getValue().getRole().equals(Roles.ADMIN))
                    continue;

                Path profileImagePath = null;

                UserEntry briefUserInfo = entry.getValue();

                AutoUpdateCSV userInfo = null;

                try {
                    userInfo = new AutoUpdateCSV(briefUserInfo.getUserName(), "UID", briefUserInfo.getUserName(), OakUserResource.getUserDirOf(briefUserInfo.getUserName()).resolve(OakAppDefaults.APP_USER_INFO.value()));
                } catch (FileNotFoundException e) {
                    OakLogger.log(Level.SEVERE, "Failed to iterate over users info file to construct all users list for AdminUser");
                }

                if (Boolean.parseBoolean(userInfo.getDataWhere(briefUserInfo.getUID(), "USING_DEFAULT_PROFILE_IMAGE")))
                    profileImagePath = OakUserResource.getDefaultProfileImagePath();
                else
                    profileImagePath = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_DIR.value()).resolve(briefUserInfo.getUserName()).resolve(OakAppDefaults.APP_USER_PROFILE_IMAGE_NAME.value() + "." + userInfo.getDataWhere(briefUserInfo.getUID(), "PROFILE_IMAGE_EXT"));

                userEntrySet.add(new FullUserEntry(briefUserInfo.getUID(),
                                                   briefUserInfo.getRole(),
                                                   briefUserInfo.getUserName(),
                                                   briefUserInfo.getIsActive(),
                                                   briefUserInfo.getLoginAttempt(),
                                                   userInfo.getDataWhere(briefUserInfo.getUID(), "FIRST_NAME"),
                                                   userInfo.getDataWhere(briefUserInfo.getUID(), "LAST_NAME"),
                                                   profileImagePath,
                                                   Long.parseLong(sessionFile.getDataWhere(briefUserInfo.getUID(), "TIME"))));
            }
        }

        return user;
    }

    @SuppressWarnings("unchecked")
    private <T extends User> T getUser(String userName) {

        if (!isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "Attempting to get non existing user");
            return null;
        }

        AutoUpdateCSV userCSV;

        try {
            userCSV = new AutoUpdateCSV(userName, "UID", userName, OakUserResource.getUserDirOf(userName).resolve(OakAppDefaults.APP_USER_INFO.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "User info file not found");
            return null;
        }

        Path profileImagePath = null;

        if (Boolean.parseBoolean(userCSV.getDataWhere(getUIDOf(userName), "USING_DEFAULT_PROFILE_IMAGE"))) {
            profileImagePath = OakUserResource.getDefaultProfileImagePath();
        } else {
            profileImagePath = OakResourcePrefix.getDataDirPrefix().resolve(userName).resolve(OakAppDefaults.APP_USER_PROFILE_IMAGE_NAME.value() + "." + userCSV.getDataWhere(getUIDOf(userName), "PROFILE_IMAGE_EXT"));
        }

        T user = (T) switch (briefUserTable.get(getUIDOf(userName)).getRole()) {
            case CONSUMER -> new ConsumerUser(userCSV.getDataWhere(getUIDOf(userName), "UID"),
                                              userCSV.getDataWhere(getUIDOf(userName), "USER_NAME"),
                                              Roles.CONSUMER,
                                              userCSV.getDataWhere(getUIDOf(userName), "FIRST_NAME"),
                                              userCSV.getDataWhere(getUIDOf(userName), "LAST_NAME"),
                                              Boolean.parseBoolean(userCSV.getDataWhere(getUIDOf(userName), "USING_DEFAULT_PROFILE_IMAGE")),
                                              userCSV.getDataWhere(getUIDOf(userName), "PROFILE_IMAGE_EXT"),
                                              profileImagePath);

            case STAFF -> new StaffUser(userCSV.getDataWhere(getUIDOf(userName), "UID"),
                                        userCSV.getDataWhere(getUIDOf(userName), "USER_NAME"),
                                        userCSV.getDataWhere(getUIDOf(userName), "FIRST_NAME"),
                                        userCSV.getDataWhere(getUIDOf(userName), "LAST_NAME"),
                                        Boolean.parseBoolean(userCSV.getDataWhere(getUIDOf(userName), "USING_DEFAULT_PROFILE_IMAGE")),
                                        userCSV.getDataWhere(getUIDOf(userName), "PROFILE_IMAGE_EXT"),
                                        profileImagePath);

            case ADMIN -> new AdminUser(userCSV.getDataWhere(getUIDOf(userName), "UID"),
                                        userCSV.getDataWhere(getUIDOf(userName), "USER_NAME"),
                                        userCSV.getDataWhere(getUIDOf(userName), "FIRST_NAME"),
                                        userCSV.getDataWhere(getUIDOf(userName), "LAST_NAME"),
                                        Boolean.parseBoolean(userCSV.getDataWhere(getUIDOf(userName), "USING_DEFAULT_PROFILE_IMAGE")),
                                        userCSV.getDataWhere(getUIDOf(userName), "PROFILE_IMAGE_EXT"),
                                        profileImagePath);
        };

        return user;
    }

    public UserManagerStatus setActiveStatus(AdminUser adminUser, String userName, boolean isActive) {
        if (Objects.isNull(adminUser))
            return UserManagerStatus.INVALID_ACCESS;

        if (!isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "User does not exist");
            return UserManagerStatus.FAILURE;
        } else if (!briefUserTable.containsKey(getUIDOf(userName))) {
            OakLogger.log(Level.SEVERE, "Attempting to set active status for non existing account");
            return UserManagerStatus.FAILURE;
        }

        briefUserTable.get(getUIDOf(userName)).setIsActive(isActive);
        briefUserFile.editRecord(getUIDOf(userName), "IS_ACTIVE", String.valueOf(isActive), false);

        if (isActive) {
            briefUserTable.get(getUIDOf(userName)).resetLoginAttempt();
            briefUserFile.editRecord(getUIDOf(userName), "LOGIN_ATTEMPT", String.valueOf(0), false);
        }

        return UserManagerStatus.SUCCESSFUL;
    }

    public UserManagerStatus deleteUser(AdminUser adminUser, String userName, String password) {
        if (Objects.isNull(adminUser))
            return UserManagerStatus.INVALID_ACCESS;

        if (!isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "User does not exist");
            return UserManagerStatus.FAILURE;
        } else if (!OakHotspot.validUserName(userName)) {
            OakLogger.log(Level.SEVERE, "userName must be in all lowercase");
            return UserManagerStatus.USER_NAME_CONTAINS_UPPER_CASE;
        }

        PasswordManager.removePassword(getUIDOf(userName), password);
        OakUserResource.removeUserDirectory(userName);
        sessionFile.removeRecordWhere(getUIDOf(userName));
        briefUserFile.removeRecordWhere(getUIDOf(userName));

        Iterator<FullUserEntry> users = userEntrySet.iterator();

        while (users.hasNext()) {
            FullUserEntry user = users.next();
            if (user.getUserName().equals(userName)) {
                userEntrySet.remove(user);
                break;
            }
        }

        briefUserTable.remove(userName);

        return UserManagerStatus.SUCCESSFUL;
    }

    public boolean changePassword(String userName, String oldPassword, String newPassword, String confirmNewPassword) {
        if (!OakHotspot.validUserName(userName) && !briefUserTable.get(getUIDOf(userName)).getRole().equals(Roles.ADMIN)) {
            OakLogger.log(Level.SEVERE, "userName must be in all lowercase");
            return false;
        }

        if (!isRegistered(userName)) {
            OakLogger.log(Level.SEVERE, "User does not exist");
            return false;
        } else if (!PasswordManager.verifyPassword(getUIDOf(userName), oldPassword)) {
            OakLogger.log(Level.INFO, "Wrong password");
            return false;
        } else if (!newPassword.equals(confirmNewPassword)) {
            OakLogger.log(Level.INFO, "Password not match");
            return false;
        }

        if (PasswordManager.changePassword(getUIDOf(userName), oldPassword, confirmNewPassword))
            return true;

        return false;
    }

    public ObservableMap<String, UserEntry> getBriefUserTableProperty() {
        return briefUserTable;
    }
}
