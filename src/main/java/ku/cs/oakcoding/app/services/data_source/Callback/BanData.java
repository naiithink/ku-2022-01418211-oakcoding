package ku.cs.oakcoding.app.services.data_source.Callback;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;
import ku.cs.oakcoding.app.models.Ban.Ban;
import ku.cs.oakcoding.app.models.ConsumerUser;

import java.nio.file.Paths;

public class BanData implements ManageDataType<Ban>{

    public BanData(){}
    @Override
    public Ban instanceCreate(String [] data) {
        Ban bannedUser = new Ban(data[0],data[1]);
        return bannedUser;
    }
    @Override
    public String instanceWrite(Object obj) { return formatCSV(obj);}

    @Override
    public String getKey(String [] data){
        return data[0].trim();
    }

    @Override
    public String getQuoteFormat(Object o){
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    @Override
    public String formatCSV(Object o){
        Ban ban = (Ban) o;
        String line = getQuoteFormat(ban.getUsername()) + ","
                + getQuoteFormat(ban.getReasonBan());

        return line;

    }

}
