package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;

import java.nio.file.Path;

public final class AdminUser
        extends User {

    public AdminUser(Roles role,
                     String firstName,
                     String lastName,
                     Path profileImagePath,
                     String password) {

        super(role, firstName, lastName, profileImagePath, password);
    }
}
