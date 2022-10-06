/**
 * @file As.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05; file name
 *      2. (CASE) naiithink, 2022-10-06; comments
 */

//package ku.cs.oakcoding.app.models;
//
//
//import ku.cs.oakcoding.app.Constants.Status;
//import ku.cs.oakcoding.app.models.User.User;
//import ku.cs.oakcoding.app.models.User.UserList;
//import ku.cs.oakcoding.app.models.picture.Picture;
//import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
//import ku.cs.oakcoding.app.services.DataSource.UserDataSourceCSV;
//import ku.cs.oakcoding.app.Constants.DataType;
//import ku.cs.oakcoding.app.services.FactoryDatabase;
//
//import java.util.HashMap;
//
//public class DataManager {
//    public DataManager(){
//    }
//
//
//    //
//    public boolean doRegister(String userName, String password, String confirmPassword, String firstName, String lastName) {
//        if (firstName != null && lastName != null && userName != null && password != null && confirmPassword != null) {
//            DataSourceCSV dataSourceCSV = new FactoryDatabase().getDataSource(DataType.USER);
//            HashMap<String, User> usersMap = ((UserList) dataSourceCSV.readData()).getUsersMap();
//            if (password.equals(confirmPassword) && !usersMap.containsKey(userName)) {
//                return true;
//            }
//            return false;
//
//        }
//        return false;
//    }
//
//    public boolean doLogin(String userName, String password) {
//        if (userName != null && password != null) {
//            DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.USER);
//            HashMap<String, User> usersMap = ((UserList) dataSourceCSV.readData()).getUsersMap();
//            return usersMap.containsKey(userName);
//        }
//        return false;
//    }
////
////    public boolean changePassword(String userName, String password, String newPassword){
////        if (userName != null && password != null && newPassword != null){
////            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
////            if (dataSourceCSV.checkID(userName,password,"All")){
////                return true;
////            }
////            else {
////                System.err.println("There is no ID");
////                return false;
////            }
////        }
////        else {
////            System.err.println("can't be NULL");
////            return false;
////        }
////    }
////
////    public User getUser(String userName, String password){
////        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
////        User user = dataSourceCSV.readThatData(userName,password);
////        return user;
////    }
//
//    public void regisID(String userName, String password, String picturePath, String firstName, String lastName){
//        User user = new User(Status.UNBAN,userName,password,firstName,lastName,picturePath);
//        DataSourceCSV userDataSource = FactoryDatabase.getDataSource(DataType.USER);
//        HashMap<String,User> userHashMap = ((UserList) userDataSource.readData()).getUsersMap();
//        userHashMap.put(userName,user);
//
//        UserList newUserList = new UserList(userHashMap);
//        userDataSource.clearData();
//        userDataSource.writeData(newUserList);
//
//    }
//
//    //
//    public String writePicture(String name,String pictureFrom){
//        DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.PICTURE);
//        Picture newPic = new Picture(pictureFrom,name);
//        dataSourceCSV.writeData(newPic);
//        newPic = (Picture) dataSourceCSV.readData();
//        return newPic.getPicturePath();
//    }
//
//
//
//
//}
