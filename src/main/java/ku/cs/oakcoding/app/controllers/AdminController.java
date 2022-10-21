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
import java.util.Objects;
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
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
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
import org.w3c.dom.Text;


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
    private Pane departmentDetailPane;

    @FXML
    private ImageView userImageView;

    @FXML
    private Pane settingDetailPane;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Pane sideBarPane;

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
     * @ DepartmentTableView
     */

    @FXML
    private TableView<Department> departmentsListTableView;
    @FXML
    private TableColumn<Department, String> departmentCol;
    @FXML
    private TableColumn<Department, String> leaderStaffCol;



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
     *
     *
     * Department Detail Page
     *
     * */

    @FXML
    private ListView<String> departmentStaffMembersListView = new ListView<>();

    @FXML
    private Label departmentNameLabel;

    @FXML
    private Label leaderStaffLabel;

    @FXML
    private TableView<FullUserEntry> chooseLeaderStaffTableView;

    @FXML
    private TableColumn<FullUserEntry, ImageView> chooseLeaderProfileImageCol;

    @FXML
    private TableColumn<FullUserEntry, String> chooseFirstNameCol;

    @FXML
    private TableColumn<FullUserEntry, String> chooseLastNameCol;

     private ObservableSet<FullUserEntry> observableStaffMembersSet = FXCollections.observableSet();

    private ObservableList<FullUserEntry> observableStaffMembersList = FXCollections.observableArrayList();


    /**
     *
     *  Department ChangeLeader Page
     */

    @FXML
    private Pane departmentChangeLeaderPane;

    private String changeLeaderDepartmentID;

    @FXML
    private Label departmentNameLabel1;

    @FXML
    private Label leaderStaffLabel1;

    /**
     * methods zones
     */

    /**
     * Admin Setting
     */
    public void handleChangePaneToChangeDetailPane(ActionEvent actionEvent) {
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        personalTableView.setVisible(false);
        surroundingTableView.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(true);

    }

    @FXML
    private void handleBackUserSettingPictureButton(){
        sideBarPane.setDisable(false);
        handleClickSetting();

    }

    public void handleChangePassword(ActionEvent actionEvent) {
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
            alertInformation.setContentText("รหัสผ่านของคุณถูกเปลี่ยนเรียบร้อย");
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
            alertWarning.setContentText("คุณไม่สามารถเปลี่ยนรหัสได้ กรุณาตรวจสอบอีกครั้ง");
            alertWarning.showAndWait();
        }

    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initPane();
        initComplaintChoiceBox();
