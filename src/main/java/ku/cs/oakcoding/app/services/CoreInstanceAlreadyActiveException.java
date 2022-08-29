package ku.cs.oakcoding.app.services;

public class CoreInstanceAlreadyActiveException
        extends Exception {

    public CoreInstanceAlreadyActiveException() {}

    public CoreInstanceAlreadyActiveException(String message) {
        super(message);
    }

    public CoreInstanceAlreadyActiveException(String message, Throwable cause) {
        super(message, cause);
    }
}
