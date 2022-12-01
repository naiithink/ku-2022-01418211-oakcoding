/**
 * @file AuthenticationController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.hotspot.OakHotspot;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UserEntry;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.stages.StageManager;

public class AuthenticationController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button getStartedButton;

    @FXML
    private Pane getStartedPane;

    @FXML
    private Button loginButton;

    @FXML
    private Pane loginPane;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private ImageView backButtonGoToGetStartedPage;

    @FXML
    private Button registerUserHereButton;

    @FXML
    private Button suspendReportButton;

    @FXML
    private Label loginSuccessfulLabel;
    @FXML
    private Label tryAgianLabel;


    // private DataSourceCSV<UsersList> dataSourceCSV;
    //    private UsersList usersList = new UsersList();
    private ObservableSet<User> observableUserSet = FXCollections.observableSet();

    @FXML
    void handleLoginButton(ActionEvent event) {
        // DataSourceCSV<UsersList> dataSourceListCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER,"users.csv");
        // UsersList newUsersList = dataSourceListCSV.readData();
        // Set<User> hashSet = newUsersList.getUsers();
        String userName = userNameTextField.getText();
        String password = passwordField.getText();
        Set<FullUserEntry> entries = AccountService.getUserManager().getAllUsersSet(AccountService.getUserManager().login("_ROOT","admin"));
        for (FullUserEntry entry: entries) {
            OakLogger.log(Level.SEVERE, entry.getFirstName());

        }
        User user = AccountService.getUserManager().login(userName, password);



        if (Objects.nonNull(user)) {
            try {
                userNameTextField.clear();
                passwordField.clear();
                switch (user.getRole()) {
                    case CONSUMER -> StageManager.getStageManager().setPage("user", user);
                    case STAFF -> StageManager.getStageManager().setPage("staff", user);
                    case ADMIN -> StageManager.getStageManager().setPage("admin", user);
                }
            } catch (StageManager.PageNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Page not found");
            }
        }
        else{
            {
                    userNameTextField.clear();
                    passwordField.clear();
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setContentText("โปรดตรวจสอบรหัสผ่านของคุณอีกครั้ง");
                    alert.showAndWait();
                }
            }
        }


    @FXML
    void handleGetStartedButtonToLoginPage(ActionEvent event) {
        if (event.getSource() == getStartedButton){
            getStartedPane.setVisible(false);
            loginPane.setVisible(true);
        }
    }

    public void handleRegisterUserHereToRegisterPage(ActionEvent actionEvent) {
        try {
            StageManager.getStageManager().setPage("register", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void handleSuspendReportUserHereToSuspendReportPage(ActionEvent actionEvent) {
        try {
            StageManager.getStageManager().setPage("suspend", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void handleBackButtonGoToGetStartedPage(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == backButtonGoToGetStartedPage){
            getStartedPane.setVisible(true);
            loginPane.setVisible(false);
        }
    }


    public void initPane(){
        getStartedPane.setVisible(true);
        loginPane.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPane();
        loginSuccessfulLabel.setVisible(false);
    }
}
