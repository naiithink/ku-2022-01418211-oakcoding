package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

public abstract class User {

    private Roles role;

    private String firstName;

    private String lastName;

    private Path profileImagePath;

    private String password;

    public User(Roles role,
                String firstName,
                String lastName,
                Path profileImagePath,
                String password) {

        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImagePath = profileImagePath;
        this.password = password;
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

    public boolean verifyPassword(String seed) {
        if (password.equals(seed)) {
            return true;
        }

        return false;
    }
}
