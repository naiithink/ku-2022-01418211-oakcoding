package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

public abstract class User {

    private Roles role;

    private String firstName;

    private String lastName;

    private Path profileImagePath;

    public User(Roles role,
                String firstName,
                String lastName,
                Path profileImagePath) {

        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImagePath = profileImagePath;
    }

    public Roles getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Path getProfileImagePath() {
        return profileImagePath;
    }
}
