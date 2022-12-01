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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import ku.cs.oakcoding.app.models.reports.UserUnsuspendRequest;
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

    @FXML
    private Pane departmentCategoryPane;

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
                initReportTableView();
                initRequestTableView();
                initDashBoard();

                setProfileSetting();
                setMockUpProfile();

                handleSelectedUsersTableViewListener();
                handleSelectedComplaintTableViewListener();
                handleSelectedDepartmentTableViewListener();
                handleSelectedRightStaffMembersTableViewListener();
//                handleSelectedLeftStaffMembersTableViewListener();
                handleSelectedChooseLeaderTableViewListener();
                handleSelectedReportTableViewListener();



            }
        });

    }

    /**
     *
     * DASHBOARD PANE
     */

    @FXML
    private Label complaintCountLabel;
    @FXML
    private Label reportCountLabel;
    @FXML
    private Label userCountLabel;
    @FXML
    private Label departmentCountLabel;

    public void initDashBoard(){
        complaintCountLabel.setText(IssueService.getIssueManager().getAllComplaintSet().size() + "");
        reportCountLabel.setText(IssueService.getIssueManager().getAllReportsSet().size() + "");
        userCountLabel.setText(AccountService.getUserManager().getAllUsersSet((AdminUser) StageManager.getStageManager().getContext()).size() + "");
        departmentCountLabel.setText(WorkspaceService.getWorkspaceManager().getAllDepartmentsSet().size() + "");
    }

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
    private TableColumn<FullUserEntry, String> userNameCol;
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
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
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
        usersListTableView.getSortOrder().add(lastLoginCol);
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

    ///// STAFF ONLY/////

    @FXML
    private Label userDetailDepartmentText;
    @FXML
    private Label userDetailDepartmentLabel;
    @FXML
    private Pane userDetailDepartmentBlack;



    public void showSelectedUser(FullUserEntry userEntry){
        initPane(userDetailPane);
        sideBarPane.setDisable(true);
        detailUserNameLabel.setText(userEntry.getUserName());
        detailUserStatusAccountLabel.setText(userEntry.getRole().getPrettyPrinted());
        detailUserFirstNameLabel.setText(userEntry.getFirstName());
        detailUserLastNameLabel.setText(userEntry.getLastName());
        Image image = new Image(userEntry.getProfileImagePath().toUri().toString());
        detailUserPicProfileSettingLabel.setImage(image);

        userDetailDepartmentText.setVisible(false);
        userDetailDepartmentLabel.setVisible(false);
        userDetailDepartmentBlack.setVisible(false);

        //// Staff ONLY ///
        if (userEntry.getRole() == Roles.STAFF){
            userDetailDepartmentText.setVisible(true);
            userDetailDepartmentLabel.setVisible(true);
            if (WorkspaceService.getWorkspaceManager().getDepartmentOfStaff(userEntry.getUID()) == null){
                userDetailDepartmentLabel.setText("NO DEPARTMENT");
            }
            else {
                userDetailDepartmentLabel.setText(WorkspaceService.getWorkspaceManager().getDepartmentNameOfID(WorkspaceService.getWorkspaceManager().getDepartmentOfStaff(userEntry.getUID())));
            }
            userDetailDepartmentBlack.setVisible(true);
        }

    }

    @FXML
    public void handleClickBackUserDetail(){
        sideBarPane.setDisable(false);
        handleClickUserPane();

        userDetailDepartmentText.setVisible(false);
        userDetailDepartmentLabel.setVisible(false);
        userDetailDepartmentBlack.setVisible(false);

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

            StringProperty leaderName = new SimpleStringProperty("NO LEADER");
            try {
                if (p.getValue().hasLeaderStaffMember()){
                    leaderName.set(AccountService.getUserManager().getUserNameOf(p.getValue().getLeaderStaffMemberID()));
                }
                else {
                    leaderName.set("NO LEADER");
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

    @FXML
    private Label departmentCateLabel;
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

    @FXML
    private TextField newNameDepartmentTextField;

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
    public void handleClickRenameDepartment(){
        String newName = newNameDepartmentTextField.getText();
        newNameDepartmentTextField.clear();
        if (!newName.isBlank()){
            WorkspaceService.getWorkspaceManager().renameDepartment(changeLeaderDepartmentID, WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getDepartmentName(), newName);
            showSelectedDepartment(changeLeaderDepartmentID);

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Department name must not blank");
            alert.showAndWait();

        }
    }

    @FXML
    public void handleDepartmentCateButton(){
        initPane(departmentCategoryPane);
        sideBarPane.setDisable(true);
        initDepartmentCateChoiceBox();
        initDepartmentCateListView(changeLeaderDepartmentID);
    }

    @FXML
    public void handleDepartmentBackButton(){
        handleClickDepartmentPane();
        sideBarPane.setDisable(false);
    }

    /////// DEPARTMENT CATEGORY ///////////

    @FXML
    private ChoiceBox<String> departmentCateChoiceBox;

    @FXML
    private ListView<String> departmentCateListView;

    @FXML
    public void handleClickDepartmentCateBackButton(){
        handleClickDepartmentPane();
        sideBarPane.setDisable(false);
    }

    public void initDepartmentCateChoiceBox(){
        departmentCateChoiceBox.setItems(FXCollections.observableArrayList(IssueService.getIssueManager().getAllCategorySet()));
    }

    public void initDepartmentCateListView(String departmentID){
        departmentCateListView.getItems().setAll(WorkspaceService.getWorkspaceManager().getAssignedCategoryFromDepartment(departmentID));
    }

    public void addDepartmentCate(){
        WorkspaceService.getWorkspaceManager().assignCategory(changeLeaderDepartmentID, departmentCateChoiceBox.getValue());
        handleDepartmentCateButton();
    }

    public void removeDepartmentCate(){
        WorkspaceService.getWorkspaceManager().removeCategory(changeLeaderDepartmentID, departmentCateChoiceBox.getValue());
        handleDepartmentCateButton();
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

    public void handleSelectedChooseLeaderTableViewListener() {

        chooseLeaderStaffTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = chooseLeaderStaffTableView.getSelectionModel().getSelectedIndex();
                changeLeaderStaffDepartment(chooseLeaderStaffTableView.getItems().get(index).getUID());
                handleClickChangeLeaderStaffMemberButton();
            }
        });

    }

    public void changeLeaderStaffDepartment(String staffUID){
        WorkspaceService.getWorkspaceManager().changeLeaderStaffMember(changeLeaderDepartmentID, staffUID);
    }
    @FXML
    public void handleClickChangeLeaderSaveButton(){
        showSelectedDepartment(changeLeaderDepartmentID);

    }

    ///////// Add Staff //////////
    @FXML
    public void handleClickAddStaffButton(){
        initPane(staffMembersInfoPane);
        initLeftStaffMembers();
        initRightStaffMembers();
        sideBarPane.setDisable(true);
    }
    @FXML
    public void handleAddStaffBackButton(){
        showSelectedDepartment(changeLeaderDepartmentID);
        sideBarPane.setDisable(false);

    }

    @FXML
    private TableView<FullUserEntry> staffMemberLeftTableView;
    @FXML
    private TableColumn<FullUserEntry, ImageView> profileImageStaffLeftCol;
    @FXML
    private TableColumn<FullUserEntry, String> firstNameStaffLeftCol;
    public void initLeftStaffMembers(){
        staffMemberLeftTableView.getItems().clear();

        ObservableSet<FullUserEntry> observableLeftStaffDepartmentSet = WorkspaceService.getWorkspaceManager().getAllStaffMemberSetProperty(changeLeaderDepartmentID);
        ObservableList<FullUserEntry> observableLeftStaffDepartmentList = FXCollections.observableArrayList();

        staffMemberLeftTableView.setEditable(true);
        firstNameStaffLeftCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        profileImageStaffLeftCol.setCellValueFactory(p -> {
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


        observableLeftStaffDepartmentList.setAll(observableLeftStaffDepartmentSet);
        staffMemberLeftTableView.setItems(observableLeftStaffDepartmentList);
        staffMemberLeftTableView.refresh();

    }

//    public void handleSelectedLeftStaffMembersTableViewListener() {
//
//        staffMemberLeftTableView.setOnMousePressed(e ->{
//            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
//                int index = staffMemberLeftTableView.getSelectionModel().getSelectedIndex();
//                removeStaffFromDepartment(staffMemberLeftTableView.getItems().get(index).getUID());
//                handleClickAddStaffButton();
//            }
//        });
//
//    }
//
//    public void removeStaffFromDepartment(String staffUID){
//        WorkspaceService.getWorkspaceManager().removeStaffMember(changeLeaderDepartmentID, staffUID);
//        System.out.println(WorkspaceService.getWorkspaceManager().hasLeaderStaffMember(changeLeaderDepartmentID));
//
//    }




    @FXML
    private TableView<FullUserEntry> staffMemberRightTableView;
    @FXML
    private TableColumn<FullUserEntry, ImageView> profileImageStaffRightCol;
    @FXML
    private TableColumn<FullUserEntry, String> firstNameStaffRightCol;

    public void initRightStaffMembers(){

        staffMemberRightTableView.getItems().clear();

        ObservableSet<FullUserEntry> observableRightStaffDepartmentSet = WorkspaceService.getWorkspaceManager().getALlComplementMemberSetProperty((AdminUser) StageManager.getStageManager().getContext(), changeLeaderDepartmentID);
        ObservableList<FullUserEntry> observableRightStaffDepartmentList = FXCollections.observableArrayList();

        staffMemberRightTableView.setEditable(true);
        firstNameStaffRightCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        profileImageStaffRightCol.setCellValueFactory(p -> {
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


        observableRightStaffDepartmentList.setAll(observableRightStaffDepartmentSet);
        staffMemberRightTableView.setItems(observableRightStaffDepartmentList);
        staffMemberRightTableView.refresh();

    }

    public void handleSelectedRightStaffMembersTableViewListener() {

        staffMemberRightTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = staffMemberRightTableView.getSelectionModel().getSelectedIndex();
                addStaffToDepartment(staffMemberRightTableView.getItems().get(index).getUID());
                handleClickAddStaffButton();
            }
        });

    }

    public void addStaffToDepartment(String staffUID){
        WorkspaceService.getWorkspaceManager().addStaffMember(changeLeaderDepartmentID, staffUID);
    }

    /**
     *
     * REPORT PANE
     */

    @FXML
    private TableView<Report> reportTableView;
    @FXML
    private TableColumn<Report, ReportType> reportTypeCol;

    @FXML
    private TableColumn<Report, String> reportDescriptionCol;

    @FXML
    private TableColumn<Report, String> reportTargetCol;


    public void initReportTableView(){

        reportTableView.getItems().clear();

        ObservableSet<Report> observableReportSet = IssueService.getIssueManager().getAllReportsSet();
        System.out.println(observableReportSet.size());
        for (Report repot : observableReportSet){
            System.out.println(repot);
        }
        ObservableList<Report> observableReportList = FXCollections.observableArrayList();


        reportTableView.setEditable(true);
        reportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDescriptionCol.setCellValueFactory(p -> {
            ObjectProperty<String> description = new SimpleObjectProperty<>("NO Description");
            try {
                if (!p.getValue().getDescription().isEmpty()){
                    description = new SimpleObjectProperty<>(p.getValue().getDescription());
                }
                else {
                    description = new SimpleObjectProperty<>("NO Description");
                }

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return description;
            }

        });
        reportTargetCol.setCellValueFactory(p -> {
            ObjectProperty<String> res = null;

            if (AccountService.getUserManager().userExists(p.getValue().getTargetID()))
                res = new SimpleObjectProperty<>(AccountService.getUserManager().getUserNameOf(p.getValue().getTargetID()));
            else
                res = new SimpleObjectProperty<>("report content");

            return res;
        });

        observableReportList.setAll(observableReportSet);
        reportTableView.setItems(observableReportList);
        reportTableView.refresh();

    }

    public void handleSelectedReportTableViewListener() {

        reportTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = reportTableView.getSelectionModel().getSelectedIndex();
                showSelectedReport(reportTableView.getItems().get(index).getReportID());

            }
        });

    }

    //////// REPORT DETAIL PANE /////////
    @FXML
    private Label reportDetailAuthorLabel;
    @FXML
    private Label reportDetailTargetLabel;
    @FXML
    private Label reportDetailTypeTargetLabel;
    @FXML
    private Label reportDetailStatusLabel;
    @FXML
    private Label reportDetailDescriptionLabel;

    private String reportIDTemp;

    private void showSelectedReport(String reportId){
        initPane(reportDetailPane);
        sideBarPane.setDisable(true);



        reportDetailAuthorLabel.setText(AccountService.getUserManager().getUserNameOf(IssueService.getIssueManager().getReport(reportId).getAuthorID()));
        if (AccountService.getUserManager().userExists(reportId))
            reportDetailTargetLabel.setText(AccountService.getUserManager().getUserNameOf(reportId));
        else
            reportDetailTargetLabel.setText("report content");

        reportDetailTypeTargetLabel.setText(IssueService.getIssueManager().getReport(reportId).getType() + "");
        reportDetailStatusLabel.setText(IssueService.getIssueManager().getReport(reportId).getStatus() + "");
        reportDetailDescriptionLabel.setText(IssueService.getIssueManager().getReport(reportId).getDescription());

        reportIDTemp = reportId;

    }

    @FXML
    public void handleReportDetailBackButton(){
        handleClickReportPane();
        sideBarPane.setDisable(false);
    }

    @FXML
    public void handleReportApprovedButton(){
        IssueService.getIssueManager().reviewReport((AdminUser) StageManager.getStageManager().getContext(), reportIDTemp, true);
        handleReportDetailBackButton();

    }

    @FXML
    public void handleReportDeniedButton(){
        IssueService.getIssueManager().reviewReport((AdminUser) StageManager.getStageManager().getContext(), reportIDTemp, false);
        handleReportDetailBackButton();
    }

    /**
     *
     * REQUEST PANE
     */

    @FXML
    private TableView<UserUnsuspendRequest> requestTableView;
    @FXML
    private TableColumn<UserUnsuspendRequest, String> requestAuthorCol;
    @FXML
    private TableColumn<UserUnsuspendRequest, String> requestDescriptionCol;

    public void initRequestTableView(){

        requestTableView.getItems().clear();

        ObservableSet<UserUnsuspendRequest> observableRequestSet = AccountService.getUserManager().getUserUnsuspendRequestSet();
        System.out.println(observableRequestSet.size());
        ObservableList<UserUnsuspendRequest> observableRequestList = FXCollections.observableArrayList();


        requestTableView.setEditable(true);
        requestAuthorCol.setCellValueFactory(p -> {
            ObjectProperty<String> author = null;
            try {
                author = new SimpleObjectProperty<>(AccountService.getUserManager().getUserNameOf(p.getValue().getUID()));
            }  catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return author;
            }

        });
        requestDescriptionCol.setCellValueFactory(p -> {
            ObjectProperty<String> message = new SimpleObjectProperty<>("NO Message");
            try {

                if (!p.getValue().getMessage().isEmpty()){
                    message = new SimpleObjectProperty<>(p.getValue().getMessage());
                }
                else {
                    message = new SimpleObjectProperty<>("NO Message");
                }

            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } finally {
                return message;
            }

        });

        observableRequestList.setAll(observableRequestSet);
        requestTableView.setItems(observableRequestList);
        requestTableView.refresh();

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
        sideBarPane.setDisable(false);
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
        departmentCategoryPane.setVisible(false);
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
        initDashBoard();
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
        initReportTableView();
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
        initRequestTableView();


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

}
