package ku.cs.oakcoding.app.controllers;

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

import java.net.URL;
import java.util.ResourceBundle;

public class StaffController implements Initializable {
    @FXML
    private Button AccountChangeInfoButton;

    @FXML
    private Label FullnameLabel;

    @FXML
    private Label UsernameLabel;

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
    private ImageView picProfileSettingLabel;

    @FXML
    private Button reportButton;

    @FXML
    private ImageView reportImageView;

    @FXML
    private Pane reportsUserOfStaffPane;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingImageView;

    @FXML
    private Pane settingStaffPane;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Pane welcomeStaffPane;



    public void handleMinimizeButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleClickReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        reportsUserOfStaffPane.setVisible(true);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(false);
    }

    public void handleCloseButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleClickLogoutButton(ActionEvent actionEvent) {
    }

    public void handleClickSetting(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));
        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(true);
    }



    public void handleClickDashboard(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
    }
    public void initPane(){
        reportsUserOfStaffPane.setVisible(false);
        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
    }
}
