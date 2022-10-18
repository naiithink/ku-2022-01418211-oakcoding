package ku.cs.oakcoding.app.models.org;

import java.util.Set;

import ku.cs.oakcoding.app.helpers.id.OakID;

public final class Departments {
    private Departments() {}

    public static Department newDepartment(String departmentName) {
        return new Department(OakID.generate(Department.class.getSimpleName()), departmentName);
    }

    public static Department restoreDepartment(String departmentID,
                                               String departmentName,
                                               String leaderStaffMemberID,
                                               Set<String> departmentStaffMembers,
                                               Set<String> asssignedCategories) {

        return new Department(departmentID, departmentName, leaderStaffMemberID, departmentStaffMembers, asssignedCategories);
    }
}
