package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.models.BanStatus;
import ku.cs.oakcoding.app.models.ConsumerUser;
import ku.cs.oakcoding.app.models.users.Consumer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ConsumerData implements ManageDataType<ConsumerUser> {
    public ConsumerData(){}
    @Override
    public ConsumerUser instanceCreate(String [] data) {

        ConsumerUser consumer = new ConsumerUser(Roles.valueOf(data[0].trim()),
                                                 data[1].trim(),
                                                 data[2].trim(),
                                                 Paths.get(data[3].trim()),
                                                 data[4].trim(),
                                                 data[5].trim());
        return consumer;
    }
    @Override
    public String instanceWrite(Object obj) { return formatCSV(obj);}

    @Override
    public String getKey(String [] data){
        return data[1].trim();
    }

    @Override
    public String getQuoteFormat(Object o) {
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    @Override
    public String formatCSV(Object o) {
        ConsumerUser consumerUser = (ConsumerUser) o;
        String line = getQuoteFormat(consumerUser.getRole()) + ","
                + getQuoteFormat(consumerUser.getUsername()) + ","
                + getQuoteFormat(consumerUser.getPassword()) + ","
                + getQuoteFormat(consumerUser.getFirstName()) + ","
                + getQuoteFormat(consumerUser.getLastName()) + ","
                + getQuoteFormat(consumerUser.getProfileImagePath());

        return line;
    }


}
