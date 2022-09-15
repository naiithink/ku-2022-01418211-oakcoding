package ku.cs.oakcoding.app.models.picture;

public class Picture {
    private String picturePath;
    private String filename;

    public Picture(String picturePath, String filename){
        this.picturePath = picturePath;
        this.filename = filename;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public void setFilename(String filename){
        this.filename = filename;
    }

    public String getPicturePath(){
        return picturePath;
    }

    public String getFilename(){
        return filename;
    }


}
