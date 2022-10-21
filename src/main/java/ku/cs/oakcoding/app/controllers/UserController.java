/**
 * @file UserController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.reports.Report;
import ku.cs.oakcoding.app.models.reports.ReportType;
import ku.cs.oakcoding.app.models.users.*;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueService;
import ku.cs.oakcoding.app.services.stages.StageManager;
import ku.cs.oakcoding.app.services.stages.StageManager.PageNotFoundException;

public class UserController implements Initializable {
    @FXML
    private Pane createReportsUserPane;

    @FXML
    private Label userNameHomeLabel;

    @FXML
    private Label statusHomeLabel;

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
    private Pane reportsPane;

    @FXML
    private Button settingButton;

    @FXML
    private ImageView settingImageView;

    @FXML
    private ImageView dashboardImageView;

    @FXML
    private Button dashboardButton;

    @FXML
    private Pane settingUserPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label statusAccountLabel;

    @FXML
    private Label firstNameAccountLabel;

    @FXML
    private Label lastNameAccountLabel;

    @FXML
    private Label profileImageNameLabel;

    @FXML
    private ImageView profileImageView;
    @FXML
    private Pane settingDetailChangeUserPane;

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Pane sideBarPane;

    /**
     *
     *
     *  report page
     *
     */
    @FXML
    private TableView<Report> reportTableView;

    @FXML
    private TableColumn<Report, ReportType> reportTypeCol;

    @FXML
    private TableColumn<Report, String> reportDescriptionCol ;

    @FXML
    private TableColumn<Report, String> reportTargetCol;

    @FXML
    private ChoiceBox<String> reportTypeChoiceBox;

    private ObservableSet<Report> observableReportSet = FXCollections.observableSet();
    private ObservableList<Report> observableReportList = FXCollections.observableArrayList() ;

    /**
     *
     * Complaint Page
     */

    @FXML
    private ChoiceBox<String> complaintTypeChoiceBox;
    @FXML
    private ChoiceBox<String> complaintCategoryChoiceBox;

    @FXML
    private TextField complaintSubjectTextField;

    @FXML
    private TextArea complaintDescriptionTextArea;

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




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
        initReportPageChoiceBox();
        initComplaintPageChoiceBox();
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("user")) {
                setMyPane();
                initReportTableView();
                initComplaintTableView();
            }
        });
    }

    /**
     *
     * Report pane
     *
     */

    private void initReportTableView(){

        observableReportSet.addAll(IssueService.getIssueManager().getAllReportsSet());
        observableReportSet.addListener((SetChangeListener<? super Report>) change -> {
            observableReportList.addAll(observableReportSet);
            reportTableView.refresh();
        });

        reportTableView.setEditable(true);
        reportTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        reportTargetCol.setCellValueFactory(new PropertyValueFactory<>("TARGET_ID"));

        observableReportList.addAll(observableReportSet);
        reportTableView.getItems().setAll(observableReportList);
        reportTableView.refresh();

    }

    private void initReportPageChoiceBox(){
        List<String>reportTypeChoiceBoxSelected = new ArrayList<>();
        reportTypeChoiceBoxSelected.add("BEHAVIOR");
        reportTypeChoiceBoxSelected.add("CONTENT");
        reportTypeChoiceBoxSelected.add("DEFAULT");
        reportTypeChoiceBox.setValue("DEFAULT");
        reportTypeChoiceBox.setItems(FXCollections.observableArrayList(reportTypeChoiceBoxSelected));

    }


    /**
     *
     * Complaint pane
     *
     */

    private void initComplaintPageChoiceBox(){
        List<String> reportTypeChoiceBoxSelected = new ArrayList<>();
        reportTypeChoiceBoxSelected.add("BEHAVIOR");
        reportTypeChoiceBoxSelected.add("CONTENT");
        complaintTypeChoiceBox.setItems(FXCollections.observableArrayList(reportTypeChoiceBoxSelected));

        complaintCategoryChoiceBox.setItems(FXCollections.observableArrayList(IssueService.getIssueManager().getAllCategorySet()));


    }

    private void initComplaintTableView(){

        observableComplaintSet.addAll(IssueService.getIssueManager().getAllComplaintSet());
        observableComplaintSet.addListener((SetChangeListener<? super Complaint>) change -> {
            observableComplaintList.addAll(observableComplaintSet);
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


        observableComplaintList.addAll(observableComplaintSet);
        complaintTableView.getItems().setAll(observableComplaintList);
        complaintTableView.refresh();

    }

    @FXML
    void handleProfileUpload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("อัพโหลดรูปหลักฐาน");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Profile Image", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(StageManager.getStageManager().getPrimaryStage());

        if (Objects.nonNull(selectedFile)) {
            this.evidence = selectedFile.toPath();
            fileUpload.setText(this.evidence.getFileName().toString());
        }
    }


    @FXML
    private void handleCreateComplaintButton(){
        sideBarPane.setDisable(true);
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(true);
    }

    @FXML
    private void handleCreateButton(){
        IssueService.getIssueManager().newComplaint(((ConsumerUser) StageManager.getStageManager().getContext()).getUID()
                                                    ,complaintCategoryChoiceBox.getValue()
                                                    ,complaintSubjectTextField.getText()
                                                    ,complaintDescriptionTextArea.getText()
                                                    ,evidence
                                                    ,null
        );

        sideBarPane.setDisable(false);
        handleClickCreateReport();

    }




























    public void handleChangeDetail() {
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
            } catch (PageNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Page not found: " + e.getMessage());
            }

        }
        else {
            alertWarning.setContentText("คุณไม่สามารถเปลี่ยนรหัสได้ กรุณาตรวจสอบอีกครั้ง");
            alertWarning.showAndWait();
        }
    }

    public void handleClickReport() {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag-seleted.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        reportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");

        reportsUserPane.setVisible(true);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
    }

    public void handleClickCreateReport() {
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView
                .setImage(new Image(getClass().getResource("/images/paper-plane-seleted.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        createReportButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(true);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
    }

    public void handleClickSetting() {

        dashboardImageView.setImage(new Image(getClass().getResource("/images/home.png").toExternalForm()));
        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings-seleted.png").toExternalForm()));
        settingButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        dashboardButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
        setProfileLabel();
    }

    private void setProfileLabel(){
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(consumerUser.getUserName());
        statusAccountLabel.setText(consumerUser.getRole().getPrettyPrinted());
        firstNameAccountLabel.setText(consumerUser.getFirstName());
        lastNameAccountLabel.setText(consumerUser.getLastName());
        Image image = new Image(consumerUser.getProfileImagePath().toUri().toString());
        profileImageNameLabel.setText(consumerUser.getProfileImagePath().toUri().toString());
        profileImageView.setImage(image);
    }

    public void handleClickLogoutButton() {
        // ไว้จัดการการ logout ของ user
        oldPasswordField.clear();
        newPasswordField.clear();
        usernameTextField.clear();
        confirmPasswordField.clear();
        try {

            StageManager.getStageManager().setPage("authentication", null);
        } catch (PageNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Page not found: " + e.getMessage());
        }
    }

    public void handleClickDashboard() {

        reportImageView.setImage(new Image(getClass().getResource("/images/flag.png").toExternalForm()));
        createReportImageView.setImage(new Image(getClass().getResource("/images/paper-plane.png").toExternalForm()));
        settingImageView.setImage(new Image(getClass().getResource("/images/settings.png").toExternalForm()));
        dashboardImageView.setImage(new Image(getClass().getResource("/images/home-seleted.png").toExternalForm()));

        dashboardButton.setStyle("-fx-text-fill: #FFFFFF;" +
                "-fx-background-color: #7986CD;" +
                "-fx-border-radius: 5px;" +
                "-fx-cursor: hand;");
        createReportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        settingButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportButton.setStyle("-fx-text-fill: #C0C0C9;" +
                "-fx-font-size:18;" +
                "-fx-background-color:transparent;" +
                "-fx-cursor: hand;");
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
    }

    public void setMyPane() {
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameHomeLabel.setText(consumerUser.getFirstName());
        statusHomeLabel.setText(consumerUser.getRole().getPrettyPrinted());


//        OakLogger.log(Level.SEVERE,"no admin");
    }

    public void initPane() {
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
    }

    public void handleBackUserPictureButton(){
        sideBarPane.setDisable(false);
        handleClickSetting();
    }


    public void handleChangePaneToChangeDetailPane(ActionEvent actionEvent) {
        sideBarPane.setDisable(true);
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(true);
        reportsPane.setVisible(false);

    }
}
