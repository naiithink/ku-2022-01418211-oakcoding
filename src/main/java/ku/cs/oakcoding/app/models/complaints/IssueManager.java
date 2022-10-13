package ku.cs.oakcoding.app.models.complaints;

public sealed abstract class IssueManager<T extends Issue>
        permits ComplaintManager,
                ReportManager {

    public String getIDOf(T issue) {
        return issue.getIssueId();
    }
}
