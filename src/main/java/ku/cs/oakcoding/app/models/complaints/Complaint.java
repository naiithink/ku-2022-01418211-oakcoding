package ku.cs.oakcoding.app.models.complaints;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.models.users.StaffUser;

public class Complaint {

    private final String COMPLAINT_ID;

    private final String AUTHOR_UID;

    private final StringProperty category;

    private final StringProperty subject;

    private final StringProperty description;

    private final ObjectProperty<Path> evidencePath;

    private final ObservableSet<String> voters;

    private final ObjectProperty<ComplaintStatus> status;

    private final BooleanProperty isResolved;

    private final StringProperty caseManagerUID;

    public Complaint(String complaintID,
                     String authorUID,
                     String category,
                     String subject,
                     String description,
                     Path evidencePath,
                     Set<String> voters,
                     ComplaintStatus status,
                     String caseManagerUID) {

        COMPLAINT_ID = complaintID;
        AUTHOR_UID = authorUID;
        this.category = new SimpleStringProperty(category);
        this.subject = new SimpleStringProperty(subject);
        this.description = new SimpleStringProperty(description);
        this.evidencePath = new SimpleObjectProperty<>();
        this.voters = FXCollections.observableSet();
        this.status = new SimpleObjectProperty<>(status);
        this.isResolved = new SimpleBooleanProperty(status.equals(ComplaintStatus.RESOLVED));
        this.caseManagerUID = new SimpleStringProperty();

        if (Objects.nonNull(evidencePath))
            this.evidencePath.set(evidencePath);

        if (Objects.nonNull(voters))
            this.voters.addAll(voters);

        if (Objects.nonNull(caseManagerUID))
            this.caseManagerUID.set(caseManagerUID);
    }

    public String getComplaintID() {
        return COMPLAINT_ID;
    }

    public String getAuthorUID() {
        return AUTHOR_UID;
    }

    public String getCategory() {
        return category.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public String getDescription() {
        return description.get();
    }

    public Path getEvidencePath() {
        return evidencePath.get();
    }

    public Set<String> getVoters() {
        return voters;
    }

    public ComplaintStatus getStatus() {
        return status.get();
    }

    public String getCaseManagerUID() {
        return caseManagerUID.get();
    }

    public void setCaseManager(String staffUID) {
        this.caseManagerUID.set(staffUID);
    }

    public void setCaseManager(StaffUser staffUser) {
        this.caseManagerUID.set(staffUser.getUID());
    }

    public boolean vote(String UID) {
        return this.voters.add(UID);
    }

    public boolean unVote(String UID) {
        return this.voters.remove(UID);
    }

    public long getVoteCount() {
        return this.voters.size();
    }

    public boolean setStatus(StaffUser caseManager, ComplaintStatus status) {
        if (Objects.isNull(caseManager)
            || Objects.isNull(status)
            || this.status.get().equals(status)) {

            return false;
        }

        this.caseManagerUID.set(caseManager.getUID());
        this.status.set(status);

        return true;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public boolean isResolved() {
        return isResolved.get();
    }

    @Override
    public String toString() {
        return "Complaint [COMPLAINT_ID=" + COMPLAINT_ID + ", AUTHOR_UID=" + AUTHOR_UID + ", category=" + category
                + ", subject=" + subject + ", description=" + description + ", evidencePath=" + evidencePath
                + ", voters=" + voters + ", status=" + status + ", isResolved=" + isResolved + ", caseManagerUID="
                + caseManagerUID + "]";
    }
}
