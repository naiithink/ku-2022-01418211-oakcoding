package ku.cs.oakcoding.app.models.complaints;

import java.util.Map;
import java.util.Set;

import javafx.beans.binding.SetBinding;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.models.users.AdminUser;

public final class ComplaintCategory {

    private final ReadOnlyMapWrapper<String, ObservableSet<CaseAssignable>> categoryTable;

    private final SetBinding<String> categoryNames;

    private final ReadOnlyIntegerWrapper evidenceContentTypeConstraint;

    public ComplaintCategory() {
        this.categoryTable = new ReadOnlyMapWrapper<>(this, "categories", FXCollections.observableHashMap());
        this.categoryNames = new SetBinding<String>() {

            {
                super.bind(categoryTable);
            }

            @Override
            public ObservableSet<String> computeValue() {
                return FXCollections.observableSet(categoryTable.keySet());
            }
        };

        this.evidenceContentTypeConstraint = new ReadOnlyIntegerWrapper();
    }

    public ComplaintCategory(Map<String, Set<CaseAssignable>> categoryData, int evidenceContentTypeConstraint) {
        this.categoryTable = new ReadOnlyMapWrapper<>(this, "categories", FXCollections.observableHashMap());
        this.categoryNames = new SetBinding<String>() {

            {
                super.bind(categoryTable);
            }

            @Override
            public ObservableSet<String> computeValue() {
                return FXCollections.observableSet(categoryTable.keySet());
            }
        };

        for (String categoryName : categoryData.keySet()) {
            this.categoryTable.put(categoryName, FXCollections.observableSet(categoryData.get(categoryName)));

            for (CaseAssignable assignee : this.categoryTable.get(categoryName)) {
                assignee.assign(categoryName);
            }
        }

        this.evidenceContentTypeConstraint = new ReadOnlyIntegerWrapper(this, "evidenceContentTypeConstraint", evidenceContentTypeConstraint);
    }

    public ReadOnlyMapProperty<String, ObservableSet<CaseAssignable>> getReadOnlyCategoryProperty() {
        return categoryTable.getReadOnlyProperty();
    }

    public ReadOnlySetProperty<String> getReadOnlyCategoryNamesProperty() {
        return new ReadOnlySetWrapper<>(categoryNames).getReadOnlyProperty();
    }

    public ReadOnlyIntegerProperty getReadOnlyEvidenceContentTypeConstraint() {
        return evidenceContentTypeConstraint.getReadOnlyProperty();
    }

    public void addCategory(AdminUser admin, String categoryName, int evidenceContentTypeConstraint, CaseAssignable... caseManager) {
        this.categoryTable.put(categoryName, FXCollections.observableSet(Set.of(caseManager)));

        for (CaseAssignable assignee : caseManager) {
            this.categoryTable.get(categoryName).add(assignee);
            assignee.assign(categoryName);
        }
    }

    public void addCategory(AdminUser admin, String categoryName, int evidenceContentTypeConstraint, Set<CaseAssignable> caseManager) {
        this.categoryTable.put(categoryName, FXCollections.observableSet(caseManager));

        for (CaseAssignable assignee : caseManager) {
            this.categoryTable.get(categoryName).add(assignee);
            assignee.assign(categoryName);
        }
    }

    public void addEvidenceContentTypeConstraint(int evidenceContentTypeConstraint) {
        this.evidenceContentTypeConstraint.set(this.evidenceContentTypeConstraint.get() | evidenceContentTypeConstraint);
    }

    public void removeEvidenceContentTypeConstraint(int evidenceContentTypeConstraint) {
        this.evidenceContentTypeConstraint.set(this.evidenceContentTypeConstraint.get() & ~evidenceContentTypeConstraint);
    }

    public void addAssignee(AdminUser admin, String categoryName, CaseAssignable assignee) {
        this.categoryTable.get(categoryName).add(assignee);
        assignee.assign(categoryName);
    }
}
