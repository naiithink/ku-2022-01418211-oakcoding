/**
 * @file AdminController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminController implements Initializable {

    @FXML
    private Button accountChangeInfoButton;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button complaintsButton;

    @FXML
    private ImageView complaintsImageView;

    @FXML
    private Pane complaintsPane;

    @FXML
    private Button dashboardButton;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private Label firstNameLabelAccount;

    @FXML
    private Label fullNameLabelAccountSetting;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Label nameProfileSettingLabel;

    @FXML
    private Label numberReportOfUser;

    @FXML
    private Label numberReportOfUser1;

    @FXML
    private Label numberReportOfUser11;

    @FXML
    private Label numberReportOfUser12;

    @FXML
    private Button organizationsButton;

    @FXML
    private ImageView organizationsImageView;

    @FXML
    private Pane organizationsPane;

    @FXML
    private ImageView picProfileSettingLabel;

    @FXML
    private Button reportButton;

    @FXML
    private ImageView reportImageView;

    @FXML
    private Pane reportPane;

    @FXML
    private Button requestButton;

    @FXML
    private ImageView requestImageView;

    @FXML
    private Pane requestPane;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingImageView;

    @FXML
    private Pane settingPane;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Button userButton;

    @FXML
    private Pane userPane;

    @FXML
    private Pane welcomePane;

    @FXML
    private ImageView userImageView;

    @FXML
    void handleClickComplaints(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle-seleted.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));


        complaintsButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(true);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

    }

    @FXML
    void handleClickDashboard(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(true);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);


    }

    @FXML
    void handleClickLogoutButton(ActionEvent event) {

    }

    @FXML
    void handleClickOrganizations(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building-seleted.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));


        organizationsButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(true);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

    }

    @FXML
    void handleClickReport(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines-seleted.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));


        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(true);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
            }

    @FXML
    void handleClickRequest(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));


        requestButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(true);
        settingPane.setVisible(false);
    }

    @FXML
    void handleClickSetting(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));


        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(true);

    }

    @FXML
    void handleClickUser(ActionEvent event) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        userImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        complaintsImageView.setImage(new Image(getClass().getResource("/images/comment-alt-middle.png").toExternalForm()));
        organizationsImageView.setImage(new Image(getClass().getResource("/images/building.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/calendar-lines.png").toExternalForm()));
        requestImageView.setImage(new Image(getClass().getResource("/images/envelope.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));


        userButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        welcomePane.setVisible(false);
        userPane.setVisible(true);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

    }

    @FXML
    void handleCloseButton(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleMinimizeButton(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    public void initPane(){
        welcomePane.setVisible(true);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
    }


}
