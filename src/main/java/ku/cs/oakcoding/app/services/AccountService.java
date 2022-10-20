package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.UserEntry;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.UserManager;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public final class AccountService implements AppService {
    public AccountService() {}

    private static UserManager userManager;

    @Override
    public void start() {
        AutoUpdateCSV userEntriesDB = null;
        AutoUpdateCSV sessionDB = null;
        AutoUpdateCSV userRequestDB = null;

        try {
            userEntriesDB = new AutoUpdateCSV("briefUserEntries", "UID", "briefUserEntries", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_ENTRIES.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "User account entry file not found");
            System.exit(1);
        }

        try {
            sessionDB = new AutoUpdateCSV("sessions", "UID", "sessions", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_SESSION_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Session file not found");
            System.exit(1);
        }

        try {
            System.out.println(OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_DIR.value()).resolve(OakAppDefaults.APP_USER_REQUESTS.value()));
            userRequestDB = new AutoUpdateCSV("userRequests", "UID", "userRequests", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_DIR.value()).resolve(OakAppDefaults.APP_USER_REQUESTS.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "User request file not found");
            System.exit(1);
        }

        Set<UserEntry> userEntrySet = new HashSet<>();
        Set<String> primaryKeySet = userEntriesDB.getPrimaryKeySet();

        for (String primaryKey : primaryKeySet) {
            userEntrySet.add(new UserEntry(primaryKey, Enum.valueOf(Roles.class, userEntriesDB.getDataWhere(primaryKey, "ROLE")), userEntriesDB.getDataWhere(primaryKey, "USER_NAME"), Boolean.parseBoolean(userEntriesDB.getDataWhere(primaryKey, "IS_ACTIVE")), Integer.parseInt(userEntriesDB.getDataWhere(primaryKey, "LOGIN_ATTEMPT"))));
        }

        userManager = new UserManager(userEntriesDB, sessionDB, userRequestDB, userEntrySet);
    }

    public static UserManager getUserManager() {
        return userManager;
    }
}
