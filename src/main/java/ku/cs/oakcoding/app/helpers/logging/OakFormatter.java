package ku.cs.oakcoding.app.helpers.logging;

import java.time.Instant;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public final class OakFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return Instant.now().toString()
               + ","    + record.getLongThreadID()
               + ","    + record.getLevel()
               + ","    + record.getSourceClassName()
                        + "."    + record.getSourceMethodName()
               + ","    + record.getMessage()
               + "\n";
    }
}
