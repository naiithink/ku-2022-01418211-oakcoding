/**
 * @file Picture.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.picture;

import java.nio.file.Path;

public class ProfileImage {
    private Path profileImagePath;
    private String fileName;

    public ProfileImage(Path profileImagePath, String fileName){
        this.profileImagePath = profileImagePath;
        this.fileName = fileName;
    }

    public void setProfileImagePath(Path profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public Path getProFileImagePath(){
        return profileImagePath;
    }

    public String getFileName(){
        return fileName;
    }


}
