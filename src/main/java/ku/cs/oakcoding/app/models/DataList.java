/**
 * @file DataList.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import java.util.Map;
import java.util.TreeMap;

public class DataList {
    private Map<String, User> dataMap;

    public DataList(){
        dataMap = new TreeMap<>();
    }

    public DataList(Map<String, User> dataMap){
        this.dataMap = dataMap;
    }

    public void addUserMap(String key, User obj){
        dataMap.put(key,obj);
    }
    public void removeUserMap(String key) { dataMap.remove(key); }

    public User getUser(String userName) { return dataMap.get(userName); }
    public Map<String, User> getUsersMap() {return dataMap;}
}





