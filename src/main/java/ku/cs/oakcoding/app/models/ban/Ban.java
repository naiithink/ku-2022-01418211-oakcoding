/**
 * @file Ban.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.ban;

import ku.cs.oakcoding.app.helpers.hotspot.DataFile;

public class Ban {
    private String userName;
    private String reasonBan;

    private final DataFile dataFile = DataFile.SUSPENDED;

    public Ban(String userName,
               String reasonBan){

        this.userName = userName;
        this.reasonBan = reasonBan;

    }

    public String getReasonBan() {
        return reasonBan;
    }

    public String getUserName() {
        return userName;
    }

    public DataFile getDataFile (){
        return dataFile;
    }

}
