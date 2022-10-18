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
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UserEntry;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.stages.OldStageManager;

public class AdminController implements Initializable {

    @FXML
    private Button accountChangeInfoButton;

    @FXML
    private Label uidLabel;

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
    private TableView<UserEntry> userListTableView;

    @FXML
    private TableColumn<UserEntry, String> firstNameCol;

    @FXML
    private TableColumn<UserEntry, String> lastNameCol;
    @FXML
    private TableColumn<UserEntry, String> roleCol;

    @FXML
    private TableColumn<UserEntry, String> userNameCol;

    private ObservableSet<UserEntry> observableUserSet = FXCollections.observableSet();

    private void initTableView() {


        /**
         * @todo Read AdminUser instance
         */
        observableUserSet.addAll(AccountService.getUserManager().getAllUsersSet(AccountService.getUserManager()
                .login("_ROOT", "admin")) /* dataSourceCSV.readData().getUsers() */);

        userListTableView.setEditable(true);
        roleCol.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("role"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());


//        firstNameCol.setOnEditCommit(event -> {
//            UserEntry user = event.getRowValue();
//            AccountService.getUserManager().setActiveStatus()
////            user.setFirstName(event.getNewValue());
//        });

//        new EventHandler<TableColumn.CellEditEvent<UserEntry, String>>() {
//            @Override
//            public void handle(TableColumn.CellEditEvent<UserEntry, String> event) {
//
//            }
//        }

        lastNameCol.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameCol.setCellValueFactory(new PropertyValueFactory<UserEntry, String>("userName"));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle-seleted.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home-seleted.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building-seleted.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines-seleted.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope-seleted.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));

            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag.png").toUri().toURL().toString()));

            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));

            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));

            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));

            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));

            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings-seleted.png").toUri().toURL().toString()));
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
        setProfileLabel();

    }


    void setProfileLabel(){
        AdminUser admin = (AdminUser) OldStageManager.getStageManager().getContext();
        userNameLabel.setText(admin.getUserName());
        statusAccountLabel.setText(admin.getRole().getPrettyPrinted());
        firstNameLabelAccount.setText(admin.getFirstName());
        lastNameLabel.setText(admin.getLastName());
        uidLabel.setText(admin.getUID());
        Image image = new Image(admin.getProfileImagePath().toUri().toString());
        picProfileSettingLabel.setImage(image);


    }

    @FXML
    void handleClickUser(ActionEvent event) {
        try {
            dashboardImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("home.png").toUri().toURL().toString()));
            userImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("flag-seleted.png").toUri().toURL().toString()));
            complaintsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("comment-alt-middle.png").toUri().toURL().toString()));
            organizationsImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("building.png").toUri().toURL().toString()));
            reportImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("calendar-lines.png").toUri().toURL().toString()));
            requestImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("envelope.png").toUri().toURL().toString()));
            settingImageView.setImage(new Image(OakResourcePrefix.getPrefix().resolve("images")
                    .resolve("settings.png").toUri().toURL().toString()));
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

    public void initPane() {
        welcomePane.setVisible(true);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);

    }
    public void setMyPane() {
            AdminUser admin = (AdminUser) OldStageManager.getStageManager().getContext();
            fullNameLabelAccountSetting.setText(admin.getFirstName());
            statusLabel.setText(admin.getRole().getPrettyPrinted());


//        OakLogger.log(Level.SEVERE,"no admin");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
        initTableView();

        OldStageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("admin")) {
                setMyPane();
            }
        });
    }

    public void handleSave(ActionEvent actionEvent) throws Exception {
        // Writer writer = null;

        System.out.println(OakResourcePrefix.getPrefix().getParent().toString());
        System.out.println(OakResourcePrefix.getPrefix().getParent().getParent().resolve("data")
                .resolve("loveJava.csv").toAbsolutePath().toString());

        try (BufferedWriter writer = Files.newBufferedWriter(OakResourcePrefix.getPrefix().getParent()
                .getParent().resolve("data").resolve("loveJava.csv"))) {
            for (UserEntry user : observableUserSet) {
                String text = user.getFirstName() + ";" + user.getLastName() + ";" + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

            // writer.flush();
            // writer.close();
        }
    }

}
