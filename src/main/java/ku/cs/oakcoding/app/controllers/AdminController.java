/**
 * @file AdminController.java
 *
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

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
    private Pane createReportsUserPane;

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
    private Pane reportsUserPane;

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

    private Path evidence;
    @FXML
    private Label fileUpload;

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


    private ObservableSet<Complaint> observableComplaintSet = FXCollections.observableSet();
    private ObservableList<Complaint> observableComplaintList = FXCollections.observableArrayList() ;

    /**
     *
     * Detail Complaint page
     */

    @FXML
    private Pane detailComplaintPane;

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


    private String complaintID;


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
     *
     *
     *  report pane
     *
     */



    @FXML
    private TableView<Report> reportTableView;

    @FXML
    private TableColumn<Report, ReportType> reportTypeCol;

    @FXML
    private TableColumn<Report, String> reportDescriptionCol;

    @FXML
    private TableColumn<Report, String> reportTargetCol;

    @FXML
    private ChoiceBox<String> reportTypeChoiceBox;

    private ObservableSet<Report> observableReportSet = FXCollections.observableSet();
    private ObservableList<Report> observableReportList = FXCollections.observableArrayList() ;

    /**
     *
     *  Report Detail Pane
     *
     */

    @FXML
    private Pane reportDetail;

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

    /**
     *
     *
     * StaffMembersinfo PAGe
     */

    @FXML
    private Pane staffMembersInfoPane;

    @FXML
    private TableView<FullUserEntry> staffMemberLeftTableView;
    @FXML
    private TableColumn<FullUserEntry, ImageView> profileImageStaffLeftCol;

    @FXML
    private TableColumn<FullUserEntry, String> firstNameStaffLeftCol;

    private ObservableSet<FullUserEntry> observableLeftStaffSet = FXCollections.observableSet();
    private ObservableList<FullUserEntry> observableLeftStaffList = FXCollections.observableArrayList();



    @FXML
    private TableView<FullUserEntry> staffMemberRightTableView;

    @FXML
    private TableColumn<FullUserEntry, ImageView> profileImageStaffRightCol;

    @FXML
    private TableColumn<FullUserEntry, String> firstNameStaffRightCol;

    private ObservableSet<FullUserEntry> observableRightStaffSet = FXCollections.observableSet();
    private ObservableList<FullUserEntry> observableRightStaffList = FXCollections.observableArrayList() ;






    /**
     * methods zones
     */

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {


        initPane();
        initReportPageChoiceBox();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("admin")) {
                initUsersTableView();
                initDepartmentTableView();
                initStaffTableView();
                initComplaintTableView();
                initStaffRightTableView();
                setMyPane();
                initReportTableView();
                handleSelectedUsersTableView();
                handleSelectedDepartmentTableView();
                handleSelectComplaintTableView();
                handleSelectedReportTableView();
            }
        });
    }

    private void initStaffRightTableView(){
        observableRightStaffSet = AccountService.getUserManager().getFilteredUsersSetProperty(((AdminUser) StageManager.getStageManager().getContext()),Roles.STAFF);
        observableRightStaffSet.addListener((SetChangeListener<? super FullUserEntry>) change -> {
            observableRightStaffList.clear();
            observableRightStaffList.setAll(observableRightStaffSet);
            staffMemberRightTableView.getItems().setAll(observableRightStaffList);
            staffMemberRightTableView.refresh();
        });

        staffMemberRightTableView.setEditable(true);
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

        firstNameStaffRightCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        observableRightStaffList.setAll(observableRightStaffSet);
        staffMemberRightTableView.getItems().setAll(observableRightStaffList);
        staffMemberRightTableView.refresh();

    }

    private void initStaffLeftTableView(){

    }


    private void initReportTableView(){

        observableReportSet.addAll(IssueService.getIssueManager().getAllReportsSet());
        observableReportSet.addListener((SetChangeListener<? super Report>) change -> {
            observableReportList.clear();
            observableReportList.setAll(observableReportSet);
            reportTableView.getItems().setAll(observableReportList);
            reportTableView.refresh();
        });

        // new PropertyValueFactory<>("targetID")

        reportTableView.setEditable(true);
        reportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportTargetCol.setCellValueFactory(p -> {
            ObjectProperty<String> res = null;

            if (AccountService.getUserManager().userExists(p.getValue().getTargetID()))
                res = new SimpleObjectProperty<>(AccountService.getUserManager().getUserNameOf(p.getValue().getTargetID()));
            else
                res = new SimpleObjectProperty<>(p.getValue().getTargetID());

            return res;
        });

        observableReportList.setAll(observableReportSet);
        reportTableView.getItems().setAll(observableReportList);
        reportTableView.refresh();

    }

    private void handleSelectedReportTableView(){
        reportTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = reportTableView.getSelectionModel().getSelectedIndex();
                showSelectedReport(reportTableView.getItems().get(index).getReportID());
            }
        });
    }

    private void showSelectedReport(String reportId){
        sideBarPane.setDisable(true);
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
        reportDetail.setVisible(true);
        staffMembersInfoPane.setVisible(false);

        reportDetailAuthorLabel.setText(IssueService.getIssueManager().getReport(reportId).getAuthorID());
        reportDetailTargetLabel.setText(IssueService.getIssueManager().getReport(reportId).getTargetID());
        reportDetailTypeTargetLabel.setText(IssueService.getIssueManager().getReport(reportId).getType() + "");
        reportDetailStatusLabel.setText(IssueService.getIssueManager().getReport(reportId).getStatus() + "");
        reportDetailDescriptionLabel.setText(IssueService.getIssueManager().getReport(reportId).getDescription());





    }
    @FXML
    private void handleReportDetailBackButton(){
        sideBarPane.setDisable(false);
        handleClickReport();
        reportTableView.refresh();
    }

    private void initReportPageChoiceBox(){
        List<String> reportTypeChoiceBoxSelected = new ArrayList<>();
        reportTypeChoiceBoxSelected.add("BEHAVIOR");
        reportTypeChoiceBoxSelected.add("CONTENT");
        reportTypeChoiceBoxSelected.add("DEFAULT");
        reportTypeChoiceBox.setValue("DEFAULT");
        reportTypeChoiceBox.setItems(FXCollections.observableArrayList(reportTypeChoiceBoxSelected));

    }

    private void initComplaintTableView(){

        observableComplaintSet = IssueService.getIssueManager().getAllComplaintSet();
        observableComplaintSet.addListener((SetChangeListener<? super Complaint>) change -> {
            observableComplaintList.clear();
            observableComplaintList.setAll(observableComplaintSet);
            complaintTableView.getItems().setAll(observableComplaintList);
            complaintTableView.refresh();
        });

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
        complaintTableView.getItems().setAll(observableComplaintList);
        complaintTableView.refresh();

    }








    /**
     * Admin Setting
     */
    public void handleChangePaneToChangeDetailPane(ActionEvent actionEvent) {
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(true);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);

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

    private void clearAllData(){
        usersListTableView.getItems().clear();
        complaintTableView.getItems().clear();
        chooseLeaderStaffTableView.getItems().clear();
        departmentsListTableView.getItems().clear();
        reportTableView.getItems().clear();

        usersListTableView.refresh();
        complaintTableView.refresh();
        chooseLeaderStaffTableView.refresh();
        departmentsListTableView.refresh();
        reportTableView.refresh();
    }

    /**
     *
     *
     * Complaint page
     */


    @FXML
    private void handleBackButton(){
        sideBarPane.setDisable(false);
        handleClickComplaints();
        complaintTableView.refresh();
    }

    private void showDetailComplaint(String complaintID){
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        reportsUserPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(true);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
        reportAuthorLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getAuthorUID());
        reportNumVoteLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getVoteCount() + "");
        reportCategoryLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getCategory());
        reportSubjectLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getSubject());
        reportStatusLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getStatus() + "");
        reportEvidenceLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getEvidencePath() + "");
        reportDescriptionLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getDescription());

        this.complaintID = complaintID;


    }

    private void initUsersTableView() {


        /**
         * @todo Read AdminUser instance
         */


        observableUserSet = AccountService.getUserManager().getAllUsersSetProperty((AdminUser) StageManager.getStageManager().getContext());
        observableUserSet.addListener((SetChangeListener<? super FullUserEntry>) change -> {
            observableUserList.clear();
            observableUserList.setAll(observableUserSet);
            usersListTableView.getItems().setAll(observableUserList);
            usersListTableView.refresh();
        });

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
        usersListTableView.getItems().setAll(observableUserList);
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
            StageManager.getStageManager().setPage("register", StageManager.getStageManager().getContext());
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void showSelectedUser(FullUserEntry userEntry){
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(true);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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

        observableDepartmentSet = WorkspaceService.getWorkspaceManager().getAllDepartmentsSet();
        observableDepartmentSet.addListener((SetChangeListener<? super Department>) change -> {
            observableDepartmentList.clear();
            observableDepartmentList.setAll(observableDepartmentSet);
            departmentsListTableView.getItems().setAll(observableDepartmentList);
            departmentsListTableView.refresh();
        });


        departmentsListTableView.setEditable(true);


        departmentCol.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        leaderStaffCol.setCellValueFactory(new PropertyValueFactory<>("leaderStaffMemberID"));

        observableDepartmentList.setAll(observableDepartmentSet);
        departmentsListTableView.getItems().setAll(observableDepartmentList);
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
            initDepartmentTableView();
            departmentsListTableView.refresh();


        }


    }

    private void handleSelectedDepartmentTableView() {

        departmentsListTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = departmentsListTableView.getSelectionModel().getSelectedIndex();
                showSelectedDepartment(departmentsListTableView.getItems().get(index).getDepartmentID());
            }
        });

    }

    private void initStaffMembersListView(String departmentID){
        departmentStaffMembersListView.getItems().addAll(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getStaffMemberSetProperty());
    }

    @FXML
    private void handleAddStaffPage(){
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(true);
    }



    private void showSelectedDepartment(String departmentID){
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(true);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);


        initStaffMembersListView(departmentID);
        departmentNameLabel.setText(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getDepartmentName());
        leaderStaffLabel.setText(WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getLeaderStaffMemberID());
        changeLeaderDepartmentID = WorkspaceService.getWorkspaceManager().getDepartment(departmentID).getDepartmentID();



    }

    private void initStaffTableView(){

        observableStaffMembersSet = AccountService.getUserManager().getFilteredUsersSetProperty(((AdminUser) StageManager.getStageManager().getContext()),Roles.STAFF);
        observableStaffMembersSet.addListener((SetChangeListener<? super FullUserEntry>) change -> {
            observableStaffMembersList.clear();
            observableStaffMembersList.setAll(observableStaffMembersSet);
            chooseLeaderStaffTableView.getItems().setAll(observableStaffMembersList);
            chooseLeaderStaffTableView.refresh();
        });

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
        chooseLeaderStaffTableView.getItems().setAll(observableStaffMembersList);
        chooseLeaderStaffTableView.refresh();

    }


    @FXML
    private void handleChangeLeaderStaffButton(){
        sideBarPane.setDisable(true);
        welcomePane.setVisible(false);
        userPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(true);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);


        departmentNameLabel1.setText(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getDepartmentName());
        leaderStaffLabel1.setText(AccountService.getUserManager().getUIDOf(WorkspaceService.getWorkspaceManager().getDepartment(changeLeaderDepartmentID).getLeaderStaffMemberID()));
        handleSelectStaffLeader();
    }
    private void handleSelectComplaintTableView(){

        complaintTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = complaintTableView.getSelectionModel().getSelectedIndex();
                showDetailComplaint(complaintTableView.getItems().get(index).getComplaintID());
            }
        });
    }

    private void handleSelectStaffLeader(){
        chooseLeaderStaffTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = chooseLeaderStaffTableView.getSelectionModel().getSelectedIndex();
                WorkspaceService.getWorkspaceManager().assignLeaderStaffMember(changeLeaderDepartmentID, chooseLeaderStaffTableView.getItems().get(index).getUID());
                sideBarPane.setDisable(false);
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

    /**
     *
     * Complaint Page
     *
     *
     */











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
        createReportsUserPane.setVisible(true);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(true);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);

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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(true);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        settingDetailPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(true);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(true);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
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
        createReportsUserPane.setVisible(false);
        organizationsPane.setVisible(false);
        reportsUserPane.setVisible(false);
        requestPane.setVisible(false);
        settingPane.setVisible(false);
        userDetailPane.setVisible(false);
        departmentDetailPane.setVisible(false);
        settingDetailPane.setVisible(false);
        departmentChangeLeaderPane.setVisible(false);
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);

    }

    public void initPane() {
        welcomePane.setVisible(true);
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
        detailComplaintPane.setVisible(false);
        reportDetail.setVisible(false);
        staffMembersInfoPane.setVisible(false);
    }
    public void setMyPane() {
        AdminUser admin = (AdminUser) StageManager.getStageManager().getContext();
        fullNameLabelAccountSetting.setText(admin.getFirstName());
        statusLabel.setText(admin.getRole().getPrettyPrinted());
    }


}
