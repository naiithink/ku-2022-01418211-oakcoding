package ku.cs.oakcoding.app.models;

import java.nio.file.Path;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;

public abstract class User {

    private Roles role;

    private String firstName;

    private String lastName;

    private Path profileImagePath;

    private String username;

    private String password;

    public User(Roles role,
                String firstName,
                String lastName,
                Path profileImagePath,
                String username,
                String password) {

        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImagePath = profileImagePath;
        this.username = username;
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

    public String getUsername() { return username;}

    public String getPassword() { return password;}

    public boolean verifyPassword(String seed) {
        if (password.equals(seed)) {
            return true;
        }

        return false;
    }

    public String getQuoteFormat(Object o){
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    public String formatCSV() {
        return null;
    }
}
