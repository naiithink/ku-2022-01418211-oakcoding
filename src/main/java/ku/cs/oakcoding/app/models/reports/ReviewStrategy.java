package ku.cs.oakcoding.app.models.reports;

import java.util.Objects;

import ku.cs.oakcoding.app.models.users.User;

public interface ReviewStrategy<T extends User> {

    boolean approve(T reviewer);

    boolean deny(T reviewer);

    default boolean verifyAccess(T reviewer) {
        if (Objects.nonNull(reviewer) && (reviewer instanceof T))
            return true;

        return false;
    }
}
