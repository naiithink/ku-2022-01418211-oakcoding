package ku.cs.oakcoding.app.services.data_source.services_models.callback;

import ku.cs.oakcoding.app.models.User;

public class UserData implements ManageDataType<User>,FormatCSV{
    public UserData(){}
    @Override
    public User instanceCreate(String[] data) {
        return null;
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
        User user = (User) o;
        return getQuoteFormat(user.getUserName()) + ","
                + getQuoteFormat(user.getRole());
    }
}
