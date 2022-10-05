package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

public final class AdminUser
        extends User {

    public AdminUser(Roles role,
                     String firstName,
                     String lastName,
                     Path profileImagePath,
                     String username,
                     String password) {

        super(role, firstName, lastName, profileImagePath, username, password);
    }
}
