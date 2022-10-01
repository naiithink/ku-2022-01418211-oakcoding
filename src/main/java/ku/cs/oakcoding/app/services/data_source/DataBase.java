package ku.cs.oakcoding.app.services.data_source;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.models.data.User;
import ku.cs.oakcoding.app.services.data_source.data_source_roles.ConsumerData;

public class DataBase {

    public static Object readData(String [] data){
        Roles roleType = Roles.valueOf(data[0]);
        switch (roleType){
            case USER : return DataCallback.readData(new ConsumerData(),data);
            default: return null;
        }
    }

    public static String getKey(String [] data){
        Roles roleType = Roles.valueOf(data[0]);
        switch (roleType){
            case USER : return DataCallback.readKey(new ConsumerData(),data);
            default: return null;
        }
    }

    public static String writeData(Object obj){
        if (obj instanceof User) {
            assert obj instanceof User;
            return DataCallback.writeData(new ConsumerData(), obj);
        }
        else {
            return null;

        }
    }


}