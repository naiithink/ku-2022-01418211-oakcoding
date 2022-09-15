package ku.cs.oakcoding.app.models;


import ku.cs.oakcoding.app.models.User.Status;
import ku.cs.oakcoding.app.models.User.User;
import ku.cs.oakcoding.app.models.User.UserList;
import ku.cs.oakcoding.app.models.picture.Picture;
import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
import ku.cs.oakcoding.app.services.DataSource.UserDataSourceCSV;
import ku.cs.oakcoding.app.services.DataType;
import ku.cs.oakcoding.app.services.FactoryDatabase;

import java.util.HashMap;

public class DataManager {
    public DataManager(){
    }


    //
    public boolean doRegister(String username, String password, String confirmPassword, String firstname, String lastname) {
        if (firstname != null && lastname != null && username != null && password != null && confirmPassword != null) {
            DataSourceCSV dataSourceCSV = new FactoryDatabase().getDataSource(DataType.USER);
            HashMap<String, User> usersMap = ((UserList) dataSourceCSV.readData()).getUsersMap();
            if (password.equals(confirmPassword) && !usersMap.containsKey(username)) {
                return true;
            }
            return false;

        }
        return false;
    }

    public boolean doLogin(String username, String password) {
        if (username != null && password != null) {
            DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.USER);
            HashMap<String, User> usersMap = ((UserList) dataSourceCSV.readData()).getUsersMap();
            return usersMap.containsKey(username);
        }
        return false;
    }
//
//    public boolean changePassword(String username, String password, String newPassword){
//        if (username != null && password != null && newPassword != null){
//            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
//            if (dataSourceCSV.checkID(username,password,"All")){
//                return true;
//            }
//            else {
//                System.err.println("There is no ID");
//                return false;
//            }
//        }
//        else {
//            System.err.println("can't be NULL");
//            return false;
//        }
//    }
//
//    public User getUser(String username, String password){
//        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
//        User user = dataSourceCSV.readThatData(username,password);
//        return user;
//    }

    public void regisID(String username, String password, String picturePath, String firstname, String lastname){
        User user = new User(Status.UNBAN,username,password,firstname,lastname,picturePath);
        DataSourceCSV userDataSource = FactoryDatabase.getDataSource(DataType.USER);
        HashMap<String,User> userHashMap = ((UserList) userDataSource.readData()).getUsersMap();
        userHashMap.put(username,user);

        UserList newUserList = new UserList(userHashMap);
        userDataSource.clearData();
        userDataSource.writeData(newUserList);

    }

    //
    public String writePicture(String name,String pictureFrom){
        DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.PICTURE);
        Picture newPic = new Picture(pictureFrom,name);
        dataSourceCSV.writeData(newPic);
        newPic = (Picture) dataSourceCSV.readData();
        return newPic.getPicturePath();
    }




}
