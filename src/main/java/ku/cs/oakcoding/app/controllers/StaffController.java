/**
 * @file StaffController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.users.AdminUser;
import ku.cs.oakcoding.app.models.users.ConsumerUser;
import ku.cs.oakcoding.app.models.users.StaffUser;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueService;
import ku.cs.oakcoding.app.services.stages.StageManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class StaffController implements Initializable {

    /**
     *  ALL FXML PANE
     * */



    @FXML
    private Pane settingDetailChangeUserPane;

    @FXML
    private Pane settingStaffPane;

    @FXML
    private Pane welcomeStaffPane;

    @FXML
    private Pane welcomeUserPane111;

    /**
     *  ALL IMAGEVIEW
     *
     * */

    @FXML
    private ImageView backPicture;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ImageView profileImageView1;

    @FXML
    private ImageView reportImageView;

    @FXML
    private ImageView settingImageView;

    /**
     * ALL BUTTON PANE
     * */

    @FXML
    private Button dashboardButton;

    @FXML
    private Button reportButton;

    @FXML
    private Button AccountChangeInfoButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button settingButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initPane();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("staff")) {
                setMyPane();
//                initReportTableView();
//                initComplaintTableView();
//
//                handleSelectComplaintTableView();
            }
        });
        initComplaintTableView();
        handleSelectedComplaintTableViewListener();
        setProfileLabel();
    }
    /**
     *
     * DASHBOARD PANE
     */



    /**
     *
     * CHANGE SETTING
     */

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button changePasswordButton;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private TextField usernameTextField;

    public void handleChangeDetail(ActionEvent actionEvent) {
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

    /**
     *
     * SETTING PANE
     */


    @FXML
    private Label firstNameAccountLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label fullNameLabel11121;

    @FXML
    private Label lastNameAccountLabel;
    @FXML
    private Label profileImageNameLabel;

    @FXML
    private Label userNameLabel;


    private void setProfileLabel(){
        System.out.println("Hello World123");
        StaffUser staffUser = (StaffUser) StageManager.getStageManager().getContext();
        System.out.println("Hello World345");
        userNameLabel.setText(staffUser.getUserName());
        statusAccountLabel.setText(staffUser.getRole().getPrettyPrinted());
        firstNameAccountLabel.setText(staffUser.getFirstName());
        lastNameAccountLabel.setText(staffUser.getLastName());
        Image image = new Image(staffUser.getProfileImagePath().toUri().toString());
        profileImageNameLabel.setText(staffUser.getProfileImagePath().toUri().toString());
        profileImageView.setImage(image);
    }
    /**
     *
     * Logout
     */

    @FXML
    public void handleClickLogoutButton(ActionEvent actionEvent) {
        try {
//            clearAllData();
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     *  COMPLAINT PANE
     * */

    @FXML
    private Pane createReportsUserPane;
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

    private String UID;


    public void initComplaintTableView(){

        complaintTableView.getItems().clear();

        // get department
//        System.out.println("Got: " + StageManager.getStageManager().getContext());
//        StaffUser staffUser = (StaffUser) StageManager.getStageManager().getContext();
//        System.out.println(staffUser);
//        UID = staffUser.getUID();
//        System.out.println("MY UID is " + UID);

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

    /**
     *
     *  COMPLAINT DETAIL
     * */

    @FXML
    private Pane complaintDetailPane;
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
//        initPane(complaintDetailPane);
//        sideBarPane.setDisable(true);
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
//        handleClickComplaintPane();
//        sideBarPane.setDisable(false);
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
     * Other
     */
    @FXML
    private Label numberReportOfUser;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label statusHomeLabel;

    @FXML
    private Label userNameHomeLabel;



























    /**
     * ALL PANE INIT
     * */

    public void handleClickReport(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");




        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        createReportsUserPane.setVisible(true);
        complaintDetailPane.setVisible(false);
    }

//    private void clearAllData(){
//        clearUsersPageData();
//        clearProfilePageData();
//        clearDepartmentPageData();
//        clearComplaintPageData();
//    }
//
//    private void clearUsersPageData(){
//
//        firstNameCol.setText("");
//        lastNameCol.setText("");
//        profileImageCol.setText("");
//        lastLoginCol.setText("");
//        observableUserSet = FXCollections.observableSet();
//        observableUserList = FXCollections.observableArrayList();
//        usersListTableView.getItems().clear();
//        usersListTableView.refresh();
//
//     }



    public void handleClickSetting(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));
        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");


        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        complaintDetailPane.setVisible(false);
    }

    public void handleClickDashboard(ActionEvent actionEvent) {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");


        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        complaintDetailPane.setVisible(false);
    }
    public void initPane(){

        welcomeStaffPane.setVisible(true);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        complaintDetailPane.setVisible(false);
        createReportsUserPane.setVisible(false);

    }
    public void setMyPane() {
        StaffUser staffUser = (StaffUser) StageManager.getStageManager().getContext();
        userNameHomeLabel.setText(staffUser.getFirstName());
        statusHomeLabel.setText(staffUser.getRole().getPrettyPrinted());
    }


    public void handleChangePaneToChangeDetailPane(ActionEvent actionEvent) {

        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(true);
        complaintDetailPane.setVisible(false);
        createReportsUserPane.setVisible(false);
    }

    public void handleBackUserPictureButton(MouseEvent mouseEvent) {

        welcomeStaffPane.setVisible(false);
        settingStaffPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        complaintDetailPane.setVisible(false);
        createReportsUserPane.setVisible(false);
    }


    public void handleComplaintCategoryCreateButton(ActionEvent actionEvent) {

    }

    public void handleComplaintBackButton(MouseEvent mouseEvent) {

    }
}
