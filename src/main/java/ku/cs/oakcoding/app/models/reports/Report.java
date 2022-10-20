package ku.cs.oakcoding.app.models.reports;

import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.AdminUser;

public class Report {

    private final String REPORT_ID;

    private final ObjectProperty<ReportType> type;

    private final String AUTHOR_UID;

    private final String TARGET_ID;

    private final StringProperty description;

    private final ObjectProperty<ReportStatus> status;

    private final StringProperty result;

    private final ReviewStrategy<AdminUser> resolveStrategy;

    public Report(String reportID,
                  ReportType type,
                  String authorID,
                  String targetID,
                  String description,
                  ReportStatus status,
                  String result) {

        REPORT_ID = reportID;
        AUTHOR_UID = authorID;
        TARGET_ID = targetID;
        this.type = new SimpleObjectProperty<>(type);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleObjectProperty<>(status);
        this.result = new SimpleStringProperty(result);

        switch (this.type.get()) {
            case BEHAVIOR:
                this.resolveStrategy = new BehavioralReportReviewStrategy(this.REPORT_ID);
                break;
            case CONTENT:
                this.resolveStrategy = new ContentReportReviewStrategy(this.REPORT_ID);
                break;
            default:
                this.resolveStrategy = null;
                OakLogger.log(Level.SEVERE, "Internal error, unknown ReportType");
                System.exit(1);
        }
    }

    public String getReportID() {
        return REPORT_ID;
    }

    public String getAuthorID() {
        return AUTHOR_UID;
    }

    public String getTargetID() {
        return TARGET_ID;
    }

    public ReportType getType() {
        return type.get();
    }

    public String getDescription() {
        return description.get();
    }

    public ReportStatus getStatus() {
        return status.get();
    }

    public String getResult() {
        return this.result.get();
    }

    public boolean hasBeenResolved() {
        return this.status.get().equals(ReportStatus.RESOLVED);
    }

    public boolean hasBeenClosed() {
        return this.status.get().equals(ReportStatus.CLOSED);
    }

    public ReportResolvingStatus approve(AdminUser admin, String message) {
        if (Objects.isNull(admin)) {
            return ReportResolvingStatus.INVALID_ACCESS;
        } else if (hasBeenClosed()) {
            return ReportResolvingStatus.HAD_BEEN_CLOSED;
        } else if (hasBeenResolved()) {
            return ReportResolvingStatus.HAD_BEEN_RESOLVED;
        }

        if (this.resolveStrategy.approve(admin)) {
            setStatus(ReportStatus.RESOLVED, message);
            return ReportResolvingStatus.SUCCESS;
        }

        return ReportResolvingStatus.FAILURE;
    }

    public ReportResolvingStatus approve(AdminUser admin) {
        return approve(admin, "(Auto-generated message) User '" + TARGET_ID + "' has been suspended");
    }

    public ReportResolvingStatus deny(AdminUser admin, String message) {
        if (Objects.isNull(admin)) {
            return ReportResolvingStatus.INVALID_ACCESS;
        } else if (hasBeenClosed())
            return ReportResolvingStatus.HAD_BEEN_CLOSED;

        if (this.resolveStrategy.deny(admin)) {
            this.status.set(ReportStatus.CLOSED);
            this.result.set(message);
            return ReportResolvingStatus.SUCCESS;
        } else {
            return ReportResolvingStatus.FAILURE;
        }
    }

    public ReportResolvingStatus deny(AdminUser admin) {
        return deny(admin, "(Auto-generated message) Report '" + REPORT_ID + "' has not been approved");
    }

    protected ReportStatus setStatus(ReportStatus status, String result) {
        if (this.status.get().equals(ReportStatus.RESOLVED)) {
            OakLogger.log(Level.WARNING, "This report had been marked as resolved, its status is no longer be modifiable");
            return this.status.get();
        } else if (!result.isEmpty()) {
            OakLogger.log(Level.WARNING, "Report result can only be set when a report is about to be marked as resolved");
        }

        if (status.equals(ReportStatus.RESOLVED))
            this.result.set(result);

        this.status.set(status);

        return this.status.get();
    }

    @Override
    public String toString() {
        return "Report [REPORT_ID=" + REPORT_ID + ", type=" + type + ", AUTHOR_UID=" + AUTHOR_UID + ", TARGET_ID="
                + TARGET_ID + ", description=" + description + ", status=" + status + ", result=" + result
                + ", resolveStrategy=" + resolveStrategy + "]";
    }
}
