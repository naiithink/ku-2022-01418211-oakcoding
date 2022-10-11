package ku.cs.oakcoding.app.services.data_source.callback;

import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.User;

import java.nio.file.Path;

public class UserProfileData implements ManageDataType<User>{
    public UserProfileData(){}
    @Override
    public User instanceCreate(String[] data) {
        if (Roles.valueOf(data[0]) == Roles.CONSUMER){
            return (User) DataCallback.readData(new ConsumerData(), data);
        }
        else if (Roles.valueOf(data[0]) == Roles.ADMIN){
            return (User) DataCallback.readData(new AdminData(), data);
        }
        else if (Roles.valueOf(data[0]) == Roles.STAFF){
            return (User) DataCallback.readData(new StaffData(), data);
        }
        else {
            return null;
        }
    }

    @Override
    public String instanceWrite(Object obj) {
        User user = (User) obj;
        if (user.getRole() == Roles.CONSUMER){
            return DataCallback.writeData(new ConsumerData(), obj);
        }
        else if (user.getRole() == Roles.ADMIN){
            return DataCallback.writeData(new AdminData(), obj);
        }
        else if (user.getRole() == Roles.STAFF){
            return DataCallback.writeData(new StaffData(), obj);
        }
        else {
            return null;
        }
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
