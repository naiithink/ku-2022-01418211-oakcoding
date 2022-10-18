package ku.cs.oakcoding.app.helpers.id;

import java.time.Instant;

import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;

public final class OakID {
    private OakID() {}

    public static String generate(String suffix) {
        return Long.toString(Instant.now().toEpochMilli()) + OakAppConfigs.getProperty("app.id.separator") + suffix.toLowerCase();
    }
}
