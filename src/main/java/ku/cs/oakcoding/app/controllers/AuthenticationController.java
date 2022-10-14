/**
 * @file AuthenticationController.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import ku.cs.oakcoding.app.helpers.hotspot.DataFile;
import ku.cs.oakcoding.app.models.users.User;
import ku.cs.oakcoding.app.models.users.UsersList;
import ku.cs.oakcoding.app.services.FactoryDataSourceCSV;
import ku.cs.oakcoding.app.services.data_source.csv.DataSourceCSV;
import ku.cs.oakcoding.app.services.stages.OldStageManager;
import ku.cs.oakcoding.app.services.stages.OldStageManager.PageNotFoundException;

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
    private TextField usernameTextField;

    @FXML
    private ImageView backButtonGoToGetStartedPage;

    @FXML
    private Button registerUserHereButton;

    @FXML
    private Label loginSuccessfulLabel;
    @FXML
    private Label tryAgianLabel;


    private DataSourceCSV<UsersList> dataSourceCSV;
    //    private UsersList usersList = new UsersList();
    private ObservableSet<User> observableUserSet = FXCollections.observableSet();

    @FXML
    void handleLoginButton(ActionEvent event) {
        DataSourceCSV<UsersList> dataSourceListCSV = FactoryDataSourceCSV.getDataSource(DataFile.USER,"users.csv");
        UsersList newUsersList = dataSourceListCSV.readData();
        Set<User> hashSet = newUsersList.getUsers();
        String username = usernameTextField.getText();
//        emergency !!!!!!!!!!!
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
            OldStageManager.getStageManager().setPage("register");
        } catch (PageNotFoundException e) {
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
        tryAgianLabel.setVisible(false);



    }
}
