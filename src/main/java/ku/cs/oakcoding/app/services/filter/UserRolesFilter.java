package ku.cs.oakcoding.app.services.filter;

import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.models.users.Roles;

public class UserRolesFilter implements Filterer<FullUserEntry> {

    public Roles roles;

    @Override
    public boolean check(FullUserEntry userEntry) {
        if (this.roles == userEntry.getRole())
            return true;
        return false;

    }

    public void setRoles(Roles roles){
        this.roles = roles;

    }
}
