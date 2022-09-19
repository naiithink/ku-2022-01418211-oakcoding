package ku.cs.oakcoding.app.models.User;

import ku.cs.oakcoding.app.Constants.Status;
import ku.cs.oakcoding.app.Constants.DataType;
import ku.cs.oakcoding.app.models.DataManager;
import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
import ku.cs.oakcoding.app.services.FactoryDatabase;

import java.util.HashMap;

public class User {

    private final DataType role = DataType.USER;
    private Status BAN_Status;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String picturePath;
    // private String type;

    public User(Status BAN_Status,String username, String password, String firstname, String lastname, String picturePath){
        this.BAN_Status = BAN_Status;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picturePath = picturePath;
    }

    public static User getValue(String keys){
        if (containID(keys)){
            return (User) DataManager.getValue(DataType.USER,keys);
        }
        return null;
    }
    public static boolean containID(String keys){
        if (DataManager.containKeys(DataType.USER,keys)){
            return true;
        }
        return false;
    }

    public static boolean login(String username, String password){
        if (DataManager.login(DataType.USER,username,password)){
            return true;
        }
        return false;
    }

    public void register(String username){
        if (!containID(username)){
            DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.USER);

        }
    }

//    public void changePassword(String username, String password, String newPassword){
//        IDmanager iDmanager = new IDmanager();
//        if (iDmanager.changePassword(username,password,newPassword)) {
//
//            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
//            User userTemp = dataSourceCSV.readThatData(username, password);
//
//            UserDataSourceListCSV dataSourceReal = new UserDataSourceListCSV("data", "user.csv");
//            UserDataSourceListCSV dataSourceTEMP = new UserDataSourceListCSV("data", "temp.csv");
//            dataSourceTEMP.clearData();
//
//            UserList userRealToTEMP = dataSourceReal.readNotContainThatDataList(username, password);
//            dataSourceTEMP.writeData(userRealToTEMP);
//            dataSourceReal.clearData();
//
//            UserList userTempToReal = dataSourceTEMP.readAllDataList();
//            dataSourceReal.writeData(userTempToReal);
//            dataSourceTEMP.clearData();
//
//            User userNewPassword = new User(userTemp.getUsername(), newPassword, userTemp.getFirstname(), userTemp.getLastname(), userTemp.getPicturePath());
//            dataSourceCSV.writeData(userNewPassword);
//
//        }
//    }
//
//    public void changeProfilePicture(String newPicturePath){
//        PictureSourceCSV pictureSourceCSV = new PictureSourceCSV("picture",this.username,this.picturePath);
//        pictureSourceCSV.deletePicture(this.picturePath);
//        this.picturePath = pictureSourceCSV.getPicturePath();
//
//        IDmanager iDmanager = new IDmanager();
//        this.picturePath = iDmanager.writeThenGetPicturePath(this.username,newPicturePath);
//
//        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
//        User userTemp = dataSourceCSV.readThatData(username, password);
//
//        UserDataSourceListCSV dataSourceReal = new UserDataSourceListCSV("data", "user.csv");
//        UserDataSourceListCSV dataSourceTEMP = new UserDataSourceListCSV("data", "temp.csv");
//        dataSourceTEMP.clearData();
//
//        UserList userRealToTEMP = dataSourceReal.readNotContainThatDataList(username, password);
//        dataSourceTEMP.writeData(userRealToTEMP);
//        dataSourceReal.clearData();
//
//        UserList userTempToReal = dataSourceTEMP.readAllDataList();
//        dataSourceReal.writeData(userTempToReal);
//        dataSourceTEMP.clearData();
//
//        User userNewPassword = new User(userTemp.getUsername(), userTemp.getPassword() ,userTemp.getFirstname(), userTemp.getLastname(), this.picturePath);
//        dataSourceCSV.writeData(userNewPassword);
//
//    }
//
//    public void clearProfileImage(){
//        PictureSourceCSV pictureSourceCSV = new PictureSourceCSV("picture",this.username,this.picturePath);
//        pictureSourceCSV.deletePicture(this.picturePath);
//        this.picturePath = pictureSourceCSV.getPicturePath();
//
//        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
//        User userTemp = dataSourceCSV.readThatData(username, password);
//
//        UserDataSourceListCSV dataSourceReal = new UserDataSourceListCSV("data", "user.csv");
//        UserDataSourceListCSV dataSourceTEMP = new UserDataSourceListCSV("data", "temp.csv");
//        dataSourceTEMP.clearData();
//
//        UserList userRealToTEMP = dataSourceReal.readNotContainThatDataList(username, password);
//        dataSourceTEMP.writeData(userRealToTEMP);
//        dataSourceReal.clearData();
//
//        UserList userTempToReal = dataSourceTEMP.readAllDataList();
//        dataSourceReal.writeData(userTempToReal);
//        dataSourceTEMP.clearData();
//
//        User userNewPassword = new User(userTemp.getUsername(), userTemp.getPassword() ,userTemp.getFirstname(), userTemp.getLastname(), this.picturePath);
//        dataSourceCSV.writeData(userNewPassword);
//    }

    public Status getBAN_STATUS(){ return BAN_Status;}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPicturePath() {
        return picturePath;
    }

    @Override
    public String toString() {
        return    role + ","
                + BAN_Status + ","
                + username + ","
                + password + ","
                + firstname + ","
                + lastname + ","
                + picturePath;
    }
}
