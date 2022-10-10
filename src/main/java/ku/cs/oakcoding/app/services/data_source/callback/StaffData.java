/**
 * @file StaffData.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.callback;

public class StaffData implements ManageDataType<StaffData>{
    public StaffData(){}

    @Override
    public StaffData instanceCreate(String[] data) {
        return null;
    }

    @Override
    public String instanceWrite(Object obj) {
        return null;
    }

    @Override
    public String getQuoteFormat(Object o) {
        return null;
    }

    @Override
    public String formatCSV(Object o) {
        return null;
    }
}
