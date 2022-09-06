package ku.cs.oakcoding.app.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.github.saacsos.fxrouter.Router;
import ku.cs.oakcoding.app.models.IDmanager;
import ku.cs.oakcoding.app.models.User.User;

import ku.cs.oakcoding.app.services.DataSourceCSV;
import ku.cs.oakcoding.app.services.User.UserDataSourceCSV;

public class LoginController {

    private IDmanager IDmanager;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        IDmanager = new IDmanager();
    }

    @FXML
    public void handleSignInButton(ActionEvent actionEvent) {
        String usernameIn = username.getText();
        String passwordIn = password.getText();

        if (IDmanager.doLogin(usernameIn,passwordIn)){
            // .getUser
            User user = IDmanager.getUser(usernameIn,passwordIn);
            try {
                Router.goTo("homeUser",user);
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
            Router.goTo("project");
        } catch (final Exception e) {
            System.err.println("ไปที่หน้า start ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
