package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

public final class StaffUser
        extends User {

    public StaffUser(Roles role,
                     String firstName,
                     String lastName,
                     Path profileImagePath,
                     String password) {

        super(role, firstName, lastName, profileImagePath, password);
    }
}
