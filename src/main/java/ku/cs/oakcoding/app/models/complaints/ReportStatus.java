package ku.cs.oakcoding.app.models.complaints;

public enum ReportStatus implements IssueStatus<Report> {
    PENDING         ("Pending"),
    REVIEWED        ("Reviewed"),
    NIL             ("N/A");

    private final String prettyPrinted;

    private ReportStatus(String prettyPrinted) {
        this.prettyPrinted = prettyPrinted;
    }

    public static ReportStatus getDefault() {
        return NIL;
    }
}
