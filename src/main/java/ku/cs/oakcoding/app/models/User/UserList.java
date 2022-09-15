package ku.cs.oakcoding.app.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserList {
    private HashMap<String,User> usersMap;

    public UserList(){
        usersMap = new HashMap<>();
    }

    public UserList(HashMap<String,User> usersMap){
        this.usersMap = usersMap;
    }

    public void addUserMap(String key, User user){
        usersMap.put(key,user);
    }
    public HashMap<String,User> getUsersMap() {return usersMap;}
}