//        initTableView();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("admin")) {
                initUsersTableView();
                initDepartmentTableView();
                initStaffTableView();
                setMyPane();
                handleSelectedUsersTableView();
                handleSelectedComplaintChoiceBox();
                handleSelectedDepartmentTableView();
            }
        });
    }

    private void clearAllData(){
        clearUsersPageData();
        clearProfilePageData();
        clearDepartmentPageData();
        clearComplaintPageData();
    }

    private void initUsersTableView() {


        /**
         * @todo Read AdminUser instance
         */

        ObservableSet<FullUserEntry> fullUserEntries = AccountService.getUserManager().getAllUsersSetProperty((AdminUser) StageManager.getStageManager().getContext());
        fullUserEntries.addListener((SetChangeListener<? super FullUserEntry>) change -> {
            fullUserEntries.addAll(AccountService.getUserManager().getAllUsersSetProperty((AdminUser) StageManager.getStageManager().getContext()));
            usersListTableView.refresh();
        });

        observableUserSet.addAll(fullUserEntries); /* dataSourceCSV.readData().getUsers() */;
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

        observableUserList.setAll(observableUserSet);
        usersListTableView.getItems().addAll(observableUserList);
        usersListTableView.refresh();

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
    @FXML
    private void handleCreateStaffMember(){
        try {
            clearUsersPageData();
            StageManager.getStageManager().setPage("register", StageManager.getStageManager().getContext());
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearUsersPageData(){

       firstNameCol.setText("");
       lastNameCol.setText("");
       profileImageCol.setText("");
       lastLoginCol.setText("");
       observableUserSet = FXCollections.observableSet();
       observableUserList = FXCollections.observableArrayList();
       usersListTableView.getItems().clear();
       usersListTableView.refresh();

    }

    private void showSelectedUser(FullUserEntry userEntry){
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(true);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

        detailUserNameLabel.setText(userEntry.getUserName());
        detailUserStatusAccountLabel.setText(userEntry.getRole().getPrettyPrinted());
        detailUserFirstNameLabelAccount.setText(userEntry.getFirstName());
        detailUserLastNameLabel.setText(userEntry.getLastName());
        Image image = new Image(userEntry.getProfileImagePath().toUri().toString());
        detailUserPicProfileSettingLabel.setImage(image);




    }
    @FXML
    private void handleBackUserPictureButton(){
        sideBarPane.setDisable(false);
        handleClickUser();

    }


    /**
     *  Department page
     */





    private void initDepartmentTableView(){
        departmentsListTableView.getItems().clear();
        ObservableSet<Department> departments = WorkspaceService.getWorkspaceManager().getAllDepartmentsSet();
        departments.addListener((SetChangeListener<? super Department>) change -> {
            departments.removeAll(WorkspaceService.getWorkspaceManager().getAllDepartmentsSet());
            departments.addAll(WorkspaceService.getWorkspaceManager().getAllDepartmentsSet());
            departmentsListTableView.refresh();
        });
        observableDepartmentSet.addAll(departments);


        departmentsListTableView.setEditable(true);


        departmentCol.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        leaderStaffCol.setCellValueFactory(new PropertyValueFactory<>("leaderStaffMemberID"));

        observableDepartmentList.setAll(observableDepartmentSet);
        departmentsListTableView.getItems().addAll(observableDepartmentList);
        departmentsListTableView.refresh();



    }
    @FXML
    private void handleRegisterDepartment(){
        String nameDepartment = nameDepartmentTextField.getText();
        nameDepartmentTextField.clear();
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
            clearDepartmentPageData();
            initDepartmentTableView();
            departmentsListTableView.refresh();


        }


    }

    private void handleSelectedDepartmentTableView() {

        departmentsListTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = departmentsListTableView.getSelectionModel().getSelectedIndex();
                Department department = departmentsListTableView.getItems().get(index);
                showSelectedDepartment(department.getDepartmentID());
            }
        });

    }

    private void initStaffMembersListView(String departmentID){
        departmentStaffMembersListView.getItems().addAll(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getStaffMemberSetProperty());
    }

    private void showSelectedDepartment(String departmentID){
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(true);
        departmentChangeLeaderPane.setVisible(false);


        initStaffMembersListView(departmentID);


        departmentNameLabel.setText(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getDepartmentName());
        leaderStaffLabel.setText(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getLeaderStaffMemberID());
        changeLeaderDepartmentID = WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getDepartmentID();



    }

    private void initStaffTableView(){
        chooseLeaderStaffTableView.getItems().clear();
        ObservableSet<FullUserEntry> staffUserEntries = AccountService.getUserManager().getFilteredUsersSetProperty(((AdminUser) StageManager.getStageManager().getContext()),Roles.STAFF);

        staffUserEntries.addListener((SetChangeListener<? super FullUserEntry>) change -> {
            staffUserEntries.addAll(AccountService.getUserManager().getFilteredUsersSetProperty(((AdminUser) StageManager.getStageManager().getContext()),Roles.STAFF));
            chooseLeaderStaffTableView.refresh();
        });

        observableStaffMembersSet.addAll(staffUserEntries); /* dataSourceCSV.readData().getUsers() */;
        chooseLeaderStaffTableView.setEditable(true);



        chooseFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        chooseLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        chooseLeaderProfileImageCol.setCellValueFactory(p -> {
            ObjectProperty<ImageView> res = null;
            try {
                res = new SimpleObjectProperty<>(new ImageView(new Image(p.getValue().getProfileImagePath().toUri().toURL().toString())));
                res.get().setPreserveRatio(true);
                res.get().setFitWidth(30);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } finally {
                return res;
            }
        });


        observableStaffMembersList.setAll(observableStaffMembersSet);
        chooseLeaderStaffTableView.getItems().addAll(observableStaffMembersList);
        chooseLeaderStaffTableView.refresh();

    }


    @FXML
    private void handleChangeLeaderStaffButton(){
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        complaintsPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(true);


        departmentNameLabel1.setText(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getDepartmentName());
        leaderStaffLabel1.setText(AccountService.getUserManager().getUIDOf(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getLeaderStaffMemberID()));
        handleSelectStaffLeader();
    }

    private void handleSelectStaffLeader(){
        chooseLeaderStaffTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = chooseLeaderStaffTableView.getSelectionModel().getSelectedIndex();
                WorkspaceService.getWorkspaceManager().assignLeaderStaffMember(changeLeaderDepartmentID, chooseLeaderStaffTableView.getItems().get(index).getUID());
                sideBarPane.setDisable(false);
                initDepartmentTableView();
                handleClickOrganizations();
            }
        });

    }

    private void changeStaffLeader(String staffUID){
        System.out.println(">>>>" + staffUID);
        System.out.println(changeLeaderDepartmentID);
        System.out.println(WorkspaceService.getWorkspaceManager().assignLeaderStaffMember(changeLeaderDepartmentID, staffUID));
        System.out.println("write down");
        departmentNameLabel1.setText(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getDepartmentName());
        leaderStaffLabel1.setText(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getDepartmentID());


    }

    @FXML
    private void handleSaveLeaderStaffButton(){
        sideBarPane.setDisable(false);
        initDepartmentTableView();
        handleClickOrganizations();


    }
    private void clearDepartmentPageData(){
        observableDepartmentSet.clear();
        observableDepartmentList.clear();
        departmentsListTableView.getItems().clear();
        departmentsListTableView.refresh();

    }


    /**
     *
     * Profile page
     */

    private void setProfileLabel(){
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(admin.getUserName());
        statusAccountLabel.setText(admin.getRole().getPrettyPrinted());
        firstNameLabelAccount.setText(admin.getFirstName());
        lastNameLabel.setText(admin.getLastName());
        Image image = new Image(admin.getProfileImagePath().toUri().toString());
        picProfileSettingLabel.setImage(image);


    }

    private void clearProfilePageData(){
        userNameLabel.setText("");
        statusAccountLabel.setText("");
        firstNameLabelAccount.setText("");
        lastNameLabel.setText("");
        picProfileSettingLabel = new ImageView();
    }

    /**
     *
     * Complaint Page
     *
     *
     */


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

    private void clearComplaintPageData(){
        complaintChoiceBox.setValue("Personnel");
        personalTableView.getItems().clear();
        personalTableView.refresh();
        surroundingTableView.getItems().clear();
        surroundingTableView.refresh();
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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

    }

    @FXML
    void handleClickLogoutButton() {
        try {
            clearAllData();
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
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        initStaffTableView();

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
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        settingDetailPane.setVisible(false);

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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

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
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);

    }
    public void setMyPane() {
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        fullNameLabelAccountSetting.setText(admin.getFirstName());
        statusLabel.setText(admin.getRole().getPrettyPrinted());
    }


}
