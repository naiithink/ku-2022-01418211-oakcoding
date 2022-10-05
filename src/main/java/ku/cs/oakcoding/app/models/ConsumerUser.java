package ku.cs.oakcoding.app.models;

import ku.cs.oakcoding.app.helpers.hotspot.BanStatus;
import ku.cs.oakcoding.app.helpers.hotspot.Roles;

import java.nio.file.Path;

public final class ConsumerUser
        extends User {

    private BanStatus banStatus;

    public ConsumerUser(Roles role,
                        BanStatus banStatus,
                        String firstName,
                        String lastName,
                        Path profileImagePath,
                        String username,
                        String password) {

        super(role, firstName, lastName, profileImagePath, username, password);
        this.banStatus = banStatus;
    }
    public BanStatus getBanStatus(){
        return banStatus;
    }
    @Override
    public String formatCSV(){

            String line = getQuoteFormat(getRole()) + ","
                    + getQuoteFormat(getBanStatus()) + ","
                    + getQuoteFormat(getFirstName()) + ","
                    + getQuoteFormat(getLastName()) + ","
                    + getQuoteFormat(getProfileImagePath()) + ","
                    + getQuoteFormat(getUsername()) + ","
                    + getQuoteFormat(getPassword());

            return line;
        }
    }


}
