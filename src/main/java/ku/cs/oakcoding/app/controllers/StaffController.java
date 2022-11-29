/**
 * @file StaffController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.StaffUser;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.stages.StageManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class StaffController implements Initializable {
    @FXML
    private Button AccountChangeInfoButton;

    @FXML
    private ImageView backPicture;

    @FXML
    private Button changePasswordButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private Label firstNameAccountLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label fullNameLabel11121;

    @FXML
    private Label lastNameAccountLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label numberReportOfUser;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private Label profileImageNameLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView profileImageView1;

    @FXML
    private Button reportButton;

    @FXML
    private ImageView reportImageView;

    @FXML
    private Pane reportsUserOfStaffPane;

    @FXML
    private Button settingButton;

    @FXML
    private Pane settingDetailChangeUserPane;

    @FXML
    private ImageView settingImageView;

    @FXML
    private Pane settingStaffPane;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label statusHomeLabel;

    @FXML
    private Label userNameHomeLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Pane welcomeStaffPane;

    @FXML
    private Pane welcomeUserPane111;





    public void handleClickReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        reportsUserOfStaffPane.setVisible(true);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
    }

//    private void clearAllData(){
//        clearUsersPageData();
//        clearProfilePageData();
//        clearDepartmentPageData();
//        clearComplaintPageData();
//    }
//
//    private void clearUsersPageData(){
//
//        firstNameCol.setText("");
//        lastNameCol.setText("");
//        profileImageCol.setText("");
//        lastLoginCol.setText("");
//        observableUserSet = FXCollections.observableSet();
//        observableUserList = FXCollections.observableArrayList();
//        usersListTableView.getItems().clear();
//        usersListTableView.refresh();
//
//     }

    @FXML
    public void handleClickLogoutButton(ActionEvent actionEvent) {
        try {
//            clearAllData();
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleClickSetting(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));
        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        setProfileLabel();
    }



    public void handleClickDashboard(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
    }
    public void initPane(){
        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
    }
    public void setMyPane() {
        StaffUser staffUser = (StaffUser) StageManager.getStageManager().getContext();
        userNameHomeLabel.setText(staffUser.getFirstName());
        statusHomeLabel.setText(staffUser.getRole().getPrettyPrinted());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPane();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("staff")) {
                setMyPane();
//                initReportTableView();
//                initComplaintTableView();
//
//                handleSelectComplaintTableView();
            }
        });
    }

    public void handleChangePaneToChangeDetailPane(ActionEvent actionEvent) {
        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(true);



    }

    public void handleChangeDetail(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmPasswordField.getText();

        boolean isChangePassword = AccountService.getUserManager().changePassword(username,oldPassword,newPassword,confirmNewPassword);
        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");

        if (isChangePassword){
            alertInformation.setContentText("Your password has been successfully changed.");
            alertInformation.showAndWait();
            oldPasswordField.clear();
            newPasswordField.clear();
            usernameTextField.clear();
            confirmPasswordField.clear();

            try {

                StageManager.getStageManager().setPage("authentication", null);
            } catch (StageManager.PageNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Page not found: " + e.getMessage());
            }

        }
        else {
            alertWarning.setContentText("Please check again.");
            alertWarning.showAndWait();
        }

    }

    public void handleBackUserPictureButton(MouseEvent mouseEvent) {
        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
    }
    private void setProfileLabel(){
        StaffUser staffUser = (StaffUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(staffUser.getUserName());
        statusAccountLabel.setText(staffUser.getRole().getPrettyPrinted());
        firstNameAccountLabel.setText(staffUser.getFirstName());
        lastNameAccountLabel.setText(staffUser.getLastName());
        Image image = new Image(staffUser.getProfileImagePath().toUri().toString());
        profileImageNameLabel.setText(staffUser.getProfileImagePath().toUri().toString());
        profileImageView.setImage(image);
    }
}
