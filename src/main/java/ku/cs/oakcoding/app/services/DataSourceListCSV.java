package ku.cs.oakcoding.app.services;

import ku.cs.oakcoding.app.models.User.UserList;

public interface DataSourceListCSV <T>{
    boolean checkID(String username,String password, String checkType);
    T readNotContainThatDataList(String username,String password);
    T readAllDataList();
    void writeData(T t);

    void clearData();
}
