package ku.cs;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.github.saacsos.Router;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ku.cs.app.models.Register;

import java.io.IOException;


public class ProjectController {

    private Register register;
    @FXML private TextField username;
    @FXML private PasswordField password;

    @FXML
    public void initialize() {
        register = new Register();
    }
    @FXML public void handleSignInButton(ActionEvent actionEvent){
        String usernameIn = username.getText();
        String passwordIn = password.getText();

        register.setUsername(usernameIn);
        register.setPassword(passwordIn);

        if (register.doLogin()){
            username.clear();
            password.clear();

                try {
                    Router.goTo("test");
                }
                catch (IOException e) {
                    System.err.println("ไปที่หน้า home ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกําหนด route");
                }
        }

        else {
            username.clear();
            password.clear();


        }

    }

    @FXML public void handleCloseButton(MouseEvent mouseEvent) throws IOException {
        //Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        //stage.close();
        Router.popup("test");

    }

    @FXML public void handleMinimizeButton(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }


}
