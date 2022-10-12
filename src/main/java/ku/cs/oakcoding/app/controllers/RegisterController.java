/**
 * @file RegisterController.java
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.services.stages.OldStageManager;
import ku.cs.oakcoding.app.services.stages.OldStageManager.PageNotFoundException;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
// User Register

    @FXML
    private Pane addOrganizationPane;

    @FXML
    private Pane addStaffRegisterPane;

    @FXML
    private TextField bossOfOrganizationField;

    @FXML
    private CheckBox confirmStudentCheckbox;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField firstnameStaffField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField lastnameStaffField;

    @FXML
    private TextField memberOfOrganizationField;

    @FXML
    private Label messageWhenRegisterLabel;

    @FXML
    private Label messageWhenRegisterOrganizationLabel;

    @FXML
    private Label messageWhenRegisterStaffLabel;

    @FXML
    private Label nameLogoUploadOrganizationLabel;

    @FXML
    private Label nameProfileUploadLabel;

    @FXML
    private Label nameProfileUploadStaffLabel;

    @FXML
    private TextField organizationNameField;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private PasswordField passwordConfirmStaffField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordStaffField;

    @FXML
    private Button registerOrganizationButton;

    @FXML
    private Button registerStaffButton;

    @FXML
    private Button registerUserButton;

    @FXML
    private TextField userNameField;

    @FXML
    private Pane userRegisterPane;

    @FXML
    private TextField usernameStaffField;

    @FXML
    void handleBackButtonGoToAdminPage(MouseEvent event) {

    }

    @FXML
    void handleBackButtonGoToLoginPage(MouseEvent event) {

    }

    @FXML
    void handleProfileUpload(MouseEvent event) {

    }

    @FXML
    void handleRegisterOrganizationButton(ActionEvent event) {

    }

    @FXML
    void handleRegisterStaffButton(ActionEvent event) {

    }

    @FXML
    void handleRegisterUserButton(ActionEvent event) {

    }
    public void initPane(){
        userRegisterPane.setVisible(true);
        addOrganizationPane.setVisible(false);
        addStaffRegisterPane.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
    }
}
