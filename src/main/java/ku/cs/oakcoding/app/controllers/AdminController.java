/**
 * @file AdminController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;


import java.io.BufferedWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UsersList;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;

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
    private TableView<User> userListTableView;

    @FXML
    private TableColumn<User, String> firstNameCol;

    @FXML
    private TableColumn<User, String> lastNameCol;
    @FXML
    private TableColumn<User, String> roleCol;

    @FXML
    private TableColumn<User, String> userNameCol;

    private DataSourceCSV<UsersList> dataSourceCSV;
//    private UsersList usersList = new UsersList();
    private ObservableSet<User> observableUserSet = FXCollections.observableSet();
    private void initTableView() {

        dataSourceCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER,"users.csv");
        observableUserSet.addAll(dataSourceCSV.readData().getUsers());
//        usersList = dataSourceCSV.readData();
        userListTableView.setEditable(true);
        roleCol.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User, String> event) {
                User user = event.getRowValue();
                user.setFirstName(event.getNewValue());
            }
        });
        lastNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        userNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        showListView();

    }
    private void showListView() {
        userListTableView.getItems().addAll(observableUserSet);
        userListTableView.refresh();
    }
    @FXML
    void handleClickComplaints(ActionEvent event) {
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle-seleted.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }


        complaintsButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home-seleted.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }
        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building-seleted.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }
        organizationsButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines-seleted.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }

        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope-seleted.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }
        requestButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings-seleted.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }

        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        userButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

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
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("home.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("flag-seleted.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("comment-alt-middle.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images").resolve("settings.png").toUri().toURL().toString()));
        } catch (MalformedURLException e) {
            OakLogger.log(Level.SEVERE, "Got 'MalformedURLException' while loading menu icons");
        }

        userButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        complaintsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        organizationsButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        requestButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        welcomePane.setVisible(false);
        userPane.setVisible(true);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

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
        initTableView();
    }
    public void handleSave(ActionEvent actionEvent) throws Exception{
        // Writer writer = null;

        System.out.println(OakResourcePrefix.getPrefix().getParent().toString());
        System.out.println(OakResourcePrefix.getPrefix().getParent().getParent().resolve("data").resolve("loveJava.csv").toAbsolutePath().toString());

        try (BufferedWriter writer = Files.newBufferedWriter(OakResourcePrefix.getPrefix().getParent().getParent().resolve("data").resolve("loveJava.csv"))) {
            for (User user : observableUserSet) {
                String text = user.getFirstName() + ";" + user.getLastName() + ";" + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {

            // writer.flush();
            // writer.close();
        }
    }


}
