package ku.cs.app.models;

public class RegisterUser {
    private String username;
    private String password;
    private String passwordConfirm;
    private String firstname;
    private String lastname;

    public RegisterUser(String username, String password, String passwordConfirm, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }
}
