/**
 * @file BanList.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.ban;

import java.util.HashSet;
import java.util.Set;

public class BanList {
    private Set<Ban> banSet;

    public BanList(){
        banSet = new HashSet<>();
    }

    public BanList(HashSet<Ban> banSet){
        this.banSet = banSet;
    }

    public void addBan(Ban banUser){ banSet.add(banUser); }
    public void removeBanMap(Ban banUser) { banSet.remove(banUser); }
    public Set<Ban> getUsersSet() {return banSet;}
}
