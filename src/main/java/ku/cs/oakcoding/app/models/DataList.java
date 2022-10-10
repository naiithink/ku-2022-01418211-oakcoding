/**
 * @file DataList.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class DataList {
    private Set<User> dataSet;

    public DataList(){
        dataSet = new HashSet<>();
    }

    public DataList(HashSet<User> dataHashSet){
        this.dataSet = dataHashSet;
    }

    public void addUser(User user){
        dataSet.add(user);
    }
    public void removeUser(User user) { dataSet.remove(user); }
    public Set<User> getUsers() {return dataSet;}
}





