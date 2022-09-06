package ku.cs.oakcoding.app.controllers;

import com.github.saacsos.fxrouter.Router;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ku.cs.oakcoding.app.models.User.User;

import java.io.File;
import java.io.IOException;

public class ProfileUserController {
    private String picturePath;

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize(){
        User user = (User) Router.getData();
        File file = new File(user.getPicturePath());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }

    @FXML
    public void handleProfileUpload(){
        FileChooser profileChooser = new FileChooser();
        profileChooser.setTitle("Choose Picture");
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Image Files", "*.jpg","*.png");

        profileChooser.getExtensionFilters().addAll(fileExtensions);
        File selectedFile = profileChooser.showOpenDialog(null);
        picturePath = selectedFile.getAbsolutePath();

        User user = (User) Router.getData();
        user.changeProfilePicture(picturePath);

        refreshPicture();

    }



    public void refreshPicture(){
        User user = (User) Router.getData();
        File file = new File(user.getPicturePath());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
}
