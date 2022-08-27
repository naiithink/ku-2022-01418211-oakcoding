package ku.cs.oakcoding.app.models;

public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String picturePath;

    public User(String username, String password, String firstname, String lastname, String picturePath){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picturePath = picturePath;
    }

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
}
