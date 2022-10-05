package ku.cs.oakcoding.app.models.Ban;

import ku.cs.oakcoding.app.models.User;

import java.util.Map;
import java.util.TreeMap;

public class BanList {
    private Map<String, Ban> BanMap;

    public BanList(){
        BanMap = new TreeMap<>();
    }

    public BanList(Map<String, Ban> BanMap){
        this.BanMap = BanMap;
    }

    public void addBanMap(String key, Ban obj){
        BanMap.put(key,obj);
    }
    public void removeBanMap(String key) { BanMap.remove(key); }

    public Ban getBanUser(String username) { return BanMap.get(username); }
    public Map<String, Ban> getUsersMap() {return BanMap;}
}
