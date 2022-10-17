/**
 * @file User.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class User {

    protected final String UID;

    protected String userName;

    protected Roles role;

    protected String firstName;

    protected String lastName;

    protected boolean usingDefaultProfileImage;

    protected String profileImageExtension;

    protected Path profileImagePath;

    public User(final String UID,
                String userName,
                Roles role,
                String firstName,
                String lastName,
                boolean usingDefaultProfileImage,
                String profileImageExtension,
                Path profileImagePath) {

        this.UID = UID;
        this.userName = userName;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.usingDefaultProfileImage = usingDefaultProfileImage;
        this.profileImageExtension = profileImageExtension;
        this.profileImagePath = profileImagePath;
    }

    public String getUID() {
        return UID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Roles getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Path getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(Path profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public List<String> toCSVList() {
        List<String> res = new ArrayList<>();

        res.add(UID);
        res.add(userName);
        res.add(role.name());
        res.add(firstName);
        res.add(lastName);
        res.add(String.valueOf(usingDefaultProfileImage));
        res.add(profileImageExtension);

        return res;
    }

    @Override
    public String toString() {
        return "User [UID=" + UID + ", userName=" + userName + ", role=" + role + ", firstName=" + firstName
                + ", lastName=" + lastName + ", usingDefaultProfileImage=" + usingDefaultProfileImage
                + ", profileImageExtension=" + profileImageExtension + ", profileImagePath=" + profileImagePath + "]";
    }
}
