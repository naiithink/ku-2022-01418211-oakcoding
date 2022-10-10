/**
 * @file DataBase.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.services.data_source.callback.*;

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


    public static String writeData(Object obj, FileCallBack writer) {
        if (writer == FileCallBack.USER) {
            return DataCallback.writeData(new UserData(), obj);
        }
        else if (writer == FileCallBack.ADMIN){
            return DataCallback.writeData(new AdminData(), obj);
        }
        else if (writer == FileCallBack.STAFF) {
            return DataCallback.writeData(new StaffData(), obj);
        }
        else if (writer == FileCallBack.CONSUMER) {
            return DataCallback.writeData(new ConsumerData(), obj);
        }
        else if (writer == FileCallBack.BAN){
            return DataCallback.writeData(new BanData(), obj);
        }
        else {
            return null;
        }
    }

}
