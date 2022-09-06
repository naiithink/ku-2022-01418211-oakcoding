package ku.cs.oakcoding.app.models.User;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<User>();
    }

    public void addUser(User user){
        users.add(user);
    }

    public ArrayList<User> getUsers(){
        return users;
    }

}
