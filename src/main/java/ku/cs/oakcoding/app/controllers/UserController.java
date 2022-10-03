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

public class UserController implements Initializable{
    @FXML
    private Pane createReportsUserPane;

    @FXML
    private Label FullnameLabel;

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
    private Label statusLabel;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private Button dashboardButton;

    @FXML
    private Pane settingUserPane;




    public void handleCloseButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    public void handleMinimizeButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }



    public void handleClickReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");

        reportsUserPane.setVisible(true);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        }


    public void handleClickCreateReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        createReportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
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
                "-fx-border-radius: 5px;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(true);
    }

    public void handleClickLogoutButton(ActionEvent actionEvent) {
//        ไว้จัดการการ logout ของ user
    }

    public void handleClickDashboard(ActionEvent actionEvent) {

        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));


        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        settingImageView.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
        settingUserPane.setVisible(false);
    }


    public void initPane(){
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
    }
}
