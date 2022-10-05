package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.models.AdminUser;
import ku.cs.oakcoding.app.models.BanStatus;
import ku.cs.oakcoding.app.models.ConsumerUser;
import ku.cs.oakcoding.app.models.users.Consumer;

public class AdminData implements ManageDataType<AdminUser> {
    public AdminData(){}
    @Override
    public AdminUser instanceCreate(String [] data) {
        return null;
    }
    @Override
    public String instanceWrite(Object obj) { return formatCSV(obj);}

    @Override
    public String getKey(String [] data){
        return data[2].trim();
    }
}
