package ku.cs.oakcoding.app.helpers.hotspot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    public static List<String> separateColonValues(String message) {
        if (Objects.isNull(message))
            return null;

        List<String> res = new ArrayList<>();
        String[] resArray = message.split(":");

        for (String elem : resArray) {
            res.add(elem);
        }

        return res;
    }

    public static String toColonSeparatedValues(Collection<String> collection) {
        if (Objects.isNull(collection))
            return null;

        StringBuffer resBuf = new StringBuffer();

        Iterator<String> elemIter = collection.iterator();

        while (elemIter.hasNext()) {
            resBuf.append(elemIter.next());

            if (elemIter.hasNext())
                resBuf.append(":");
        }

        return resBuf.toString();
    }
}
