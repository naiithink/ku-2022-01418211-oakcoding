/**
 * @file Picture.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.picture;

public class Picture {
    private String picturePath;
    private String fileName;

    public Picture(String picturePath, String fileName){
        this.picturePath = picturePath;
        this.fileName = fileName;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getPicturePath(){
        return picturePath;
    }

    public String getFileName(){
        return fileName;
    }


}
