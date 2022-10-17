package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;
import ku.cs.oakcoding.app.services.data_source.CSV;

public final class PasswordManager {
    private PasswordManager() {}

    private static AutoUpdateCSV passwordDataSource;

    private static Map<String, String> passwordDB = new HashMap<>();

    static {
        try {
            passwordDataSource = new AutoUpdateCSV("passwd", "UID", "passwd", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.PASSWORD_FILE.value()));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Password data not found");
            System.exit(1);
        }

        CSV passwordCSV = passwordDataSource.readAll();
        Set<String> entries = passwordCSV.getPrimaryKeySet();

        for (String entry : entries) {
            passwordDB.put(entry, passwordCSV.getDataWhere(entry, "PASSWORD"));
        }
    }

    public static boolean addPassword(String UID, String password) {
        if (passwordDB.containsKey(UID)) {
            OakLogger.log(Level.SEVERE, "Password already exist, change instead");
            return false;
        }

        passwordDB.put(UID, password);
        passwordDataSource.addRecord(new String[] { UID, password });

        return true;
    }

    public static boolean removePassword(String UID, String password) {
        if (!passwordDB.containsKey(UID)) {
            OakLogger.log(Level.SEVERE, "Password does not exist");
            return false;
        }

        passwordDB.remove(UID);
        passwordDataSource.removeRecordWhere(UID);

        return true;
    }

    public static boolean verifyPassword(String UID, String password) {
        return passwordDB.get(UID).equals(password);
    }

    public static boolean changePassword(String UID, String oldPassword, String newPassword) {
        if (!passwordDB.containsKey(UID))
            return false;

        if (passwordDB.get(UID).equals(oldPassword)) {
            passwordDB.put(UID, newPassword);
            passwordDataSource.editRecord(UID, "PASSWORD", newPassword, false);
            return true;
        }

        return false;
    }
}
