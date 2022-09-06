package ku.cs.oakcoding;

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

public class ProjectController {


    @FXML
    public void handleSignInButton(ActionEvent actionEvent) {
            try {
                Router.goTo("signIn");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
    }

    @FXML
    public void handleRegisterButton(MouseEvent mouseEvent) {
        try {
            Router.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("check route");
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
}
