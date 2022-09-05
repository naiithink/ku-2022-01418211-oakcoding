package ku.cs.oakcoding.app.helpers.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.hotspot.OakHotspot;

public final class OakLogger {
    private OakLogger() {}

    private static volatile Logger logger;

    private static volatile Handler consoleHandler;

    private static volatile Handler fileHandler;

    private static volatile Path logFileDirPath;

    private static final String logFileName;

    static {
        logFileName = OakAppConfigs.getProperty(OakAppDefaults.NAME.key())
                      + "-"
                      + String.valueOf(OakHotspot.getStartTime()) + ".log";
    }

    public static void init() {
        if (logger == null) {
            synchronized (OakLogger.class) {
                if (logger == null) {
                    logger = Logger.getLogger(OakLogger.class.getName());

                    consoleHandler = new ConsoleHandler();
                    logger.addHandler(consoleHandler);

                    try {
                        if (OakAppConfigs.containsKey(OakAppDefaults.LOG_FILE_DIR.key())) {
                            logFileDirPath = Paths.get(OakAppConfigs.getProperty(OakAppDefaults.LOG_FILE_DIR.key()));

                            if (Files.exists(logFileDirPath, LinkOption.NOFOLLOW_LINKS) == false) {
                                Files.createDirectory(logFileDirPath);
                            }

                            fileHandler = new FileHandler(logFileDirPath.resolve(Paths.get(logFileName)).toString(), true);
                        } else {
                            fileHandler = new FileHandler(OakAppDefaults.LOG_FILE_DIR.value(), true);
                        }

                        fileHandler.setFormatter(new OakFormatter());
                        logger.addHandler(fileHandler);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, "Configuration for log file could not be found or invalid, only print ont console.");
                    }

                    logger.log(Level.INFO, ">>>>> LOG START <<<<<");
                }
            }
        }
    }

    public static synchronized void log(Level level,
                                        String message) {
        logger.log(level, message);
    }

    public static synchronized void stop() {
        logger.log(Level.INFO, ">>>>> LOG END <<<<<");
    }

    public static void main(String[] args) {
        OakLogger.init();
        OakLogger.log(Level.INFO, "test");
    }
}
