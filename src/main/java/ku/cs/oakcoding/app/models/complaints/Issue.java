package ku.cs.oakcoding.app.models.complaints;

import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public sealed abstract class Issue
        permits Complaint,
                Report {

    protected final String ISSUE_ID;

    protected final String AUTHOR_UID;

    protected final ReadOnlyStringWrapper subject;

    protected final ReadOnlyStringWrapper description;

    protected final ReadOnlyMapWrapper<IssueStatus, Long> log;

    public Issue(final String issueID, final String authorUID, final String subject, final String description) {
        if (Objects.isNull(subject)) {
            OakLogger.log(Level.SEVERE, "Cannot create new " + this.getClass().getSimpleName() + " because 'subject' is null");

            System.exit(1);
        }

        this.ISSUE_ID = issueID;
        this.AUTHOR_UID = authorUID;
        this.subject = new ReadOnlyStringWrapper(this, "subject", subject);
        this.description = new ReadOnlyStringWrapper(this, "content", description);
        this.log = new ReadOnlyMapWrapper<>(this, "log", FXCollections.observableHashMap());
    }

    public String getIssueId() {
        return ISSUE_ID;
    }

    public String getAuthorUID() {
        return AUTHOR_UID;
    }

    /**
     * @todo get userName from UID
     */
    // public String getAuthorUserName() {
    //     return ;
    // }

    public String getSubject() {
        return subject.get();
    }

    public String getDescription() {
        return description.get();
    }

    public ReadOnlyStringProperty getReadOnlySubjectPropery() {
        return subject.getReadOnlyProperty();
    }

    public ReadOnlyStringProperty getReadOnlyDescriptionProperty() {
        return description.getReadOnlyProperty();
    }
}
