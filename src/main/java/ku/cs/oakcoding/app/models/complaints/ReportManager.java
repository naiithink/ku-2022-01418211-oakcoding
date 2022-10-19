package ku.cs.oakcoding.app.models.complaints;

import java.util.Objects;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ku.cs.oakcoding.app.helpers.id.OakID;

/**
 * @include ComplaintManager
 * 
 * @todo Consider using singleton?
 */
public non-sealed class ReportManager
        extends IssueManager<Report> {

    private ComplaintManager complaintManager;

    private ObservableMap<String, Report> reports = FXCollections.observableHashMap();

    public ReportManager(ComplaintManager complaintManager) {
        this.complaintManager = complaintManager;
    }

    public boolean init(ComplaintManager complaintManager) {
        if (Objects.nonNull(complaintManager)) {
            this.complaintManager = complaintManager;

            return true;
        }

        return false;
    }

    public boolean reportExists(String reportID) {
        return reports.containsKey(reportID);
    }

    public boolean checkPeer(ComplaintManager complaintManager) {
        return this.complaintManager.equals(complaintManager);
    }

    public void fileNewReport(ReportType type, String authorUID, String subject, String content, Object target, ReportStatus status) {
        addReport(new Report(type, OakID.generate(Report.class.getSimpleName()), authorUID, subject, content, target, status));
    }

    public void addReport(Report report) {
        reports.put(report.getReportID(), report);
    }

    public ReadOnlyMapProperty<String, Report> getReadOnlyReportList() {
        return new ReadOnlyMapWrapper<>(reports);
    }


    public Object getTargetOf(final String reportID) {
        if (reportExists(reportID)) {
            return reports.get(reportID).getTarget();
        }

        return null;
    }
}
