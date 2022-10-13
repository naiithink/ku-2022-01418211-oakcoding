package ku.cs.oakcoding.app.services.data_source.services_models.callback;

import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UserInfo;

public class UserInfoData implements ManageDataType<UserInfo>,FormatCSV{
    public UserInfoData(){}
    @Override
    public UserInfo instanceCreate(String[] data) {
        UserInfo userInfo = new UserInfo(data[0],
                                Roles.valueOf(data[1]),
                                Long.parseLong(data[2]));

        return userInfo;
    }

    @Override
    public String instanceWrite(Object obj) {
        return formatCSV(obj);
    }


    @Override
    public String getQuoteFormat(Object o) {
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    @Override
    public String formatCSV(Object o) {
        UserInfo user = (UserInfo) o;
        return getQuoteFormat(user.userName()) + ","
                + getQuoteFormat(user.role()) + ","
                + getQuoteFormat(user.registeredTime());
    }
}
