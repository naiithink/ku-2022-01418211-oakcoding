/**
 * @file Ban.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.ban;

public class Ban {
    String userName;
    String reasonBan;

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


}
