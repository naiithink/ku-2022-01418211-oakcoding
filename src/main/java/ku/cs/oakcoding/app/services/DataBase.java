/**
 * @file DataBase.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.services.data_source.callback.*;

/**
 * @todo Assert null before Array[subscription]
 */
public class DataBase {

    public static Object readData(String[] data, DataFile reader) {
        if (reader == DataFile.USER) {
            return DataCallback.readData(new UserData(), data);
        }
        else if (reader == DataFile.USERPROFILE){
            return DataCallback.readData(new UserProfileData(), data);
        }
        else if (reader == DataFile.COMPLAINT){
            return DataCallback.readData(new ConsumerData(), data);
        }
        else if (reader == DataFile.SUSPENDED){
            return DataCallback.readData(new SuspendedData(), data);
        }
        else {
            return null;
        }


    }
    public static String writeData(Object obj, DataFile writer) {
        if (writer == DataFile.USER) {
            return DataCallback.writeData(new UserData(), obj);
        }
        else if (writer == DataFile.USERPROFILE) {
            return DataCallback.writeData(new UserProfileData(), obj);
        }
        else if (writer == DataFile.COMPLAINT) {
            return DataCallback.writeData(new ConsumerData(), obj); // fix later
        }
        else if (writer == DataFile.SUSPENDED) {
            return DataCallback.writeData(new SuspendedData(), obj);
        }
        else {
            return null;
        }
    }

}
