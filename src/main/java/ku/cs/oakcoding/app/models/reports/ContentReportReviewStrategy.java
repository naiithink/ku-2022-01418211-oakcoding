package ku.cs.oakcoding.app.models.reports;

import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.services.IssueManagerStatus;
import ku.cs.oakcoding.app.services.IssueService;

public class ContentReportReviewStrategy
        implements ReviewStrategy<AdminUser> {

    private final String REPORT_ID;

    public ContentReportReviewStrategy(String reportID) {
        this.REPORT_ID = reportID;
    }

    @Override
    public boolean approve(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        if (IssueService.getIssueManager().deleteComplaint(admin, REPORT_ID).equals(IssueManagerStatus.SUCCESS)) {
            IssueService.getIssueManager().getReport(REPORT_ID).setStatus(ReportStatus.CLOSED, "(Auto-generated message) Complaint has been deleted");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deny(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        IssueService.getIssueManager().getReport(REPORT_ID).setStatus(ReportStatus.CLOSED, "(Auto-generated message) Report has been ignored");
        return true;
    }

    @Override
    public String toString() {
        return "ContentReportReviewStrategy [REPORT_ID=" + REPORT_ID + "]";
    }
}
