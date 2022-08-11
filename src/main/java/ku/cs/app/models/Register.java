package ku.cs.app.models;

public class Register {
    private String username;
    private String password;

    public Register(){
        this.username = null;
        this.password = null;

    }


    public boolean doLogin(){
        if (username.equals("adminTEST") && password.equals("adminTEST")){
            return true;
        }
        else {
            return false;
        }
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }



    public void doLogOut(){
        this.username = null;
        this.password = null;
    }







}
