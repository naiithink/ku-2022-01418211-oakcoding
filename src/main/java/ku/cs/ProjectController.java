package ku.cs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.github.saacsos.FXRouter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ProjectController {

    @FXML private TextField username;
    @FXML private PasswordField password;


    @FXML public void handleSignInButton(ActionEvent actionEvent){
        String usernameIn = username.getText();
        String passwordIn = password.getText();
        // do what ever username and password


        username.clear();
        password.clear();

    }

    @FXML public void handleCloseButton(MouseEvent mouseEvent){
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML public void handleMinimizeButton(MouseEvent mouseEvent){
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }

    private void setIconified(boolean b) {
    }

}
