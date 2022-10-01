package ku.cs.oakcoding.app.services.data_source;

import ku.cs.oakcoding.app.constants.DataType;
import ku.cs.oakcoding.app.constants.Status;
import ku.cs.oakcoding.app.models.data.User;

interface ManageDataType<T> {
    public T instanceCreate(String [] data);
    public String instanceWrite(Object obj);
    public String getKey(String [] data);
}


public class DataBase {
//    private DataType dataType;
//    private String [] data;
//    private Object obj;
//    public DataBase(DataType dataType,String [] data){
//        this.dataType = dataType;
//        this.data = data;
//    }
//
//    public DataBase(DataType dataType,Object object){
//        this.dataType = dataType;
//        this.obj = object;
//    }

    public static Object readData(String [] data){
        DataType roleType = DataType.valueOf(data[0]);
        switch (roleType){
            case USER : return Data.readData(new UserData(),data);
            default: return null;
        }
//        if (dataType.equals(DataType.USER)){
//            ReadDataType readDataType = new UserReadData(data);
//            return ReadData.readData(readDataType);
//        }
//        else {
//            return null;
//        }
    }

    public static String getKey(String [] data){
        DataType roleType = DataType.valueOf(data[0]);
        switch (roleType){
            case USER : return Data.readKey(new UserData(),data);
            default: return null;
        }
//        if (dataType.equals(DataType.USER)){
//            keyMaker keyMaker = new UserReadData(data);
//            return ReadData.readKey(keyMaker);
//        }
//        else {
//            return null;
//        }

    }

    public static String writeData(Object obj){
        if (obj instanceof User) {
            assert obj instanceof User;
            return Data.writeData(new UserData(), obj);
        }
        else {
            return null;
//        if (obj instanceof Admin)
//        switch (roleType){
//            case USER :
//                assert obj instanceof User;
//                return Data.writeData(new UserData((User) obj));
//            default: return null;
        }
    }
}

class Data{
    public static Object readData(ManageDataType manageDataType,String [] data) { return manageDataType.instanceCreate(data);}

    public static String readKey(ManageDataType manageDataType,String [] data) {
        return manageDataType.getKey(data);
    }

    public static String writeData(ManageDataType manageDataType, Object obj){ return manageDataType.instanceWrite(obj);}
}


class UserData implements ManageDataType<User>{
    public UserData(){}
    @Override
    public User instanceCreate(String [] data) {
        User user = new User(Status.valueOf(data[1].trim()),data[2].trim(),data[3].trim(),data[4].trim(),data[5].trim(),data[6].trim());
        return user;
    }

    @Override
    public String instanceWrite(Object obj) { return ((User) obj).toString();}

    @Override
    public String getKey(String [] data){
        return data[2].trim();
    }
}

class AdminData {

}

class StaffData {

}

class ComplaintData {

}

