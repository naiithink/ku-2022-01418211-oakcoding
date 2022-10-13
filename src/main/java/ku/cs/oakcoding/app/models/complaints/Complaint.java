/**
 * @file Complaint.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.complaints;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.FXCollections;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.StaffUser;

public non-sealed final class Complaint
        extends Issue
        implements Resolvable<Complaint> {

    private final ReadOnlySetWrapper<String> categories;

    private final ReadOnlyObjectWrapper<StaffUser> caseManager;

    private final ReadOnlyObjectWrapper<ComplaintStatus> caseStatus;

    private final BooleanBinding isResolved;

    private final ReadOnlyMapWrapper<Integer, Evidence> evidence;

    private final IntegerBinding evidenceCount;

    private final ReadOnlySetWrapper<String> voters;

    public Complaint(String issueID, String authorUID, String subject, String description, ComplaintStatus status) {
        super(issueID, authorUID, subject, description);

        this.categories = new ReadOnlySetWrapper<>();
        this.caseManager = new ReadOnlyObjectWrapper<>();
        this.caseStatus = new ReadOnlyObjectWrapper<>(this, "status");
        this.evidence = new ReadOnlyMapWrapper<>();
        this.voters = new ReadOnlySetWrapper<>(this, "voters", FXCollections.observableSet());

        if (Objects.nonNull(status)) {
            this.caseStatus.set(status);
        }  else {
            this.caseStatus.set(ComplaintStatus.getDefault());
            OakLogger.log(Level.WARNING, "Attempting to create new Complaint with null status");
        }

        this.isResolved = new BooleanBinding() {

            {
                super.bind(caseStatus);
            }

            @Override
            public boolean computeValue() {
                if (caseStatus.get().equals(ComplaintStatus.CLOSED)) {
                    return true;
                }

                return false;
            }
        };

        this.evidenceCount = new IntegerBinding() {

            {
                super.bind(evidence);
            }

            @Override
            public int computeValue() {
                return evidence.getSize();
            }
        };
    }

    public Complaint(String issueID, String authorUID, String subject, String description, ComplaintStatus status, Collection<? extends String> voters) {
        super(issueID, authorUID, subject, description);

        this.categories = new ReadOnlySetWrapper<>();
        this.caseManager = new ReadOnlyObjectWrapper<>();
        this.caseStatus = new ReadOnlyObjectWrapper<>(this, "status");
        this.evidence = new ReadOnlyMapWrapper<>();
        this.voters = new ReadOnlySetWrapper<>(this, "voters", FXCollections.observableSet());

        if (Objects.nonNull(status)) {
            this.caseStatus.set(status);
        }  else {
            this.caseStatus.set(ComplaintStatus.getDefault());
            OakLogger.log(Level.WARNING, "Attempting to create new Complaint with null status");
        }

        if (Objects.nonNull(voters))
            this.voters.addAll(voters);

        this.isResolved = new BooleanBinding() {

            {
                super.bind(caseStatus);
            }

            @Override
            public boolean computeValue() {
                if (caseStatus.get().equals(ComplaintStatus.CLOSED)) {
                    return true;
                }

                return false;
            }
        };

        this.evidenceCount = new IntegerBinding() {

            {
                super.bind(evidence);
            }

            @Override
            public int computeValue() {
                return evidence.getSize();
            }
        };
    }

    public Complaint(String id, String authorUserName, String subject, String description, ComplaintStatus status, String... voters) {
        super(id, authorUserName, subject, description);

        this.categories = new ReadOnlySetWrapper<>();
        this.caseManager = new ReadOnlyObjectWrapper<>();
        this.caseStatus = new ReadOnlyObjectWrapper<>(this, "status");
        this.evidence = new ReadOnlyMapWrapper<>();
        this.voters = new ReadOnlySetWrapper<>(this, "voters", FXCollections.observableSet());

        if (Objects.nonNull(status) == false) {
            OakLogger.log(Level.SEVERE, "Attempting to create new Complaint with null status");

            System.exit(1);
        }

        this.caseStatus.set(status);

        if (Objects.nonNull(voters)) {
            for (String voter : voters) {
                this.voters.add(voter);
            }
        }

        this.isResolved = new BooleanBinding() {

            {
                super.bind(caseStatus);
            }

            @Override
            public boolean computeValue() {
                if (caseStatus.get().equals(ComplaintStatus.CLOSED)) {
                    return true;
                }

                return false;
            }
        };

        this.evidenceCount = new IntegerBinding() {

            {
                super.bind(evidence);
            }

            @Override
            public int computeValue() {
                return evidence.getSize();
            }
        };
    }

    public String getComplaintID() {
        return super.getIssueId();
    }

    public boolean isResolved() {
        return isResolved.get();
    }

    public ReadOnlySetProperty<String> getReadOnlyCategoriesProperty() {
        return categories.getReadOnlyProperty();
    }

    public int getNumVote() {
        return voters.size();
    }

    public ReadOnlySetProperty<String> getVoters() {
        return this.voters.getReadOnlyProperty();
    }

    /**
     * @return      true if the voter has not already vote. Otherwise, false is returned.
     */
    public int vote(String userName) {
        if (super.AUTHOR_UID.equals(userName)) {
            return voters.size();
        }

        voters.add(userName);
        return voters.size();
    }

    /**
     * @return      true if the voter has not already vote. Otherwise, false is returned.
     * 
     * @see vote
     */
    public int unVote(String userName) {
        if (super.AUTHOR_UID.equals(userName)) {
            return voters.size();
        }

        voters.remove(userName);
        return voters.size();
    }

    public ComplaintStatus getStatus() {
        return caseStatus.get();
    }

    public ReadOnlyObjectProperty<ComplaintStatus> getReadOnlyStatusProperty() {
        return caseStatus.getReadOnlyProperty();
    }

    public ReadOnlySetProperty<String> getReadOnlyVotersProperty() {
        return voters.getReadOnlyProperty();
    }

    @Override
    public void resolve(Resolver<Complaint> resolver, IssueStatus<Complaint> resolveStatus) {
        if (resolver.getClass().isAssignableFrom(StaffUser.class)) {
            caseManager.set((StaffUser) resolver);
            caseStatus.set((ComplaintStatus) resolveStatus);
        }
    }
}
