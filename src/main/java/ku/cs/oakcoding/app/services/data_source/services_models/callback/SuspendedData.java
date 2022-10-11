/**
 * @file BanData.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.services_models.callback;

import ku.cs.oakcoding.app.models.ban.Ban;

public class SuspendedData implements ManageDataType<Ban>,FormatCSV {

    public SuspendedData() {}

    @Override
    public Ban instanceCreate(String[] data) {
        Ban bannedUser = new Ban(data[0], data[1]);
        return bannedUser;
    }

    @Override
    public String instanceWrite(Object obj) {
        return formatCSV(obj);
    }

    /**
     * @todo Assert null before String::trim
     * 
     * NullPointerException is unchecked Exception.
     */

    @Override
    public String getQuoteFormat(Object o) {
        String line = o + "";
        String result = "\"" + line + "\"";
        return result;
    }

    @Override
    public String formatCSV(Object o) {
        Ban ban = (Ban) o;
        String line = getQuoteFormat(ban.getUserName()) + ","
                + getQuoteFormat(ban.getReasonBan());

        return line;

    }

}
