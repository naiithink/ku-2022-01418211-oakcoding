package ku.cs.oakcoding.app.models.complaints;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import ku.cs.oakcoding.app.models.users.AdminUser;

public non-sealed class Report
        extends Issue
        implements Resolvable<Report> {

    private final ObjectProperty<ReportType> type = new SimpleObjectProperty<>();

    private final Object target;

    private final ReadOnlyObjectWrapper<ReportStatus> status = new ReadOnlyObjectWrapper<>();

    public Report(ReportType type, String reportID, String authorUID, String subject, String description, Object target, ReportStatus status) {
        super(reportID, authorUID, subject, description);

        this.type.set(type);
        this.target = target;
        this.status.set(status);
    }

    public String getReportID() {
        return super.getIssueId();
    }

    public ReadOnlyObjectProperty<ReportType> getType() {
        return type;
    }

    public Object getTarget() {
        return target;
    }

    public ReportStatus getStatus() {
        return status.get();
    }

    public ReadOnlyObjectProperty<ReportStatus> getReadOnlyStatusProperty() {
        return status.getReadOnlyProperty();
    }

    @Override
    public void resolve(Resolver<Report> resolver, IssueStatus<Report> status) {
        if (resolver.getClass().isAssignableFrom(AdminUser.class)) {
            this.status.set((ReportStatus) status);
        }
    }
}
