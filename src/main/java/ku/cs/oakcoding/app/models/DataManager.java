package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.Constants.DataType;
import ku.cs.oakcoding.app.models.User.DataList;
import ku.cs.oakcoding.app.models.User.User;
import ku.cs.oakcoding.app.services.DataSource.DataSourceCSV;
import ku.cs.oakcoding.app.services.FactoryDatabase;

import java.util.HashMap;

interface IDBehavior<T> {
    T getValue(String keys);
    boolean containKeys(String keys);
    boolean login(String username, String password);

}

public class DataManager<T> {

    public static Object getValue(DataType dataType,String keys){
        switch (dataType){
            case USER : return BehaviorSelector.getValue(new UserBehavior(),keys);
            default: return null;
        }
    }

    public static boolean containKeys(DataType dataType, String keys){
        switch (dataType){
            case USER : return BehaviorSelector.containkeys(new UserBehavior(),keys);
            default: return false;
        }
    }

    public static boolean login(DataType dataType, String username, String password){
        switch (dataType){
            case USER : return BehaviorSelector.login(new UserBehavior(),username,password);
            default: return false;
        }
    }
}

class BehaviorSelector {
    public static Object getValue(IDBehavior idBehavior, String keys){ return idBehavior.getValue(keys); }
    public static boolean containkeys(IDBehavior idBehavior, String keys){ return idBehavior.containKeys(keys);}
    public static boolean login(IDBehavior idBehavior, String username, String password) { return idBehavior.login(username,password);}
}

class UserBehavior implements IDBehavior<User>{

    @Override
    public User getValue(String keys) {
        if (containKeys(keys)){
            DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.USER);
            HashMap<String, Object> usersMap = ((DataList) dataSourceCSV.readData()).getUsersMap();
            return (User) usersMap.get(keys);
        }
        return null;
    }

    @Override
    public boolean containKeys(String keys) {
        DataSourceCSV dataSourceCSV = FactoryDatabase.getDataSource(DataType.USER);
        HashMap<String, Object> usersMap = ((DataList) dataSourceCSV.readData()).getUsersMap();
        if (usersMap.containsKey(keys)){
            return true;
        }
        return  false;
    }

    @Override
    public boolean login(String username, String password) {
        if (containKeys(username)) {
            User user = getValue(username);
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}


