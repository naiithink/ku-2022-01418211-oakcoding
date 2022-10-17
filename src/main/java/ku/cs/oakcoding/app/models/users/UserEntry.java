package ku.cs.oakcoding.app.models.users;

import java.nio.file.Path;

public class UserEntry
        extends BriefUserEntry {

    protected String firstName;

    protected String lastName;

    protected Path profileImagePath;

    protected long lastLogin;

    public UserEntry(String UID, Roles role, String userName, boolean isActive, int loginAttempt, String firstName,
            String lastName, Path profileImagePath, long lastLogin) {

        super(UID, role, userName, isActive, loginAttempt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImagePath = profileImagePath;
        this.lastLogin = lastLogin;
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

    public long getLastLogin() {
        return lastLogin;
    }

    @Override
    public String toString() {
        return "UserEntry [UID=" + UID + ", role=" + role + ", userName=" + userName + ", isActive=" + isActive
                + ", loginAttempt=" + loginAttempt + " firstName=" + firstName + ", lastName=" + lastName + ", profileImagePath=" + profileImagePath
                + ", lastLogin=" + lastLogin + "]";
    }
}
