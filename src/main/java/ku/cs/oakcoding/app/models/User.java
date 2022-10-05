/**
 * @file User.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import java.nio.file.Path;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

public abstract class User {

    private Roles role;

    private String firstName;

    private String lastName;

    private Path profileImagePath;

    private String userName;

    private String password;

    public User(Roles role,
                String firstName,
                String lastName,
                Path profileImagePath,
                String userName,
                String password) {

        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileImagePath = profileImagePath;
        this.userName = userName;
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

    public String getUserName() { return userName;}

    public String getPassword() { return password;}

    public boolean verifyPassword(String seed) {
        if (password.equals(seed)) {
            return true;
        }

        return false;
    }

    public void setNewPassword(String password){
        this.password = password;
    }

    public void changePassword(String newPassword){
        DataSourceCSV userCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER);
        DataList users = (DataList) userCSV.readData();
        User user = users.getUser(this.userName);


        users.removeUserMap(this.userName);
        user.setNewPassword(newPassword);
        users.addUserMap(this.userName, user);

        userCSV.clearData();
        userCSV.writeData(users);

    }

}
