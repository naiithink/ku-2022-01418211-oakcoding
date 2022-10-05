package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;

import java.nio.file.Path;

public final class ConsumerUser
        extends User {

    public ConsumerUser(Roles role,
                        BanStatus banStatus,
                        String firstName,
                        String lastName,
                        Path profileImagePath,
                        String username,
                        String password) {

        super(role, firstName, lastName, profileImagePath, username, password);
    }
    @Override
    public String formatCSV(){

            String line = getQuoteFormat(getRole()) + ","
                    + getQuoteFormat(getFirstName()) + ","
                    + getQuoteFormat(getLastName()) + ","
                    + getQuoteFormat(getProfileImagePath()) + ","
                    + getQuoteFormat(getUsername()) + ","
                    + getQuoteFormat(getPassword());

            return line;

    }


}
