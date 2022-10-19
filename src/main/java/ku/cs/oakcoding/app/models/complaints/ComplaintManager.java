package ku.cs.oakcoding.app.models.complaints;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import ku.cs.oakcoding.app.helpers.id.OakID;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.helpers.id.OakID;

import java.util.Objects;

/**
 * @todo Consider using singleton?
 */
public non-sealed class ComplaintManager
        extends IssueManager<Complaint> {

    private ReportManager reportManager;

    private ObservableMap<String, Complaint> complaints = FXCollections.observableHashMap();

    public ComplaintManager() {}

    public boolean init(ReportManager reportManager) {
        if (Objects.nonNull(reportManager)) {
            this.reportManager = reportManager;

            return true;
        }

        return false;
    }

    public boolean complaintExists(String complaintID) {
        return complaints.containsKey(complaintID);
    }

    public boolean checkPeer(ReportManager reportManager) {
        return this.reportManager.equals(reportManager);
    }

    public void fileNewComplaint(ComplaintType type, String authorUserName, String subject, String description, ComplaintStatus status) {
        addComplaint(new Complaint(OakID.generate(Complaint.class.getSimpleName()), authorUserName, subject, description, null));
    }

    public void addComplaint(Complaint complaint) {
        complaints.put(complaint.getComplaintID(), complaint);
    }

    /**
     * A complaint can be deleted only by the AdminUser if and only if it had been reported by one or more ConsumerUser.
     */
    public void deleteComplaint(AdminUser adminUser, String reportID) {
        if (complaintExists(((Complaint) reportManager.getTargetOf(reportID)).getComplaintID())) {
            this.complaints.remove(reportManager.getTargetOf(reportID));
        }
    }

    public ReadOnlyMapProperty<String, Complaint> getReadOnlyComplaintList() {
        return new ReadOnlyMapWrapper<>(complaints);
    }
}
