package ku.cs.oakcoding.app.models.User;

import ku.cs.oakcoding.app.Constants.DataType;
import ku.cs.oakcoding.app.Constants.Status;
import ku.cs.oakcoding.app.models.DataManager;

// IMPORTANT NOTE! THIS CLASS WILL BE DELETED THIS IS JUST A PLACEHOLDER
// Sadly Found A better method ;(

public class Admin {
    private final DataType role = DataType.ADMIN;
    private String username;
    private String password;

    public Admin(Status BAN_Status,String username, String password, String firstname, String lastname, String picturePath){
        this.username = username;
        this.password = password;
    }

    public static User getValue(String keys){
        if (containID(keys)){
            return (User) DataManager.getValue(DataType.ADMIN,keys);
        }
        return null;
    }
    public static boolean containID(String keys){
        if (DataManager.containKeys(DataType.ADMIN,keys)){
            return true;
        }
        return false;
    }

    public static boolean login(String username, String password){
        if (DataManager.login(DataType.ADMIN,username,password)){
            return true;
        }
        return false;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return    role + ","
                + username + ","
                + password;
    }
}
