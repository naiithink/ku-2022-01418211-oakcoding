package ku.cs.oakcoding.app.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.org.Department;
import ku.cs.oakcoding.app.models.reports.Report;
import ku.cs.oakcoding.app.models.reports.ReportType;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.models.users.Roles;
import ku.cs.oakcoding.app.services.*;
import ku.cs.oakcoding.app.services.stages.StageManager;

import org.w3c.dom.Text;

public class AdminController2 implements Initializable{

    /**
     *  ALL FXML PANE
     * */

    @FXML
    private Pane welcomePane;

    @FXML
    private Pane userPane;

    @FXML
    private Pane createReportsUserPane;

    @FXML
    private Pane organizationsPane;

    @FXML
    private Pane reportsUserPane;

    @FXML
    private Pane requestPane;

    @FXML
    private Pane settingPane;

    @FXML
    private Pane userDetailPane;

    @FXML
    private Pane departmentDetailPane;

    @FXML
    private Pane settingDetailPane;

    @FXML
    private Pane departmentChangeLeaderPane;

    @FXML
    private Pane complaintDetailPane;

    @FXML
    private Pane reportDetailPane;

    @FXML
    private Pane staffMembersInfoPane;

    @FXML
    private Pane sideBarPane;

    /**
     *  ALL IMAGEVIEW
     *
     * */

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private ImageView userImageView;

    @FXML
    private ImageView complaintsImageView;

    @FXML
    private ImageView organizationsImageView;

    @FXML
    private ImageView reportImageView;

    @FXML
    private ImageView requestImageView;

    @FXML
    private ImageView settingImageView;

    /**
     * ALL BUTTON PANE
     * */

    @FXML
    private Button userButton;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button complaintsButton;

    @FXML
    private Button organizationsButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button requestButton;

