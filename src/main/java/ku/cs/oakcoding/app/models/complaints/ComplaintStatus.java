package ku.cs.oakcoding.app.models.complaints;

public enum ComplaintStatus implements CaseStatus, IssueStatus<Complaint> {
    PENDING             ("Pending"),
    IN_PROGRESS         ("In-progress"),
    CLOSED              ("Closed"),
    NIL                 ("N/A");

    private final String prettyPrinted;

    private ComplaintStatus(String prettyPrinted) {
        this.prettyPrinted = prettyPrinted;
    }

    public static ComplaintStatus getDefault() {
        return NIL;
    }
}
