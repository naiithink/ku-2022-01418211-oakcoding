package ku.cs.oakcoding.app.models.users;
import java.util.Map;
import java.util.TreeMap;

public class UsersMap {
    private Map<String, UserInfo> UsersMap;

    public UsersMap(){
        UsersMap = new TreeMap<>();
    }

    public UsersMap(Map<String, UserInfo> UsersMap){
        this.UsersMap = UsersMap;
    }

    public void addUserMap(String key, UserInfo obj){
        UsersMap.put(key,obj);
    }
    public void removeUserMap(String key) { UsersMap.remove(key); }

    public UserInfo getUser(String userName) { return UsersMap.get(userName); }
    public Map<String, UserInfo> getUsersMap() {return UsersMap;}



}
