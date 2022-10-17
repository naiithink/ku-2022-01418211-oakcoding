package ku.cs.oakcoding.app.helpers.hotspot;

import java.time.Instant;

public final class OakHotspot {
    private OakHotspot() {}

    private static volatile Instant START_TIME;

    static {
        START_TIME = Instant.now();
    }

    public static long getStartTime() {
        return START_TIME.toEpochMilli();
    }

    public static long getCurrentTime() {
        return Instant.now().toEpochMilli();
    }

    public static boolean validUserName(String string) {
        for (char c : string.toCharArray())
            if (!(c >= 'a' && c <= 'z') && c != '_' && !(c >= 48 && c <= 57))
                return false;

        return true;
    }
}
