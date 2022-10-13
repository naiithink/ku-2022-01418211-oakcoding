package ku.cs.oakcoding.app.models.org;

import ku.cs.oakcoding.app.services.OakID;

public final class Departments {

    private Departments() {}

    public static Department newDepartment(Organization organization, String departmentName) {
        return new Department(organization, OakID.generate(Department.class.getSimpleName()), departmentName, null);
    }
}
