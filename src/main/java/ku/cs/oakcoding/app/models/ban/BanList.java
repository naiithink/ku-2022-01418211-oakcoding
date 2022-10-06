/**
 * @file BanList.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.ban;

import java.util.Map;
import java.util.TreeMap;

public class BanList {
    private Map<String, Ban> banMap;

    public BanList(){
        banMap = new TreeMap<>();
    }

    public BanList(Map<String, Ban> banMap){
        this.banMap = banMap;
    }

    public void addBanMap(String key, Ban obj){
        banMap.put(key,obj);
    }
    public void removeBanMap(String key) { banMap.remove(key); }

    public Ban getBanUser(String userName) { return banMap.get(userName); }
    public Map<String, Ban> getUsersMap() {return banMap;}
}
