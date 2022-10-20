/**
 * @file AdminController.java
 *
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.org.Department;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.models.users.UserManagerStatus;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.WorkspaceManager;
import ku.cs.oakcoding.app.services.WorkspaceManagerStatus;
import ku.cs.oakcoding.app.services.WorkspaceService;
import ku.cs.oakcoding.app.services.stages.StageManager;


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

    /**
     * @userslistTableView
     */
    @FXML
    private TableView<FullUserEntry> usersListTableView;

    @FXML
    private TableColumn<FullUserEntry, String> firstNameCol;

    @FXML
    private TableColumn<FullUserEntry, String> lastNameCol;

    @FXML
    private TableColumn<FullUserEntry, ImageView> profileImageCol;

    @FXML
    private TableColumn<FullUserEntry, String> lastLoginCol;

    private ObservableSet<FullUserEntry> observableUserSet = FXCollections.observableSet();

    private ObservableList<FullUserEntry> observableUserList = FXCollections.observableArrayList();

    /**
     * @organizationTableView
     */

    @FXML
    private TableView<Department> departmentsListTableView;
    @FXML
    private TableColumn<Department, ReadOnlyStringWrapper> departmentCol;
    @FXML
    private TableColumn<Department, ReadOnlyStringWrapper> leaderStaffCol;

    private ObservableSet<Department> observableDepartmentSet = FXCollections.observableSet();

    private ObservableList<Department> observableDepartmentList = FXCollections.observableArrayList();


    /**
     *
     * DetailUserName page
     *
     */
    @FXML
    private Pane userDetailPane;

    @FXML
    private Label detailUserNameLabel;

    @FXML
    private Label detailUserStatusAccountLabel;

    @FXML
    private Label detailUserFirstNameLabelAccount;

    @FXML
    private Label detailUserLastNameLabel;

    @FXML
    private Button detailUserBanButton;

    @FXML
    private ImageView detailUserPicProfileSettingLabel;

    @FXML
    private TextField nameDepartmentTextField;

    @FXML
    private Button createDepartmentButton;


    /**
     *
     *
     * Complaint Page
     *
     */

    @FXML
    private ChoiceBox<String> complaintChoiceBox;

    @FXML
    private TableView<Complaint> personalTableView;

    @FXML
    private TableView<Complaint> surroundingTableView;





    /**
     *  UserPage
     */


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initPane();
        initComplaintChoiceBox();
