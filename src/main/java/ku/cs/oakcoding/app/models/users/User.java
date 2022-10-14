/**
 * @file User.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.users;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;

public abstract class User {

    protected Roles role;

    protected String firstName;

    protected String lastName;

    protected ProfileImageState profileImageState;

    protected String userName;

    protected String password;
    protected boolean isActive;

    protected DataFile dataFile;

    public User(Roles role,
            String firstName,
            String lastName,
            String userName,
            String password,
            ProfileImageState profileImageState) {

        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImageState = profileImageState;
        this.userName = userName;
        this.password = password;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setProfileImageState(ProfileImageState profileImageState) {
        this.profileImageState = profileImageState;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
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

    public ProfileImageState getProfileImageState() {
        return profileImageState;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean verifyPassword(String seed) {
        if (password.equals(seed)) {
            return true;
        }

        return false;
    }

    public void setNewPassword(String password) {
        this.password = password;
    }

    public void changePassword(String newPassword) {
        // if (UserManager.changePassword(this.userName, newPassword)) {
        // DataSourceCSV userCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER);
        // DataList users = (DataList) userCSV.readData();
        // User user = users.getUser(this.userName);
        //
        //
        // users.removeUserMap(this.userName);
        // user.setNewPassword(newPassword);
        // users.addUserMap(this.userName, user);
        //
        // userCSV.clearData();
        // userCSV.writeData(users);
        // }

    }

    public boolean getIsActive() {
        return isActive;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profileImage=" + profileImageState +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", dataFile=" + dataFile +
                '}';
    }
}
