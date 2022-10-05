package ku.cs.oakcoding.app.models;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class DataList {
    private Map<String, User> DataMap;

    public DataList(){
        DataMap = new TreeMap<>();
    }

    public DataList(Map<String, User> DataMap){
        this.DataMap = DataMap;
    }

    public void addUserMap(String key, User obj){
        DataMap.put(key,obj);
    }
    public void removeUserMap(String key) { DataMap.remove(key); }

    public User getUser(String username) { return DataMap.get(username); }
    public Map<String, User> getUsersMap() {return DataMap;}
}





