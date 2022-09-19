package ku.cs.oakcoding.app.models.User;

import java.util.HashMap;

public class DataList {
    private HashMap<String, Object> DataMap;

    public DataList(){
        DataMap = new HashMap<>();
    }

    public DataList(HashMap<String,Object> DataMap){
        this.DataMap = DataMap;
    }

    public void addUserMap(String key, Object obj){
        DataMap.put(key,obj);
    }
    public void removeUserMap(String key) { DataMap.remove(key); }
    public HashMap<String,Object> getUsersMap() {return DataMap;}
}





