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
import ku.cs.oakcoding.app.models.Register;
import ku.cs.oakcoding.app.models.User;
import ku.cs.oakcoding.app.services.DataSourceCSV;
import ku.cs.oakcoding.app.services.UserDataSourceCSV;

public class ProjectController {

    private Register register;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        register = new Register();
    }

    @FXML
    public void handleSignInButton(ActionEvent actionEvent) {
        String usernameIn = username.getText();
        String passwordIn = password.getText();

        register.setUsername(usernameIn);
        register.setPassword(passwordIn);
        register.setConfirmPassword(passwordIn);
        register.setPicturePath("Please input your picture");
        register.setFirstname("Panachai");
        register.setLastname("Kotchagason");

        if (register.doLogin()) {
            username.clear();
            password.clear();

            User newUser = new User(register.getUsername(), register.getPassword(), register.getFirstname(), register.getLastname(), register.getPicturePath());
            DataSourceCSV<User> dataSource;
            dataSource = new UserDataSourceCSV("data","user.csv");
            dataSource.writeData(newUser);
        }
        else {
            username.clear();
            password.clear();
            System.out.println("cant object");
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
}
