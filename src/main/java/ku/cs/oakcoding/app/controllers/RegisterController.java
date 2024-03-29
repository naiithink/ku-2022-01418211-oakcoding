/**
 * @file RegisterController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ku.cs.oakcoding.app.helpers.hotspot.OakHotspot;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.UserManagerStatus;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.stages.StageManager;


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
    private Path profileImagePath;

    @FXML
    void handleBackButtonGoToAdminPage(MouseEvent event) {
        try {
            StageManager.getStageManager().setPage("admin", StageManager.getStageManager().getContext());
        } catch (StageManager.PageNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Page not found");
        }
    }

    @FXML
    void handleBackButtonGoToLoginPage(MouseEvent event) {
        try {
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void handleProfileUpload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("อัพโหลดรูปโปรไฟล์");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Profile Image", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(StageManager.getStageManager().getPrimaryStage());

        if (Objects.nonNull(selectedFile)) {
            this.profileImagePath = selectedFile.toPath();
            nameProfileUploadStaffLabel.setText(this.profileImagePath.getFileName().toString());
            nameProfileUploadStaffLabel.setVisible(true);
            nameProfileUploadLabel.setText(this.profileImagePath.getFileName().toString());
            nameProfileUploadLabel.setVisible(true);
        }
    }

    @FXML
    void handleRegisterOrganizationButton(ActionEvent event) {

    }

    @FXML
    void handleRegisterStaffButton(ActionEvent event) {
        String userName = usernameStaffField.getText();
        String firstName = firstnameStaffField.getText();
        String lastName = lastnameStaffField.getText();
        String password = passwordStaffField.getText();
        String passwordConfirm = passwordConfirmStaffField.getText();


        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");
        UserManagerStatus status = AccountService.getUserManager().register((AdminUser) StageManager.getStageManager().getContext(), userName, Roles.STAFF, firstName, lastName, profileImagePath, password, passwordConfirm);
        if (password.equals(passwordConfirm)) {
                switch (status) {
                    case SUCCESSFUL:
                        alertInformation.setContentText(usernameStaffField.getText() + " คุณได้ทำการสมัครสมาชิกเรียบร้อยแล้ว");
                        alertInformation.showAndWait();
                        usernameStaffField.clear();
                        firstnameStaffField.clear();
                        lastnameStaffField.clear();
                        passwordStaffField.clear();
                        passwordConfirmStaffField.clear();
                        nameProfileUploadLabel.setText("");
                        nameProfileUploadLabel.setVisible(false);
                        try {
                            StageManager.getStageManager().setPage("admin", StageManager.getStageManager().getContext());
                        } catch (StageManager.PageNotFoundException e) {
                            OakLogger.log(Level.SEVERE, "Page not found");
                        }
                        break;
                    case INVALID_ACCESS:
                        alertWarning.setContentText(" You do not have administrator access.");
                        alertWarning.showAndWait();
                        break;
                    case USER_NAME_ALREADY_EXIST:
                        alertWarning.setContentText(userNameField.getText() + " This user account cannot be used due to this user account already exists.");
                        alertWarning.showAndWait();
                        break;
                    case USER_NAME_CONTAINS_UPPER_CASE:
                        alertWarning.setContentText(userNameField.getText() + " This cannot be used because the account must contain all lowercase letters a-z.");
                        alertWarning.showAndWait();
                        break;
                }

        }
        if (!password.equals(passwordConfirm)){
            alertWarning.setContentText(userNameField.getText() + " Please enter the same password as the confirmation password.");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void handleRegisterUserButton(ActionEvent event) {
        String userName = userNameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        String passwordConfirm = passwordConfirmField.getText();


        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");
        UserManagerStatus status = AccountService.getUserManager().register(null, userName, Roles.CONSUMER, firstName, lastName, profileImagePath, password, passwordConfirm);
        if (password.equals(passwordConfirm)) {
            if (confirmStudentCheckbox.isSelected()) {
        switch (status) {
            case SUCCESSFUL:
                alertInformation.setContentText(userNameField.getText() + " You have successfully subscribed.");
                alertInformation.showAndWait();
                userNameField.clear();
                firstNameField.clear();
                lastNameField.clear();
                passwordField.clear();
                passwordConfirmField.clear();
                nameProfileUploadLabel.setText("");
                nameProfileUploadLabel.setVisible(false);
                try {
                    StageManager.getStageManager().setPage("authentication", null);
                } catch (StageManager.PageNotFoundException e) {
                    OakLogger.log(Level.SEVERE, "Page not found");
                }
                break;
            case INVALID_ACCESS:
                alertWarning.setContentText(" You do not have administrator access.");
                alertWarning.showAndWait();
                break;
            case USER_NAME_ALREADY_EXIST:
                alertWarning.setContentText(userNameField.getText() + " This user account cannot be used due to this user account already exists. ");
                alertWarning.showAndWait();
                break;
            case USER_NAME_CONTAINS_UPPER_CASE:
                alertWarning.setContentText(userNameField.getText() + " This cannot be used because the account must contain all lowercase letters a-z.");
                alertWarning.showAndWait();
                break;
        }
    }
}
        if (!password.equals(passwordConfirm)){
            alertWarning.setContentText(userNameField.getText() + "Please enter the same password as the confirmation password.");
            alertWarning.showAndWait();
        }
        if (!confirmStudentCheckbox.isSelected()){
            alertWarning.setContentText("Please comfirm you are student in kasetsart University");
            alertWarning.showAndWait();
        }
    }
    public void initUserRegisterPane() {
        userRegisterPane.setVisible(true);
        addOrganizationPane.setVisible(false);
        addStaffRegisterPane.setVisible(false);
        nameProfileUploadLabel.setVisible(false);
    }

    public void initStaffRegisterPane(){
        userRegisterPane.setVisible(false);
        addOrganizationPane.setVisible(false);
        addStaffRegisterPane.setVisible(true);
        nameProfileUploadLabel.setVisible(false);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (oldValue.equals("authentication")) {
                initUserRegisterPane();
            }
            else if (oldValue.equals("admin")) {
                initStaffRegisterPane();
            }
        });


    }
}
