package ku.cs.oakcoding.app.models.users;

public class BriefUserEntry {

    protected final String UID;

    protected Roles role;

    protected String userName;

    protected boolean isActive;

    protected int loginAttempt;

    public BriefUserEntry(String UID, Roles role, String userName, boolean isActive, int loginAttempt) {
        this.UID = UID;
        this.role = role;
        this.userName = userName;
        this.isActive = isActive;
        this.loginAttempt = loginAttempt;
    }

    public String getUID() {
        return UID;
    }

    public Roles getRole() {
        return role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public void addLoginAttempt() {
        this.loginAttempt++;
    }

    public void resetLoginAttempt() {
        this.loginAttempt = 0;
    }

    @Override
    public String toString() {
        return "BriefUserEntry [UID=" + UID + ", role=" + role + ", userName=" + userName + ", isActive=" + isActive
                + ", loginAttempt=" + loginAttempt + "]";
    }
}
