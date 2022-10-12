/**
 * @file AuthenticationController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.services.stages.OldStageManager;
import ku.cs.oakcoding.app.services.stages.OldStageManager.PageNotFoundException;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {
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
    private Button registerUserHereButton;

    @FXML
    void handleLoginButton(ActionEvent event) {
//        ส่วนของ ปุ่ม login ต้อง implement user ให้ตรง
    }

    @FXML
    void handleGetStartedButtonToLoginPage(ActionEvent event) {
        if (event.getSource() == getStartedButton){
            getStartedPane.setVisible(false);
            loginPane.setVisible(true);
        }
    }

    public void handleRegisterUserHereToRegisterPage(ActionEvent actionEvent) {
        try {
            OldStageManager.getStageManager().setPage("register");
        } catch (PageNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void handleBackButtonGoToGetStartedPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == backButtonGoToGetStartedPage){
            getStartedPane.setVisible(true);
            loginPane.setVisible(false);
        }
    }


    public void initPane(){
        getStartedPane.setVisible(true);
        loginPane.setVisible(false);


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();

    }
}
