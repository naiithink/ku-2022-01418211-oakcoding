/**
 * @file StaffData.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.callback;

import ku.cs.oakcoding.app.models.StaffUser;

public class StaffData implements ManageDataType<StaffData>{
    public StaffData(){}

    @Override
    public StaffData instanceCreate(String[] data) {
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
        StaffUser staffUser = (StaffUser) o;
        return null;
    }
}
