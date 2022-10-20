package ku.cs.oakcoding.app.models.reports;

import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.UserManagerStatus;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueService;

public class BehavioralReportReviewStrategy
        implements ReviewStrategy<AdminUser> {

    private final String REPORT_ID;

    public BehavioralReportReviewStrategy(String reportID) {
        REPORT_ID = reportID;
    }

    @Override
    public boolean approve(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        if (AccountService.getUserManager().setActiveStatus(admin, AccountService.getUserManager().getUserNameOf(IssueService.getIssueManager().getReport(REPORT_ID).getTargetID()), false).equals(UserManagerStatus.SUCCESSFUL)) {
            IssueService.getIssueManager().getReport(REPORT_ID).setStatus(ReportStatus.RESOLVED, "(Auto-generated message) User '" + IssueService.getIssueManager().getReport(REPORT_ID).getTargetID() + "' status has been marked as suspended");
            return true;
        }

        return false;
    }

    @Override
    public boolean deny(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        IssueService.getIssueManager().getReport(REPORT_ID).setStatus(ReportStatus.RESOLVED, "(Auto-generated message) Report has been ignored");

        return true;
    }

    @Override
    public String toString() {
        return "BehavioralReportReviewStrategy [REPORT_ID=" + REPORT_ID + "]";
    }
}
