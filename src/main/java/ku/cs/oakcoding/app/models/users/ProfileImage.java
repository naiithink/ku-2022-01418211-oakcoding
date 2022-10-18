package ku.cs.oakcoding.app.models.users;

import javafx.scene.image.ImageView;


public class ProfileImage {
    private ImageView image;

    public ProfileImage(ImageView img) {
        this.image = img;
    }

    public void setImage(ImageView value) {
        image = value;
    }

    public ImageView getImage() {
        return image;
    }
}

