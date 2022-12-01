package ku.cs.oakcoding.app.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ku.cs.oakcoding.app.models.users.UserManager;
import ku.cs.oakcoding.app.models.users.UserManagerStatus;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueService;
import ku.cs.oakcoding.app.services.stages.StageManager;


public class UnsuspendController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField messageTextField;

    public void handleSubmitButton(ActionEvent actionEvent) {
        String userName = usernameTextField.getText();
        String message = messageTextField.getText();

        Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
        alertInformation.setTitle("INFORMATION");
        Alert alertWarning = new Alert(Alert.AlertType.WARNING);
        alertWarning.setTitle("WARNING");

        if(userName.isEmpty() || userName.isBlank()){
            alertWarning.setContentText(" Please enter username ");
            alertWarning.showAndWait();
        }

        if(message.isEmpty() || message.isBlank()){
            alertWarning.setContentText(" Please enter message ");
            alertWarning.showAndWait();
        }

        if(AccountService.getUserManager().userExists(AccountService.getUserManager().getUIDOf(userName))) {
            if(AccountService.getUserManager().isActive(userName)){
                alertWarning.setContentText(" This User is not suspended ");
                alertWarning.showAndWait();
            } else if (!AccountService.getUserManager().isActive(userName)) {
                System.out.println(AccountService.getUserManager().newUserRequest(userName,message));
                alertInformation.setContentText(" Your request sent ");
                alertInformation.showAndWait();

            }
        }

        if(!userName.isEmpty() && !userName.isBlank() && !AccountService.getUserManager().userExists(AccountService.getUserManager().getUIDOf(userName))){
            alertWarning.setContentText(" User is not exist ");
            alertWarning.showAndWait();
        }
    }

    @FXML
    void handleBackButtonGoToLoginPage(MouseEvent event) {
        try {
            StageManager.getStageManager().setPage("authentication", null);
        } catch (StageManager.PageNotFoundException e) {
            e.printStackTrace();
        }

    }
}
