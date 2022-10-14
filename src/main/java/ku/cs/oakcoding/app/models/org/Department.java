package ku.cs.oakcoding.app.models.org;

import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.models.complaints.CaseAssignable;
import ku.cs.oakcoding.app.models.users.StaffUser;

public class Department
        implements CaseAssignable {

    private final Organization organization;

    protected final StringProperty departmentID;

    protected final StringProperty departmentName;

    protected final ObservableSet<StaffUser> departmentStaffMembers;

    private final ReadOnlySetWrapper<String> asssignedCategories;

    public Department(Organization organization, String departmentID, String departmentName, Set<StaffUser> departmentStaffMembers) {

        this.organization = organization;
        this.departmentID = new SimpleStringProperty(departmentID);
        this.departmentName = new SimpleStringProperty(departmentName);
        this.departmentStaffMembers = FXCollections.observableSet();
        this.asssignedCategories = new ReadOnlySetWrapper<>();

        if (Objects.nonNull(departmentStaffMembers)) {
            this.departmentStaffMembers.addAll(departmentStaffMembers);
        }
    }

    public final String getDepartmentName() {
        return this.departmentName.get();
    }

    @Override
    public void assign(String categoryName) {
        this.asssignedCategories.add(categoryName);
    }
}
