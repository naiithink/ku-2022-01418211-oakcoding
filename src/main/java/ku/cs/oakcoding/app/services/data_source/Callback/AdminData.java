package ku.cs.oakcoding.app.services.data_source.callback;

import ku.cs.oakcoding.app.models.AdminUser;

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
    @Override
    public String getQuoteFormat(Object o) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public String formatCSV(Object o) {
        // TODO Auto-generated method stub
        return null;
    }
}
