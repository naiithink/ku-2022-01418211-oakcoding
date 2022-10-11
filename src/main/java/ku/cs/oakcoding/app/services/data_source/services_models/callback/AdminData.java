/**
 * @file AdminData.java
 * 
 * @todo Implement methods
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.services.data_source.services_models.callback;

import ku.cs.oakcoding.app.models.AdminUser;
import ku.cs.oakcoding.app.models.ProfileImageState;
import ku.cs.oakcoding.app.models.Roles;
import ku.cs.oakcoding.app.models.picture.ProfileImage;

public class AdminData implements ManageDataType<AdminUser>,FormatCSV {

    public AdminData() {}

    @Override
    public AdminUser instanceCreate(String[] data) {

        AdminUser admin = new AdminUser(Roles.valueOf(data[0].trim()),
                data[1].trim(),
                data[2].trim(),
                data[3].trim(),
                data[4].trim(),
                ProfileImageState.valueOf(data[5]));
        return admin;
    }

    @Override
    public String instanceWrite(Object obj) {
        return formatCSV(obj);
    }


    @Override
    public String getQuoteFormat(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String formatCSV(Object o) {
        // TODO Auto-generated method stub
        return null;
    }
}
