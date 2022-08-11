package ku.cs.app.models;

/**
 * OakLogger
 * (Singleton)
 *
 * เขียน activity log ลงในไฟล์ และ print ขึ้นบน console
 */
class OakLogger {

    /**
     * ระดับในการเขียน activity log
     *
     * - 0 SEVERE
     * - 1 WARNING
     * - 2 INFO
     * - 3 CONFIG
     * - 4 FINE
     * - 5 FINER
     * - 6 FINEST
     */
    private enum LoggingLevel {
        SEVERE,
        WARNING,
        INFO,
        CONFIG,
        FINE,
        FINER,
        FINEST
    };


    /**
     * Static OakLogger instance
     */
    private static OakLogger instance;


    /**
     * ระดับ threshold ในการเขียน activity log สำหรับ session ปัจจุบัน
     * defaults to \ref LoggingLevel.FINEST
     *
     * \see LoggingLevel
     */
    private static LoggingLevel thisSessionLoggingLevel = LoggingLevel.FINEST;


    /**
     * OakLogger constructor
     *
     * \see OakLogger
     */
    private OakLogger() {}


    /**
     * Gets OakLogger instance
     */
    public static OakLogger getInstance() {
        if (instance == null) {
            instance = new OakLogger();
        }

        return instance;
    }
}
