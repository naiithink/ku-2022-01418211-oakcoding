package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.services.data_source.Callback.ManageDataType;

public class DataCallback {
    public static Object readData(ManageDataType manageDataType, String [] data) { return manageDataType.instanceCreate(data);}

    public static String readKey(ManageDataType manageDataType,String [] data) {
        return manageDataType.getKey(data);
    }

    public static String writeData(ManageDataType manageDataType, Object obj){ return manageDataType.instanceWrite(obj);}
}
