package ku.cs.oakcoding.app.models.Ban;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;

public class Ban {
    String username;
    String reasonBan;

    public Ban(String username,
               String reasonBan){

        this.username = username;
        this.reasonBan = reasonBan;

    }

    public String getReasonBan() {
        return reasonBan;
    }

    public String getUsername() {
        return username;
    }

    public String getQuoteFormat(Object o){
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    public String formatCSV(){

        String line = getQuoteFormat(getUsername()) + ","
                    + getQuoteFormat(getReasonBan());

        return line;
    }
}
}
