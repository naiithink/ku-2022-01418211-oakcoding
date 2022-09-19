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
}
