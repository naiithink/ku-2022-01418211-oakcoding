package ku.cs.oakcoding.app.models.complaints;

public enum ContentType {
    IMAGE           (0b000001),
    VIDEO           (0b000010),
    AUDIO           (0b000100),
    REGULAR_FILE    (0b001000);

    private final int mask;

    private ContentType(final int mask) {
        this.mask = mask;
    }
}
