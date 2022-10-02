package ku.cs.oakcoding.app.models.users;

import java.util.HashMap;
import java.util.TreeMap;

public class DataList {
    private TreeMap<String, Object> DataMap;

    public DataList(){
        DataMap = new TreeMap<>();
    }

    public DataList(TreeMap<String, Object> DataMap){
        this.DataMap = DataMap;
    }

    public void addUserMap(String key, Object obj){
        DataMap.put(key,obj);
    }
    public void removeUserMap(String key) { DataMap.remove(key); }
    public TreeMap<String, Object> getUsersMap() {return DataMap;}
}





