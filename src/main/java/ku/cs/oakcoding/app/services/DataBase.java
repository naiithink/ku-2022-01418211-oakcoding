/**
 * @file DataBase.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.ModelCallBack;
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


    public static String writeData(Object obj, ModelCallBack writer) {
        if (writer == ModelCallBack.USER) {
            return DataCallback.writeData(new UserData(), obj);
        }
        if (writer == ModelCallBack.USERPROFILE) {
            return DataCallback.writeData(new UserProfileData(), obj);
        }
        else if (writer == ModelCallBack.COMPLAINT) {
            return DataCallback.writeData(new ConsumerData(), obj); // fix later
        }
        else if (writer == ModelCallBack.BAN) {
            return DataCallback.writeData(new BanData(), obj);
        }
        else {
            return null;
        }
    }

}
