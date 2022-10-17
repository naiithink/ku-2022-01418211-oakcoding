package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.BriefUserEntry;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.UserManager;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public final class AccountService implements AppService {
    public AccountService() {}

    private static UserManager userManager;

    @Override
    public void start() {
        AutoUpdateCSV userEntriesDataSource = null;
        AutoUpdateCSV sessionFile = null;

        try {
            userEntriesDataSource = new AutoUpdateCSV("briefUserEntries", "UID", "briefUserEntries", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_USER_ENTRIES.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "User account entry file not found");
            System.exit(1);
        }

        try {
            sessionFile = new AutoUpdateCSV("sessions", "UID", "sessions", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_SESSION_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Session file not found");
            System.exit(1);
        }

        Set<BriefUserEntry> userEntrySet = new HashSet<>();
        Set<String> primaryKeySet = userEntriesDataSource.getPrimaryKeySet();

        for (String primaryKey : primaryKeySet) {
            userEntrySet.add(new BriefUserEntry(primaryKey, Enum.valueOf(Roles.class, userEntriesDataSource.getDataWhere(primaryKey, "ROLE")), userEntriesDataSource.getDataWhere(primaryKey, "USER_NAME"), Boolean.parseBoolean(userEntriesDataSource.getDataWhere(primaryKey, "IS_ACTIVE")), Integer.parseInt(userEntriesDataSource.getDataWhere(primaryKey, "LOGIN_ATTEMPT"))));
        }

        userManager = new UserManager(userEntriesDataSource, sessionFile, userEntrySet);
    }

    public static UserManager getUserManager() {
        return userManager;
    }
}
