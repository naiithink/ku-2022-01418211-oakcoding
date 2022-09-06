package ku.cs.oakcoding.app.controllers;

import com.github.saacsos.fxrouter.Router;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.models.IDmanager;
import ku.cs.oakcoding.app.models.User.User;

import java.io.File;
import java.io.IOException;

public class RegisterController {

    private IDmanager IDmanager;
    private String picturePath = "picture/Default.jpg";
    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField passwordConfirmField;

    @FXML
    public void initialize() {
        IDmanager = new IDmanager();
    }
    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        String usernameIn = usernameField.getText();
        String passwordIn = passwordField.getText();
        String firstnameIn = firstnameField.getText();
        String lastnameIn = lastnameField.getText();
        String passwordConfirmIn = passwordConfirmField.getText();

        if (IDmanager.doRegister(usernameIn,passwordIn,passwordConfirmIn,firstnameIn,lastnameIn)) {
            usernameField.clear();
            passwordField.clear();
            passwordConfirmField.clear();
            firstnameField.clear();
            lastnameField.clear();

            picturePath = IDmanager.writeThenGetPicturePath(usernameIn+".jpg",picturePath);

            IDmanager.regisID(usernameIn,passwordIn,picturePath,firstnameIn,lastnameIn);
            try {
                Router.goTo("project");
            } catch (IOException e){
                System.err.println("ไปที่หน้า start ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        } else {
            // out some error
            usernameField.clear();
            passwordField.clear();
            passwordConfirmField.clear();
            firstnameField.clear();
            lastnameField.clear();
            System.err.println("Already have ID");

        }
    }

    @FXML
    public void handleBackButton(MouseEvent mouseEvent)  {
        try {
            Router.goTo("project");
        } catch (final Exception e) {
            System.err.println("ไปที่หน้า start ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    @FXML
    public void handleCloseButton(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimizeButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void handleProfileUpload(){
        FileChooser profileChooser = new FileChooser();
        profileChooser.setTitle("Choose Picture");
        profileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.jpg","*.png"));
        File selectedFile = profileChooser.showOpenDialog(null);
        picturePath = selectedFile.getAbsolutePath();

    }

}
