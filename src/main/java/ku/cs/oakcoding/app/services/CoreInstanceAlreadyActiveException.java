package ku.cs.oakcoding.app.services;

public class CoreInstanceAlreadyActiveException
        extends Exception {

    public CoreInstanceAlreadyActiveException() {}

    public CoreInstanceAlreadyActiveException(String message) {
        super(message);
    }

    public CoreInstanceAlreadyActiveException(Throwable cause) {
        super(cause);
    }

    public CoreInstanceAlreadyActiveException(String message,
                                              Throwable cause) {
        super(message,
              cause);
    }

    public CoreInstanceAlreadyActiveException(String message,
                                              Throwable cause,
                                              boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message,
              cause,
              enableSuppression,
              writableStackTrace);
    }
}
