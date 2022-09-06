package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.models.User.User;
import ku.cs.oakcoding.app.models.User.UserList;
import ku.cs.oakcoding.app.services.DataSourceCSV;
import ku.cs.oakcoding.app.services.Picture.PictureSourceCSV;
import ku.cs.oakcoding.app.services.User.UserDataSourceCSV;
import ku.cs.oakcoding.app.services.User.UserDataSourceListCSV;

public class IDmanager {
    public IDmanager(){
    }

    public boolean doRegister(String username, String password, String confirmPassword, String firstname, String lastname) {
        if (firstname != null && lastname != null && username != null && password != null && confirmPassword != null) {
            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data","user.csv");
            if ((!dataSourceCSV.checkID(username,password,"Only")) && checkPasswordEqual(password,confirmPassword)){
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean checkPasswordEqual(String password, String confirmPassword){
        if (password.equals(confirmPassword)){
            return true;
        }
        return false;
    }

    public boolean doLogin(String username, String password) {
        if (username != null && password != null) {
            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
            return dataSourceCSV.checkID(username,password,"All");
        }
        return false;
    }

    public boolean changePassword(String username, String password, String newPassword){
        if (username != null && password != null && newPassword != null){
            UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
            if (dataSourceCSV.checkID(username,password,"All")){
                return true;
            }
            else {
                System.err.println("There is no ID");
                return false;
            }
        }
        else {
            System.err.println("can't be NULL");
            return false;
        }
    }

    public User getUser(String username, String password){
        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data", "user.csv");
        User user = dataSourceCSV.readThatData(username,password);
        return user;
    }

    public void regisID(String username, String password, String picturePath, String firstname, String lastname){
        User user = new User(username,password,firstname,lastname,picturePath);
        UserDataSourceCSV dataSourceCSV = new UserDataSourceCSV("data","user.csv");
        dataSourceCSV.writeData(user);

    }

    public String writeThenGetPicturePath(String name,String pictureFrom){
        PictureSourceCSV pictureSourceCSV = new PictureSourceCSV("picture",name,pictureFrom);
        pictureSourceCSV.writePicture();
        return pictureSourceCSV.getPicturePath();
    }




}
