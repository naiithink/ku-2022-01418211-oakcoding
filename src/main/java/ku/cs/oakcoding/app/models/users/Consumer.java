package ku.cs.oakcoding.app.models.users;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.services.DataManager;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.CSV.DataSourceCSV;

public class Consumer {

    private final Roles role = Roles.USER;
    private BanStatus banStatus;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String picturePath;
    // private String type;

    public Consumer(BanStatus banStatus, String username, String password, String firstname, String lastname, String picturePath){
        this.banStatus = banStatus;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picturePath = picturePath;
    }

    public static Consumer getValue(String keys){
        if (containID(keys)){
            return (Consumer) DataManager.getValue(Roles.USER,keys);
        }
        return null;
    }
    public static boolean containID(String keys){
        if (DataManager.containKeys(Roles.USER,keys)){
            return true;
        }
        return false;
    }

    public static boolean login(String username, String password){
        if (DataManager.login(Roles.USER,username,password)){
            return true;
        }
        return false;
    }

    public void register(String username){
        if (!containID(username)){
            DataSourceCSV dataSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.User);

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

    public BanStatus getBanStatus(){ return banStatus;}

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
                + banStatus + ","
                + username + ","
                + password + ","
                + firstname + ","
                + lastname + ","
                + picturePath;
    }
}
