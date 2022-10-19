/**
 * @file UserController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.services.stages.StageManager;
import ku.cs.oakcoding.app.services.stages.StageManager.PageNotFoundException;

public class UserController implements Initializable {
    @FXML
    private Pane createReportsUserPane;

    @FXML
    private Label userNameHomeLabel;

    @FXML
    private Label statusHomeLabel;

    @FXML
    private Button createReportButton;

    @FXML
    private ImageView createReportImageView;

    @FXML
    private Button reportButton;

    @FXML
    private ImageView reportImageView;

    @FXML
    private Pane reportsUserPane;

    @FXML
    private Pane welcomeUserPane;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingImageView;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private Button dashboardButton;

    @FXML
    private Pane settingUserPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label firstNameAccountLabel;

    @FXML
    private Label lastNameAccountLabel;

    @FXML
    private Label profileImageNameLabel;

    @FXML
    private ImageView profileImageView;



    public void handleClickReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        reportsUserPane.setVisible(true);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
    }

    public void handleClickCreateReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView
                .setImage(new Image(getClass().getResource("/images/paper-plane-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        createReportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(true);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
    }

    public void handleClickSetting(ActionEvent actionEvent) {

        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));
        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(true);
        setProfileLabel();
    }

    private void setProfileLabel(){
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(consumerUser.getUserName());
        statusAccountLabel.setText(consumerUser.getRole().getPrettyPrinted());
        firstNameAccountLabel.setText(consumerUser.getFirstName());
        lastNameAccountLabel.setText(consumerUser.getLastName());
        Image image = new Image(consumerUser.getProfileImagePath().toUri().toString());
        profileImageNameLabel.setText(consumerUser.getProfileImagePath().toUri().toString());
        profileImageView.setImage(image);
    }

    public void handleClickLogoutButton(ActionEvent actionEvent) {
        // ไว้จัดการการ logout ของ user
        try {
            StageManager.getStageManager().setPage("authentication", null);
        } catch (PageNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Page not found: " + e.getMessage());
        }
    }

    public void handleClickDashboard(ActionEvent actionEvent) {

        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));

        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
        settingUserPane.setVisible(false);
    }

    public void setMyPane() {
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameHomeLabel.setText(consumerUser.getFirstName());
        statusHomeLabel.setText(consumerUser.getRole().getPrettyPrinted());


//        OakLogger.log(Level.SEVERE,"no admin");
    }

    public void initPane() {
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("user")) {
                setMyPane();
            }
        });
    }
}
