package ku.cs.oakcoding.app.models.org;

import java.util.Collection;
import java.util.Objects;
import java.util.logging.Level;

import javafx.beans.binding.SetBinding;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.StaffUser;
import ku.cs.oakcoding.app.models.complaints.CaseAssignable;

public class Organization
        implements CaseAssignable {

    protected final StringProperty organizationID;

    protected final StringProperty organizationName;

    protected final ObservableMap<String, Department> departmentTable;

    private final SetBinding<String> departmentNames;

    private final SetBinding<Department> departments;

    private final ObservableSet<StaffUser> organizationStaffMembers;

    private final ReadOnlySetWrapper<String> asssignedCategories;

    public Organization(String organizationID, String organizationName) {
        this.organizationID = new SimpleStringProperty(this, "organizationID", organizationID);
        this.organizationName = new SimpleStringProperty(this, "organizationName", organizationName);
        this.departmentTable = FXCollections.observableHashMap();
        this.organizationStaffMembers = FXCollections.observableSet();
        this.asssignedCategories = new ReadOnlySetWrapper<>();

        this.departmentNames = new SetBinding<String>() {

            {
                super.bind(departmentTable);
            }

            @Override
            protected ObservableSet<String> computeValue() {
                return FXCollections.observableSet(departmentTable.keySet());
            }
        };

        this.departments = new SetBinding<Department>() {

            {
                super.bind(departmentTable);
            }

            @Override
            protected ObservableSet<Department> computeValue() {
                ObservableSet<Department> departments = FXCollections.observableSet();

                for (String depName : departmentTable.keySet()) {
                    departments.add(departmentTable.get(depName));
                }

                return departments;
            }
        };
    }

    /**
     * @note Restore
     */
    public Organization(String organizationID, String organizationName, Collection<? extends Department> departments) {
        this.organizationID = new SimpleStringProperty(organizationID);
        this.organizationName = new SimpleStringProperty(this, "organizationName", organizationName);
        this.departmentTable = FXCollections.observableHashMap();
        this.organizationStaffMembers = FXCollections.observableSet();
        this.asssignedCategories = new ReadOnlySetWrapper<>();

        for (Department dep : departments) {
            this.departmentTable.put(dep.getDepartmentName(), dep);
        }


        this.departmentNames = new SetBinding<String>() {

            {
                super.bind(departmentTable);
            }

            @Override
            protected ObservableSet<String> computeValue() {
                return FXCollections.observableSet(departmentTable.keySet());
            }
        };

        this.departments = new SetBinding<Department>() {

            {
                super.bind(departmentTable);
            }

            @Override
            protected ObservableSet<Department> computeValue() {
                ObservableSet<Department> departments = FXCollections.observableSet();

                for (String depName : departmentTable.keySet()) {
                    departments.add(departmentTable.get(depName));
                }

                return departments;
            }
        };
    }

    public String getOrganizationName() {
        return this.organizationName.get();
    }

    public void renameOrganization(final String organizationName) {
        if (Objects.isNull(organizationName)) {
            OakLogger.log(Level.SEVERE, "'organizationName' is null");

            return;
        }

        this.organizationName.set(organizationName);
    }

    public ObservableSet<Department> getAllDepartmentsUnmodifiable() {
        return FXCollections.unmodifiableObservableSet(departments);
    }

    public SetProperty<Department> getAllDepartmentsProperty() {
        return new ReadOnlySetWrapper<>(this.departments);
    }

    public boolean addDepartment(final Department department) {
        if (Objects.isNull(department)) {
            OakLogger.log(Level.SEVERE, "'department is null");

            return false;
        }

        this.departmentTable.put(department.getDepartmentName(), department);
        return true;
    }

    // public boolean containsDepartment(String departmentName) {
    //     return this.departments.
    // }

    @Override
    public void assign(String categoryName) {
        this.asssignedCategories.add(categoryName);
    }
}