//        initTableView();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("admin")) {
                initUsersTableView();
                initDepartmentTableView();
                setMyPane();
                handleSelectedUsersTableView();
                handleSelectedComplaintChoiceBox();
            }
        });
    }

    private void initUsersTableView() {


        /**
         * @todo Read AdminUser instance
         */


        observableUserSet.addAll(AccountService.getUserManager().getAllUsersSetProperty(AccountService.getUserManager().login("_ROOT","admin")) /* dataSourceCSV.readData().getUsers() */);
        usersListTableView.setEditable(true);



        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        profileImageCol.setCellValueFactory(p -> {
            ObjectProperty<ImageView> res = null;
            try {
                res = new SimpleObjectProperty<>(new ImageView(new Image(p.getValue().getProfileImagePath().toUri().toURL().toString())));
                res.get().setPreserveRatio(true);
                res.get().setFitWidth(100);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } finally {
                return res;
            }
        });
        lastLoginCol.setCellValueFactory(p -> {
            ObjectProperty<String> dateLastlogin = null;
            try {
                /**
                 *
                 * https://stackoverflow.com/questions/7740972/convert-epoch-time-to-date
                 */
                long millis = p.getValue().getLastLogin();
                Date date = new Date(millis);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                format.setTimeZone(TimeZone.getTimeZone("Asia/Bangkok"));
                String formatted = format.format(date);
                dateLastlogin = new SimpleObjectProperty<>(new String(formatted));

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return dateLastlogin;
            }
        });

        observableUserList.addAll(observableUserSet);
        usersListTableView.getItems().addAll(observableUserList);

    }

    private void handleSelectedUsersTableView() {

        usersListTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = usersListTableView.getSelectionModel().getSelectedIndex();
                FullUserEntry fullUserEntry = usersListTableView.getItems().get(index);
                showSelectedUser(fullUserEntry);
            }
        });




    }

    private void showSelectedUser(FullUserEntry userEntry){
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(true);

        detailUserNameLabel.setText(userEntry.getUserName());
        detailUserStatusAccountLabel.setText(userEntry.getRole().getPrettyPrinted());
        detailUserFirstNameLabelAccount.setText(userEntry.getFirstName());
        detailUserLastNameLabel.setText(userEntry.getLastName());
        Image image = new Image(userEntry.getProfileImagePath().toUri().toString());
        detailUserPicProfileSettingLabel.setImage(image);




    }


    /**
     *  Department page
     */




    private void initDepartmentTableView(){

        observableDepartmentSet.addAll(WorkspaceService.getWorkspaceManager().getAllDepartmentsSet());
        departmentsListTableView.setEditable(true);


        departmentCol.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        leaderStaffCol.setCellValueFactory(new PropertyValueFactory<>("leaderStaffMemberID"));

        observableDepartmentList.addAll(observableDepartmentSet);
        departmentsListTableView.getItems().addAll(observableDepartmentList);



    }
    @FXML
    private void handleRegisterDepartment(){
        String nameDepartment = nameDepartmentTextField.getText();
        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");
        Department department = WorkspaceService.getWorkspaceManager().newDepartment(nameDepartment);
        if (department == null){
            alertWarning.setContentText("ชื่อองค์กรนี้ถูกใช้งานแล้ว กรุณาใช้ชื่อใหม่" + nameDepartment + "'");
            alertWarning.showAndWait();
        }
        else{
            alertWarning.setContentText("คุณได้ทำการสมัครองค์กรเป็นที่เรียบร้อยแล้ว" + "'");
            alertWarning.showAndWait();
        }


    }


    void setProfileLabel(){
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(admin.getUserName());
        statusAccountLabel.setText(admin.getRole().getPrettyPrinted());
        firstNameLabelAccount.setText(admin.getFirstName());
        lastNameLabel.setText(admin.getLastName());
        Image image = new Image(admin.getProfileImagePath().toUri().toString());
        picProfileSettingLabel.setImage(image);


    }


    public void initComplaintChoiceBox(){
        String [] complaintChoiceBoxSelected = {"Personnel","Surrounding"};
        complaintChoiceBox.setValue("Personnel");
        complaintChoiceBox.getItems().addAll(complaintChoiceBoxSelected);
    }

    private void handleSelectedComplaintChoiceBox(){
        complaintChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Personnel")){
                    personalTableView.setVisible(true);
                    surroundingTableView.setVisible(false);
                }
                else if (newValue.equals("Surrounding")) {
                    personalTableView.setVisible(false);
                    surroundingTableView.setVisible(true);
                }

            }
        });
    }






    @FXML
    void handleClickComplaints() {
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
        userDetailPane.setVisible(false);
        personalTableView.setVisible(true);
        surroundingTableView.setVisible(false);

    }

    @FXML
    void handleClickDashboard() {
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
        userDetailPane.setVisible(false);

    }

    @FXML
    void handleClickLogoutButton() {
        try {
            userNameLabel.setText("");
            statusAccountLabel.setText("");
            firstNameLabelAccount.setText("");
            lastNameLabel.setText("");
            picProfileSettingLabel.setImage(null);
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleClickOrganizations() {
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
        userDetailPane.setVisible(false);

    }

    @FXML
    void handleClickReport() {
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
        userDetailPane.setVisible(false);
    }

    @FXML
    void handleClickRequest() {
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
        userDetailPane.setVisible(false);
    }

    @FXML
    void handleClickSetting() {
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
        userDetailPane.setVisible(false);
        setProfileLabel();

    }

    @FXML
    void handleClickUser() {
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
        userDetailPane.setVisible(false);

    }

    public void initPane() {
        welcomePane.setVisible(true);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);

    }
    public void setMyPane() {
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        fullNameLabelAccountSetting.setText(admin.getFirstName());
        statusLabel.setText(admin.getRole().getPrettyPrinted());
    }



}
