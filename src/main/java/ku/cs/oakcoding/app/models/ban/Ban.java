/**
 * @file Ban.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.ban;

import ku.cs.oakcoding.app.services.data_source.callback.FileCallBack;

public class Ban {
    private String userName;
    private String reasonBan;

    private final FileCallBack fileCallBack = FileCallBack.BAN;

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

    public FileCallBack getFileCallBack(){
        return fileCallBack;
    }


}