    @FXML
    private Button settingButton;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPane(welcomePane);
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("admin")) {
                initUsersTableView();
                initComplaintTableView();
                initDepartmentTableView();

                setProfileSetting();
                setMockUpProfile();

                handleSelectedUsersTableViewListener();
                handleSelectedComplaintTableViewListener();
                handleSelectedDepartmentTableViewListener();

            }
        });

    }

    /**
     *
     * DASHBOARD PANE
     */

    /**
     *
     * USER PANE
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


    @FXML
    public void handleCreateStaffMember(){
        try {
            StageManager.getStageManager().setPage("register", StageManager.getStageManager().getContext());
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initUsersTableView() {

        usersListTableView.getItems().clear();

        ObservableSet<FullUserEntry> observableUserSet = AccountService.getUserManager().getAllUsersSetProperty((AdminUser) StageManager.getStageManager().getContext());;
        ObservableList<FullUserEntry> observableUserList = FXCollections.observableArrayList();


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
        usersListTableView.setItems(observableUserList);
        usersListTableView.refresh();


    }



    public void handleSelectedUsersTableViewListener() {

        usersListTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = usersListTableView.getSelectionModel().getSelectedIndex();
                FullUserEntry fullUserEntry = usersListTableView.getItems().get(index);
                showSelectedUser(fullUserEntry);
            }
        });

    }



    /////////////// USER-DETAIL ///////////////

    @FXML
    private Label detailUserNameLabel;
    @FXML
    private Label detailUserStatusAccountLabel;
    @FXML
    private Label detailUserFirstNameLabel;
    @FXML
    private Label detailUserLastNameLabel;
    @FXML
    private ImageView detailUserPicProfileSettingLabel;

    public void showSelectedUser(FullUserEntry userEntry){
        initPane(userDetailPane);
        sideBarPane.setDisable(true);
        detailUserNameLabel.setText(userEntry.getUserName());
        detailUserStatusAccountLabel.setText(userEntry.getRole().getPrettyPrinted());
        detailUserFirstNameLabel.setText(userEntry.getFirstName());
        detailUserLastNameLabel.setText(userEntry.getLastName());
        Image image = new Image(userEntry.getProfileImagePath().toUri().toString());
        detailUserPicProfileSettingLabel.setImage(image);
    }

    @FXML
    public void handleClickBackUserDetail(){
        sideBarPane.setDisable(false);
        handleClickUserPane();

    }

    /**
     *
     *  COMPLAINT PANE
     * */

    @FXML
    private TableView<Complaint> complaintTableView;
    @FXML
    private TableColumn<Complaint, String> complaintCategoryCol;
    @FXML
    private TableColumn<Complaint, String> complaintSubjectCol;
    @FXML
    private TableColumn<Complaint, String> complaintDescriptionCol;
    @FXML
    private TableColumn<Complaint, Long>  complaintVotersCol;
    @FXML
    private TableColumn<Complaint, ComplaintStatus> complaintStatusCol;

    public void initComplaintTableView(){

        complaintTableView.getItems().clear();

        ObservableSet<Complaint> observableComplaintSet = IssueService.getIssueManager().getAllComplaintSet();
        ObservableList<Complaint> observableComplaintList = FXCollections.observableArrayList();

        complaintTableView.setEditable(true);
        complaintCategoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        complaintSubjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        complaintDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        complaintVotersCol.setCellValueFactory(p -> {
            ObjectProperty<Long> numVote = null;
            try {
                long num = p.getValue().getVoteCount();
                numVote = new SimpleObjectProperty<>(num);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return numVote;
            }
        });
        complaintStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        observableComplaintList.setAll(observableComplaintSet);
        complaintTableView.setItems(observableComplaintList);
        complaintTableView.refresh();

    }

    public void handleSelectedComplaintTableViewListener() {

        complaintTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = complaintTableView.getSelectionModel().getSelectedIndex();
                showSelectedComplaint(complaintTableView.getItems().get(index).getComplaintID());
            }
        });
    }

    ////// COMPLAINT DETAIL //////

    @FXML
    private Label reportAuthorLabel;
    @FXML
    private Label reportNumVoteLabel;
    @FXML
    private Label reportCategoryLabel;
    @FXML
    private Label reportSubjectLabel;
    @FXML
    private Label reportStatusLabel;
    @FXML
    private Label reportEvidenceLabel;
    @FXML
    private Label reportDescriptionLabel;
    @FXML
    private TextField complaintCategoryCreateTextField;

    public void showSelectedComplaint(String complaintID){
        initPane(complaintDetailPane);
        sideBarPane.setDisable(true);
        reportAuthorLabel.setText(AccountService.getUserManager().getUserNameOf(IssueService.getIssueManager().getComplaint(complaintID).getAuthorUID()));
        reportNumVoteLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getVoteCount() + "");
        reportCategoryLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getCategory());
        reportSubjectLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getSubject());
        reportStatusLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getStatus() + "");
        reportEvidenceLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getEvidencePath() + "");
        reportDescriptionLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getDescription());

    }

    @FXML
    public void handleComplaintBackButton(){
        handleClickComplaintPane();
        sideBarPane.setDisable(false);
    }

    @FXML
    public void handleComplaintCategoryCreateButton(){
        String category = complaintCategoryCreateTextField.getText();
        complaintCategoryCreateTextField.clear();
        IssueService.getIssueManager().newComplaintCategory((AdminUser) StageManager.getStageManager().getContext(),category);

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setContentText("You have created complaint category : " + category);
        alert.showAndWait();

    }

    /**
     *
     * DEPARTMENT PANE
     */

    @FXML
    private TableView<Department> departmentTableView;
    @FXML
    private TableColumn<Department, String> departmentCol;
    @FXML
    private TableColumn<Department, String> leaderStaffCol;

    public void initDepartmentTableView(){

        departmentTableView.getItems().clear();

        ObservableSet<Department> observableDepartmentSet = WorkspaceService.getWorkspaceManager().getAllDepartmentsSet();
        ObservableList<Department> observableDepartmentList = FXCollections.observableArrayList();

        departmentTableView.setEditable(true);
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        leaderStaffCol.setCellValueFactory(p -> {

            ObjectProperty<String> leaderName = new SimpleObjectProperty<>("NO LEADER");
            try {
                if (p.getValue().hasLeaderStaffMember()){
                    leaderName = new SimpleObjectProperty<>(AccountService.getUserManager().getUserNameOf(p.getValue().getLeaderStaffMemberID()));
                }
                else {
                    leaderName = new SimpleObjectProperty<>("NO LEADER");
                }

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return leaderName;
            }
        });

        observableDepartmentList.setAll(observableDepartmentSet);
        departmentTableView.setItems(observableDepartmentList);
        departmentTableView.refresh();

    }

    @FXML
    private TextField nameDepartmentTextField;
    @FXML
    private void handleRegisterDepartment(){
        String nameDepartment = nameDepartmentTextField.getText();
        nameDepartmentTextField.clear();
        if (!nameDepartment.isBlank()) {
            Department department = WorkspaceService.getWorkspaceManager().newDepartment(nameDepartment);

            if (department == null) {
                Alert alertWarning = new Alert(Alert.AlertType.WARNING);
                alertWarning.setTitle("WARNING");
                alertWarning.setContentText("This department name have already in use!!");
                alertWarning.showAndWait();
                handleClickDepartmentPane();
            } else {
                Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                alertInformation.setTitle("INFORMATION");
                alertInformation.setContentText("You have created new Department : " + nameDepartment);
                alertInformation.showAndWait();
                handleClickDepartmentPane();
            }
        }
        else {
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setTitle("WARNING");
            alertWarning.setContentText("Department name must not empty");
            alertWarning.showAndWait();
            handleClickDepartmentPane();
        }

    }

    public void handleSelectedDepartmentTableViewListener() {

        departmentTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = departmentTableView.getSelectionModel().getSelectedIndex();
                showSelectedDepartment(departmentTableView.getItems().get(index).getDepartmentID());
            }
        });

    }

    //////// DEPARTMENT DETAIL ///////

    @FXML
    private Label departmentNameLabel;
    @FXML
    private Label leaderStaffLabel;
    @FXML
    private Label departmentDetailStaffMembersLabel;
    private String changeLeaderDepartmentID;
    public void showSelectedDepartment(String departmentID){
        initPane(departmentDetailPane);
        sideBarPane.setDisable(true);
        initStaffMembersTableView(departmentID);

        Department department = WorkspaceService.getWorkspaceManager().getDepartment(departmentID);
        departmentNameLabel.setText(department.getDepartmentName());


        if (WorkspaceService.getWorkspaceManager().getAllStaffMemberSetProperty(departmentID).isEmpty()){
            departmentDetailStaffMembersLabel.setText("There is no Staff members");
        }
        else {
            departmentDetailStaffMembersLabel.setText("");
        }

        if (WorkspaceService.getWorkspaceManager().hasLeaderStaffMember(departmentID)) {
            leaderStaffLabel.setText(AccountService.getUserManager().getUserNameOf(department.getLeaderStaffMemberID()));
        }
        else {
            leaderStaffLabel.setText("NO LEADER");
        }
        changeLeaderDepartmentID = WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getDepartmentID();

    }
    @FXML
    private TableView<FullUserEntry> departmentStaffMembersTableView;
    @FXML
    private TableColumn<FullUserEntry, String> staffFirstNameCol;
    @FXML
    private TableColumn<FullUserEntry, String> staffLastNameCol;
    @FXML
    private TableColumn<FullUserEntry, ImageView> staffProfileImageCol;

    public void initStaffMembersTableView(String departmentID){
        departmentStaffMembersTableView.getItems().clear();

        ObservableSet<FullUserEntry> observableStaffDepartmentSet = WorkspaceService.getWorkspaceManager().getAllStaffMemberSetProperty(departmentID);
        ObservableList<FullUserEntry> observableStaffDepartmentList = FXCollections.observableArrayList();

        departmentStaffMembersTableView.setEditable(true);
        staffFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        staffLastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        staffProfileImageCol.setCellValueFactory(p -> {
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


        observableStaffDepartmentList.setAll(observableStaffDepartmentSet);
        departmentStaffMembersTableView.setItems(observableStaffDepartmentList);
        departmentStaffMembersTableView.refresh();

    }


    @FXML
    public void handleDepartmentBackButton(){
        handleClickDepartmentPane();
        sideBarPane.setDisable(false);
    }

    //////// CHANGE LEADER STAFF ///////

    @FXML
    private Label departmentChangeLeaderNameLabel;
    @FXML
    private Label changeLeaderStaffNameLabel;
    @FXML
    private Label chooseLeaderTableViewLabel;

    @FXML
    public void handleClickChangeLeaderStaffMemberButton(){
        initPane(departmentChangeLeaderPane);
        initChooseLeaderStaffTableView();

        if (WorkspaceService.getWorkspaceManager().getAllStaffMemberSetProperty(changeLeaderDepartmentID).isEmpty()){
            chooseLeaderTableViewLabel.setText("There is no Staff members");
        }
        else {
            chooseLeaderTableViewLabel.setText("");
        }

        Department department = WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID);
        departmentChangeLeaderNameLabel.setText(department.getDepartmentName());

        if (WorkspaceService.getWorkspaceManager().hasLeaderStaffMember(changeLeaderDepartmentID)) {
            changeLeaderStaffNameLabel.setText(AccountService.getUserManager().getUserNameOf(department.getLeaderStaffMemberID()));
        }
        else {
            changeLeaderStaffNameLabel.setText("NO LEADER");
        }

    }

    @FXML
    private TableView<FullUserEntry> chooseLeaderStaffTableView;
    @FXML
    private TableColumn<FullUserEntry, String> chooseFirstNameCol;
    @FXML
    private TableColumn<FullUserEntry, String> chooseLastNameCol;
    @FXML
    private TableColumn<FullUserEntry, ImageView> chooseLeaderProfileImageCol;

    public void initChooseLeaderStaffTableView(){
        chooseLeaderStaffTableView.getItems().clear();

        ObservableSet<FullUserEntry> observableStaffDepartmentSet = WorkspaceService.getWorkspaceManager().getAllStaffMemberSetProperty(changeLeaderDepartmentID);
        ObservableList<FullUserEntry> observableStaffDepartmentList = FXCollections.observableArrayList();

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


        observableStaffDepartmentList.setAll(observableStaffDepartmentSet);
        chooseLeaderStaffTableView.setItems(observableStaffDepartmentList);
        chooseLeaderStaffTableView.refresh();

    }
    @FXML
    public void handleClickChangeLeaderSaveButton(){
        showSelectedDepartment(changeLeaderDepartmentID);

    }

    ///////// Add Staff //////////
    @FXML
    public void handleClickAddStaffButton(){

    }













    /**
     *
     * SETTING PANE
     */

    @FXML
    private Label userNameLabel;
    @FXML
    private Label statusAccountLabel;
    @FXML
    private Label firstNameLabelAccount;
    @FXML
    private Label lastNameLabel;
    @FXML
    private ImageView picProfileSettingLabel;

    ////// MOCK UP //////

    @FXML
    private Label fullNameLabelAccountSetting;

    @FXML
    private Label statusLabel;

    public void setMockUpProfile() {
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        fullNameLabelAccountSetting.setText(admin.getFirstName());
        statusLabel.setText(admin.getRole().getPrettyPrinted());
    }

    public void setProfileSetting(){
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(admin.getUserName());
        statusAccountLabel.setText(admin.getRole().getPrettyPrinted());
        firstNameLabelAccount.setText(admin.getFirstName());
        lastNameLabel.setText(admin.getLastName());
        Image image = new Image(admin.getProfileImagePath().toUri().toString());
        picProfileSettingLabel.setImage(image);
    }
    @FXML
    public void handleClickChangeSettingPane(){
        initPane(settingDetailPane);
        sideBarPane.setDisable(true);
    }

    /////////////// CHANGE SETTING ///////////////

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField oldPasswordField;
    @FXML
    private TextField newPasswordField;
    @FXML
    private TextField confirmPasswordField;

    @FXML
    public void handleClickBackChangeSettingButton(){
        initPane(settingPane);
        handleClickSettingPane();
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
            alertInformation.setContentText("Your password has been changed");
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
            alertWarning.setContentText("You cannot change your password, Please try again!!");
            alertWarning.showAndWait();
        }

    }







































    /**
     * ALL PANE INIT
     * */

    public void hideAllPane(){
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        complaintDetailPane.setVisible(false);
        reportDetailPane.setVisible(false);
        staffMembersInfoPane.setVisible(false);
    }

    public void initPane(Pane pane){
        hideAllPane();
        pane.setVisible(true);

    }
    @FXML
    public void handleClickUserPane() {
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

        initPane(userPane);
        initUsersTableView();

    }

    @FXML
    public void handleClickDashBoardPane(){
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

        initPane(welcomePane);
    }

    @FXML
    public void handleClickComplaintPane(){
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

        initPane(createReportsUserPane);
        initComplaintTableView();
    }

    @FXML
    public void handleClickDepartmentPane(){
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

        initPane(organizationsPane);
        initDepartmentTableView();

    }

    @FXML
    public void handleClickReportPane(){
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

        initPane(reportsUserPane);
    }

    @FXML
    public void handleClickRequestPane(){
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

        initPane(requestPane);


    }

    @FXML
    public void handleClickSettingPane(){
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

        initPane(settingPane);


    }

    @FXML
    public void handleClickLogoutButton(){
        try {
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * bug fixed
     */

    @FXML
    public void handleAddStaffPage(){}
    @FXML
    public void handleReportDetailBackButton(){}

    @FXML
    public void handleReportApprovedButton(){}

    @FXML
    public void handleReportDeniedButton(){}











}
