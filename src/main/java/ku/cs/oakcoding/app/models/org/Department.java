package ku.cs.oakcoding.app.models.org;

import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.complaints.CaseAssignable;
import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.services.AccountService;

public class Department
        implements CaseAssignable {

    protected final String DEP_ID;

    protected final ReadOnlyStringWrapper departmentName;

    protected final ReadOnlyStringWrapper leaderStaffMemberID;

    protected final ObservableSet<String> departmentStaffMembers;

    private final ObservableSet<String> asssignedCategories;

    public Department(String departmentID,
                      String departmentName) {

        this.DEP_ID = departmentID;
        this.departmentName = new ReadOnlyStringWrapper(departmentName);
        this.leaderStaffMemberID = new ReadOnlyStringWrapper(new String());
        this.departmentStaffMembers = FXCollections.observableSet();
        this.asssignedCategories = FXCollections.observableSet();
    }

    public Department(String departmentID,
                      String departmentName,
                      String leaderStaffMemberID,
                      Set<String> departmentStaffMembers,
                      Set<String> asssignedCategories) {

        this.DEP_ID = departmentID;
        this.departmentName = new ReadOnlyStringWrapper(departmentName);
        this.leaderStaffMemberID = new ReadOnlyStringWrapper(leaderStaffMemberID);
        this.departmentStaffMembers = FXCollections.observableSet();
        this.asssignedCategories = FXCollections.observableSet();

        for (String staff : departmentStaffMembers)
            this.departmentStaffMembers.add(staff);

        if (Objects.nonNull(asssignedCategories))
            this.asssignedCategories.addAll(asssignedCategories);
    }

    public final String getDepartmentID() {
        return DEP_ID;
    }

    public String getDepartmentName() {
        return this.departmentName.get();
    }

    public ReadOnlyStringProperty getDepartmentNamePropertyReadOnly() {
        return this.departmentName.getReadOnlyProperty();
    }

    public final boolean renameDepartmentTo(final String newDepartmentName) {
        if (Objects.isNull(newDepartmentName)) {
            OakLogger.log(Level.SEVERE, "'newDepartmentName' is null");

            return false;
        }

        this.departmentName.set(newDepartmentName);

        return true;
    }

    public String getLeaderStaffMemberID() {
        if (this.leaderStaffMemberID.isEmpty().get()) {
            OakLogger.log(Level.WARNING, "This organization's leader has not been assigned, null is returned");
            return null;
        }

        return this.leaderStaffMemberID.get();
    }

    public boolean hasLeaderStaffMember() {
        return !this.leaderStaffMemberID.get().equals("NIL");
    }

    public boolean assignLeaderStaffMemberTo(String staffUID) {
        if (!AccountService.getUserManager().userExists(staffUID)) {
            OakLogger.log(Level.SEVERE, "'staffID' does not exist");
            return false;
        }

        this.leaderStaffMemberID.set(staffUID);

        return true;
    }

    public boolean changeLeaderStaffMemberTo(String staffUID) {
        return assignLeaderStaffMemberTo(staffUID);
    }

    public ReadOnlyStringProperty getLeaderStaffMemberIDPropertyReadOnly() {
        return this.leaderStaffMemberID.getReadOnlyProperty();
    }

    public ObservableSet<String> getStaffMemberSetProperty() {
        return this.departmentStaffMembers;
    }

    public void addStaffMember(String staffUID) {
        System.out.println(this.departmentStaffMembers.getClass().getSimpleName());
        System.out.println(this.departmentStaffMembers == null);

        System.out.println("""
                lll
                """);
        this.departmentStaffMembers.add(staffUID);
        System.out.println("""
                qqq
                """);
    }

    public void removeStaffMember(String staffUID) {
        this.departmentStaffMembers.remove(staffUID);
    }

    public boolean containsStaffMember(String staffUID) {
        return departmentStaffMembers.contains(staffUID);
    }

    public ObservableSet<String> getAsssignedCategoriesSetProperty() {
        return this.asssignedCategories;
    }

    @Override
    public boolean wasAssigned(String categoryName) {
        return this.asssignedCategories.contains(categoryName);
    }

    @Override
    public void assign(String categoryName) {
        this.asssignedCategories.add(categoryName);
    }

    @Override
    public void removeAssignment(String categoryName) {
        this.asssignedCategories.remove(categoryName);
    }

    @Override
    public String toString() {
        return "Department [DEP_ID=" + DEP_ID + ", departmentName=" + departmentName + ", leaderStaffMemberID="
                + leaderStaffMemberID + ", departmentStaffMembers=" + departmentStaffMembers + ", asssignedCategories="
                + asssignedCategories + "]";
    }
}