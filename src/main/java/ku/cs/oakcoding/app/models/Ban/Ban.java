package ku.cs.oakcoding.app.models.ban;

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


}