/**
 * @file DataCallback.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.callback;

/**
 * @todo Avoid using raw type <https://dev.java/learn/introducing-generics/#anchor_3>
 */
public class DataCallback {

    public static Object readData(ManageDataType manageDataType, String[] data) {
        return manageDataType.instanceCreate(data);
    }

    public static String readKey(ManageDataType manageDataType, String[] data) {
        return manageDataType.getKey(data);
    }

    public static String writeData(ManageDataType manageDataType, Object obj) {
        return manageDataType.instanceWrite(obj);
    }
}