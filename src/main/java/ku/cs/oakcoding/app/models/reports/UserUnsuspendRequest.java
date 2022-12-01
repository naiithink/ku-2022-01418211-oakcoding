package ku.cs.oakcoding.app.models.reports;

import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.services.AccountService;

public class UserUnsuspendRequest {

    private final String UID;

    private final String REPORT_ID;

    private final String MESSAGE;

    private final UserUnsuspendRequestReviewStrategy REVIEW_STRATEGY;

    public UserUnsuspendRequest(String UID, String reportID, String message) {
        this.UID = UID;
        this.REPORT_ID = reportID;
        this.MESSAGE = message;
        this.REVIEW_STRATEGY = new UserUnsuspendRequestReviewStrategy(UID);
    }

    public String getUID() {
        return UID;
    }

    public String getReportID() {
        return REPORT_ID;
    }

    public String getUserName() {
        return AccountService.getUserManager().getUserNameOf(UID);
    }

    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public String toString() {
        return "UserUnsuspendRequest{" +
                "UID='" + UID + '\'' +
                ", REPORT_ID='" + REPORT_ID + '\'' +
                ", MESSAGE='" + MESSAGE + '\'' +
                ", REVIEW_STRATEGY=" + REVIEW_STRATEGY +
                '}';
    }

    public boolean approve(AdminUser admin) {
        return this.REVIEW_STRATEGY.approve(admin);
    }

    public boolean deny(AdminUser admin) {
        return this.REVIEW_STRATEGY.deny(admin);
    }
}
