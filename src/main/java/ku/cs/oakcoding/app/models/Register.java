package ku.cs.oakcoding.app.models;

public class Register {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
    private String picturePath;

    public Register(){
    }

    public boolean doLogin() {
        if (firstname != null && lastname != null && username != null && password != null && confirmPassword != null) {
            if (checkPasswordEqual(this.password, this.confirmPassword)) {
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

    // getters
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPicturePath() {
        return picturePath;
    }

    // setters

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
