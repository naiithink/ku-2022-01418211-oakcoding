package ku.cs.oakcoding.app.models.complaints;

import java.util.logging.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public class Report {

    private final String REPORT_ID;

    private final String AUTHOR_UID;

    private final String TARGET_ID;

    private final ObjectProperty<ReportType> type;

    private final StringProperty description;

    private final ObjectProperty<ReportStatus> status;

    private final StringProperty result;

    private final ReportResolveStrategy resolvingStrategy;

    public Report(String reportID, String authorID, String targetID, ReportType type,
            StringProperty description, ReportStatus status, String result, ReportResolveStrategy resolvingStrategy) {

        REPORT_ID = reportID;
        AUTHOR_UID = authorID;
        TARGET_ID = targetID;
        this.type = new SimpleObjectProperty<>(type);
        this.description = description;
        this.status = new SimpleObjectProperty<>(status);
        this.result = new SimpleStringProperty(result);
        this.resolvingStrategy = resolvingStrategy;
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

    public ObjectProperty<ReportType> getType() {
        return type;
    }

    public StringProperty getDescription() {
        return description;
    }

    public ObjectProperty<ReportStatus> getStatus() {
        return status;
    }

    public ReportStatus setStatus(ReportStatus status, String result) {
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
}
