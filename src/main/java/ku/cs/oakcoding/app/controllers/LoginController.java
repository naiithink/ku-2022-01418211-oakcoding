package ku.cs.oakcoding.app.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.github.saacsos.fxrouter.OakRouter;
import ku.cs.oakcoding.app.models.User.User;


public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;


    @FXML
    public void handleSignInButton(ActionEvent actionEvent) {
        String usernameIn = username.getText();
        String passwordIn = password.getText();

        if (User.login(usernameIn,passwordIn)){
            try {
                OakRouter.goTo("homeUser",User.getValue(usernameIn));
            } catch (IOException e) {
                System.err.println("ไปที่หน้า homeUser ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

    }

    @FXML
    public void handleCloseButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleMinimizeButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void handleBackButton(MouseEvent mouseEvent)  {
        try {
            OakRouter.goTo("project");
        } catch (final Exception e) {
            System.err.println("ไปที่หน้า start ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
