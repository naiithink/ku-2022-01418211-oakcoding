package ku.cs.oakcoding.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class AuthenticationController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button getStartedButton;

    @FXML
    private Pane getStartedPane;

    @FXML
    private Button loginButton;

    @FXML
    private Pane loginPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private ImageView backButtonGoToGetStartedPage;

    @FXML
    void handleLoginButton(ActionEvent event) {
//        ส่วนของ ปุ่ม login ต้อง implement user ให้ตรง
    }
    @FXML
    void handlegetStartedButtonToLoginPage(ActionEvent event) {
        if (event.getSource() == getStartedButton){
            getStartedPane.setVisible(false);
            loginPane.setVisible(true);
        }
    }
    public void handleBackButtonGoToGetStartedPage(javafx.scene.input.MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == backButtonGoToGetStartedPage){
            loginPane.setVisible(false);
            getStartedPane.setVisible(true);
        }
    }

    public void handleCloseButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleMinimizeButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


}
