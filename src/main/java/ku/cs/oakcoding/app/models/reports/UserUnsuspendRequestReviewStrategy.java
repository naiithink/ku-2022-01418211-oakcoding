package ku.cs.oakcoding.app.models.reports;

import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.services.AccountService;

public class UserUnsuspendRequestReviewStrategy
        implements ReviewStrategy<AdminUser> {

    private final String UID;

    public UserUnsuspendRequestReviewStrategy(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    @Override
    public boolean approve(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        AccountService.getUserManager().setActiveStatus(admin, AccountService.getUserManager().getUserNameOf(UID), true);
        AccountService.getUserManager().deleteUserUnsuspendRequest(UID);

        return true;
    }

    @Override
    public boolean deny(AdminUser admin) {
        if (!verifyAccess(admin))
            return false;

        AccountService.getUserManager().deleteUserUnsuspendRequest(UID);

        return true;
    }
}
