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
            nameProfileUploadLabel.setText(this.profileImagePath.getFileName().toString());
            nameProfileUploadLabel.setVisible(true);
        }
    }

    @FXML
    void handleRegisterOrganizationButton(ActionEvent event) {

    }

    @FXML
    void handleRegisterStaffButton(ActionEvent event) {

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
                alertInformation.setContentText(userNameField.getText() + " คุณได้ทำการสมัครสมาชิกเรียบร้อยแล้ว");
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
                alertWarning.setContentText(" คุณไม่มีสิทธิ์เข้าถึงผู้ผู้แลระบบ");
                alertWarning.showAndWait();
                break;
            case USER_NAME_ALREADY_EXIST:
                alertWarning.setContentText(userNameField.getText() + " ไม่สามารถใช้บัญชีผู้ใช้นี้มีคนใช้งานแล้ว");
                alertWarning.showAndWait();
                break;
            case USER_NAME_CONTAINS_UPPER_CASE:
                alertWarning.setContentText(userNameField.getText() + " ไม่สามารถใช้ได้เนื่องจากบัญชีต้องมีตัวเล็กทั้งหมด a-z");
                alertWarning.showAndWait();
                break;
        }
    }
}
        if (!password.equals(passwordConfirm)){
            alertWarning.setContentText(userNameField.getText() + " กรุณากรอกรหัสผ่านตรงกับยืนยันรหัสผ่าน");
            alertWarning.showAndWait();
        }
        if (!confirmStudentCheckbox.isSelected()){
            alertWarning.setContentText(" คุณจำเป็นต้องเลือกว่าคุณเป็นนิสิต");
            alertWarning.showAndWait();
        }
    }
    public void initPane() {
        userRegisterPane.setVisible(true);
        addOrganizationPane.setVisible(false);
        addStaffRegisterPane.setVisible(false);
        nameProfileUploadLabel.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
    }
}
