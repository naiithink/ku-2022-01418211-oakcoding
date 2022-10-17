package ku.cs.oakcoding.app.models.org;

import ku.cs.oakcoding.app.services.OakID;

public final class Organizations {

    private Organizations() {}

    public static Organization newOrganization(String organizationName) {
        
        return new Organization(OakID.generate(Organization.class.getSimpleName()), organizationName);
    }
}
