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
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.complaints.Complaint;
import ku.cs.oakcoding.app.models.complaints.ComplaintStatus;
import ku.cs.oakcoding.app.models.reports.Report;
import ku.cs.oakcoding.app.models.reports.ReportType;
import ku.cs.oakcoding.app.models.users.*;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueManager;
import ku.cs.oakcoding.app.services.IssueManagerStatus;
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

    @FXML
    private ChoiceBox<String> reportChooseChoiceBox;

    private String complaintID;

    @FXML
    private TextField reportAddDescriptionLabel;

    /**
     *
     * DASHBOARD PAGE
     */

    @FXML
    private Label numberReportOfUser;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StageManager.getStageManager().getCurrentPrimaryStageScenePageNickProperty().addListener((observer, oldValue, newValue) -> {
            if (newValue.equals("user")) {
                initPane();
                initReportPageChoiceBox();
                initComplaintFilterChoiceBox();
                initComplaintSortChoiceBox();
                initComplaintPageChoiceBox();
                initDetailReportPageChoiceBox();
                setMyPane();
                initReportTableView();
                initComplaintTableView();
                initDashBoard();

                handleSelectComplaintTableView();

                complaintFilterChoiceBoxListener();
                complaintSortChoiceBoxListener();


            }
        });
    }

    /**
     *
     * DASHBOARD PANE
     */

    public void initDashBoard(){
        numberReportOfUser.setText(IssueService.getIssueManager().getAllComplaintSet().size() + "");
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

        observableReportList.addAll(observableReportSet);
        reportTableView.getItems().setAll(observableReportList);
        reportTableView.refresh();

    }

    private void initReportPageChoiceBox(){
        List<String>reportTypeChoiceBoxSelected = new ArrayList<>();
        reportTypeChoiceBoxSelected.add("BEHAVIOR");
        reportTypeChoiceBoxSelected.add("CONTENT");
        reportTypeChoiceBoxSelected.add("DEFAULT");
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

        complaintCategoryChoiceBox.setItems(FXCollections.observableArrayList(IssueService.getIssueManager().getAllCategorySet()));


    }
    @FXML
    private void handleReportComplaint(){
        String discrip = reportAddDescriptionLabel.getText();
        if (Objects.isNull(discrip))
            discrip = "";
        if (reportChooseChoiceBox.getValue().equals("AUTHOR")){
            IssueService.getIssueManager().newReport(ReportType.BEHAVIOR,((ConsumerUser)StageManager.getStageManager().getContext()).getUID(),
                    IssueService.getIssueManager().getComplaint(complaintID).getAuthorUID(),discrip);
            reportTableView.refresh();
        }
        else if (reportChooseChoiceBox.getValue().equals("COMPLAINT")){
            IssueService.getIssueManager().newReport(ReportType.CONTENT,((ConsumerUser)StageManager.getStageManager().getContext()).getUID(),complaintID,discrip);
            reportTableView.refresh();
        }

    }

    private ObservableList<Complaint> tempListForSorted = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> complaintFilterChoiceBox;

    @FXML
    private ChoiceBox<String> complaintSortChoiceBox;

    public void initComplaintFilterChoiceBox(){
        List<String> complaintFilter = new ArrayList<>();
        complaintFilter.add("DEFAULT");
        complaintFilter.add("PENDING");
        complaintFilter.add("IN_PROGRESS");
        complaintFilter.add("RESOLVED");
        complaintFilter.add("MY_COMPLAINT");
        complaintFilterChoiceBox.setItems(FXCollections.observableArrayList(complaintFilter));
        complaintFilterChoiceBox.getSelectionModel().selectFirst();


    }

    public void initComplaintSortChoiceBox(){
        List<String> complaintSorted = new ArrayList<>();
        complaintSorted.add("ID-ASCENDING");
        complaintSorted.add("ID-DESCENDING");
        complaintSorted.add("VOTE-ASCENDING");
        complaintSorted.add("VOTE-DESCENDING");

        complaintSortChoiceBox.setItems(FXCollections.observableArrayList(complaintSorted));
        complaintSortChoiceBox.getSelectionModel().selectFirst();
    }

    public void complaintSortChoiceBoxListener(){

        complaintSortChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String partOfSpeech, String t1) {
                String new_val = t1 + "";
                System.out.println(new_val);
                if (new_val.compareTo("ID-ASCENDING") == 0)
                    initComplaintSortedTableView("ID","A");
                else if (new_val.compareTo("ID-DESCENDING") == 0)
                    initComplaintSortedTableView("ID","D");
                else if (new_val.compareTo("VOTE-ASCENDING") == 0)
                    initComplaintSortedTableView("VOTE","A");
                else if (new_val.compareTo("VOTE-DESCENDING") == 0)
                    initComplaintSortedTableView("VOTE","D");



            }
        });
    }

    public void complaintFilterChoiceBoxListener(){

        complaintFilterChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String partOfSpeech, String t1) {
                String new_val = t1 + "";
                System.out.println(new_val);
                if (new_val.compareTo("DEFAULT") == 0)
                    initComplaintTableView();
                else if (new_val.compareTo("PENDING") == 0)
                    initComplaintFilteredTableView(ComplaintStatus.PENDING);
                else if (new_val.compareTo("IN_PROGRESS") == 0)
                    initComplaintFilteredTableView(ComplaintStatus.IN_PROGRESS);
                else if (new_val.compareTo("RESOLVED") == 0)
                    initComplaintFilteredTableView(ComplaintStatus.RESOLVED);
                else if (new_val.compareTo("MY_COMPLAINT") == 0)
                    initMyComplaintFilteredTableView(((ConsumerUser)StageManager.getStageManager().getContext()).getUID());





            }
        });
    }

    private void initComplaintTableView(){
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

        tempListForSorted.setAll(observableComplaintSet);

    }
    private void initMyComplaintFilteredTableView(String authorID){
        complaintTableView.getItems().clear();


        ObservableSet<Complaint> observableComplaintSet = IssueService.getIssueManager().getMyComplaintsSetProperty(authorID);
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

        tempListForSorted.setAll(observableComplaintSet);

    }

    private void initComplaintFilteredTableView(ComplaintStatus complaintStatus){
        complaintTableView.getItems().clear();


        ObservableSet<Complaint> observableComplaintSet = IssueService.getIssueManager().getFilteredComplaintsSetProperty(complaintStatus);
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

        tempListForSorted.setAll(observableComplaintSet);

    }

    private void initComplaintSortedTableView(String sortedBy, String sortedOrder){
        complaintTableView.getItems().clear();
        ObservableList<Complaint> newList = FXCollections.observableArrayList();

        if (complaintFilterChoiceBox.getValue().compareTo("DEFAULT") == 0){
            newList.setAll(IssueService.getIssueManager().getAllComplaintSet());
        }
        else if (complaintFilterChoiceBox.getValue().compareTo("MY_COMPLAINT") == 0)
            newList.setAll(IssueService.getIssueManager().getMyComplaintsSetProperty(((ConsumerUser)StageManager.getStageManager().getContext()).getUID()));

        else {
            newList.setAll(IssueService.getIssueManager().getFilteredComplaintsSetProperty(ComplaintStatus.valueOf(complaintFilterChoiceBox.getValue())));
        }


        ObservableList<Complaint> observableComplaintList = IssueService.getIssueManager().getComparedComplaintsSetProperty(newList, sortedBy, sortedOrder);
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

        complaintTableView.setItems(observableComplaintList);
        complaintTableView.refresh();


    }

    private void handleSelectComplaintTableView(){

        complaintTableView.setOnMousePressed(e ->{
            if (e.getClickCount() == 2 && e.isPrimaryButtonDown()){
                int index = complaintTableView.getSelectionModel().getSelectedIndex();
                showDetailComplaint(complaintTableView.getItems().get(index).getComplaintID());
            }
        });
    }

    private void initDetailReportPageChoiceBox(){
        List<String>reportTypeChoiceBoxSelected = new ArrayList<>();
        reportTypeChoiceBoxSelected.add("AUTHOR");
        reportTypeChoiceBoxSelected.add("COMPLAINT");
        reportChooseChoiceBox.setValue("AUTHOR");
        reportChooseChoiceBox.setItems(FXCollections.observableArrayList(reportTypeChoiceBoxSelected));
    }

    private void showDetailComplaint(String complaintID){
        sideBarPane.setDisable(true);
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
        detailComplaintPane.setVisible(true);

        reportAuthorLabel.setText(AccountService.getUserManager().getUserNameOf(IssueService.getIssueManager().getComplaint(complaintID).getAuthorUID()));
        reportNumVoteLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getVoteCount() + "");
        reportCategoryLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getCategory());
        reportSubjectLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getSubject());
        reportStatusLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getStatus() + "");
        reportEvidenceLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getEvidencePath() + "");
        reportDescriptionLabel.setText(IssueService.getIssueManager().getComplaint(complaintID).getDescription());

        this.complaintID = complaintID;






    }
    @FXML
    private void handleVoteButton(){
        IssueService.getIssueManager().voteComplaint((ConsumerUser) StageManager.getStageManager().getContext(),this.complaintID);
        showDetailComplaint(this.complaintID);
    }

    @FXML
    private void handleBackButton(){
        sideBarPane.setDisable(false);
        handleClickCreateReport();
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
        detailComplaintPane.setVisible(false);
    }

    @FXML
    private void handleCreateButton(){
        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");
        IssueManagerStatus status =  IssueService.getIssueManager().newComplaint(((ConsumerUser) StageManager.getStageManager().getContext()).getUID()
                ,complaintCategoryChoiceBox.getValue()
                ,complaintSubjectTextField.getText()
                ,complaintDescriptionTextArea.getText()
                ,evidence
                ,null
        );

        switch (status){
            case CATEGORY_NOT_FOUND:
                alertWarning.setContentText("Category not found, please check again.");
                alertWarning.showAndWait();
                break;
            case EVIDENCE_PATH_DOES_NOT_EXIST:
                alertWarning.setContentText("please upload pictures.");
                alertWarning.showAndWait();
                break;
            case SUCCESS:
                alertWarning.setContentText("You have successfully created.");
                alertWarning.showAndWait();
                break;
        }

        sideBarPane.setDisable(false);
        handleClickCreateReport();
        initComplaintTableView();


    }


























    @FXML
    private ImageView newPic;

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
            alertInformation.setContentText("Your password has been changed");
            alertInformation.showAndWait();
            oldPasswordField.clear();
            newPasswordField.clear();
            usernameTextField.clear();
            confirmPasswordField.clear();

            try {

                StageManager.getStageManager().setPage("authentication", null);
            } catch (PageNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Page not found: " + e.getMessage());
            } finally {
                setMyPane();
                setProfileLabel();
            }

        }
        else {
            alertWarning.setContentText("คุณไม่สามารถเปลี่ยนรหัสได้ กรุณาตรวจสอบอีกครั้ง");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void handleNewProfileUpload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("อัพโหลดรูปโปรไฟล์");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Profile Image", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(StageManager.getStageManager().getPrimaryStage());

        ((ConsumerUser)StageManager.getStageManager().getContext()).setProfileImagePath(Paths.get(selectedFile.getPath()));

        handleChangePaneToChangeDetailPane();

    }

    /**
     *
     *

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
     detailComplaintPane.setVisible(false);
     }

     */

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
        detailComplaintPane.setVisible(false);
        initComplaintPageChoiceBox();
        initComplaintFilterChoiceBox();
        initComplaintSortChoiceBox();
        initComplaintTableView();

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
        detailComplaintPane.setVisible(false);
        setProfileLabel();
    }

    private void setProfileLabel(){
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameLabel.setText(consumerUser.getUserName());
        statusAccountLabel.setText(consumerUser.getRole().getPrettyPrinted());
        firstNameAccountLabel.setText(consumerUser.getFirstName());
        lastNameAccountLabel.setText(consumerUser.getLastName());
        // hotfix/0.0.2
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
        detailComplaintPane.setVisible(false);

        initDashBoard();
    }

    public void setMyPane() {
        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        userNameHomeLabel.setText(consumerUser.getFirstName());
        statusHomeLabel.setText(consumerUser.getRole().getPrettyPrinted());

    }

    public void initPane() {
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        welcomeUserPane.setVisible(true);
        settingDetailChangeUserPane.setVisible(false);
        reportsPane.setVisible(false);
        detailComplaintPane.setVisible(false);
    }

    public void handleBackUserPictureButton(){
        sideBarPane.setDisable(false);
        handleClickSetting();
    }


    public void handleChangePaneToChangeDetailPane() {
        sideBarPane.setDisable(true);
        reportsUserPane.setVisible(false);
        createReportsUserPane.setVisible(false);
        welcomeUserPane.setVisible(false);
        settingUserPane.setVisible(false);
        settingDetailChangeUserPane.setVisible(true);
        reportsPane.setVisible(false);
        detailComplaintPane.setVisible(false);

        ConsumerUser consumerUser = (ConsumerUser) StageManager.getStageManager().getContext();
        Image image = new Image(consumerUser.getProfileImagePath().toUri().toString());
        newPic.setImage(image);

    }
}
