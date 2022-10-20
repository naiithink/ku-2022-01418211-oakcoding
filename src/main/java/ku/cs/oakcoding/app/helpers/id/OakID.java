package ku.cs.oakcoding.app.helpers.id;

import java.time.Instant;
import java.util.Objects;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public final class OakID {
    private OakID() {}

    public static String generate(String suffix) {
        return Long.toString(Instant.now().toEpochMilli()) + OakAppConfigs.getProperty("app.id.separator") + suffix.toLowerCase();
    }

    public static String of(Long timestamp, String suffix) {
        return timestamp.toString() + OakAppConfigs.getProperty("app.id.separator") + suffix;
    }

    public static String of(String timestamp, String suffix) {
        return timestamp + OakAppConfigs.getProperty("app.id.separator") + suffix;
    }

    public static Long getGenerateTimeOf(String oakID) {
        if (Objects.isNull(oakID)) {
            OakLogger.log(Level.SEVERE, "Attempting to get time from null argument");
            return null;
        }

        String[] splittedID = oakID.split(OakAppConfigs.getProperty("app.id.separator"));

        if (splittedID.length < 1) {
            OakLogger.log(Level.SEVERE, "Invalid OakID '" + oakID + "'");
            return null;
        }

        return Long.valueOf(splittedID[0]);
    }
}
