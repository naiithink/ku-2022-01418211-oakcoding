package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.models.users.Consumer;
import ku.cs.oakcoding.app.services.DataManager;

import java.nio.file.Path;

public final class ConsumerUser
        extends User {

    public ConsumerUser(Roles role,
                        String firstName,
                        String lastName,
                        Path profileImagePath,
                        String password) {

        super(role, firstName, lastName, profileImagePath, password);
    }


}
