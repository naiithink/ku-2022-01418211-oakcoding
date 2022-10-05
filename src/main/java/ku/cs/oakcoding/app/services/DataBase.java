/**
 * @file DataBase.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.services.data_source.callback.ConsumerData;
import ku.cs.oakcoding.app.services.data_source.callback.DataCallback;

/**
 * @todo Assert null before Array[subscription]
 */
public class DataBase {

    public static Object readData(String[] data) {
        Roles roleType = Roles.valueOf(data[0]);
        switch (roleType) {
            case CONSUMER:
                return DataCallback.readData(new ConsumerData(), data);
            default:
                return null;
        }
    }

    public static String getKey(String[] data) {
        Roles roleType = Roles.valueOf(data[0]);
        switch (roleType) {
            case CONSUMER:
                return DataCallback.readKey(new ConsumerData(), data);
            default:
                return null;
        }
    }

    public static String writeData(Object obj) {
        if (obj instanceof User) {
            assert obj instanceof User;
            return DataCallback.writeData(new ConsumerData(), obj);
        } else {
            return null;

        }
    }

}
