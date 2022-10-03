package ku.cs.oakcoding.app.controllers;

import com.github.saacsos.fxrouter.OakRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterControllers {
// User Register
    @FXML
    private Label messageWhenRegisterLabel;
    @FXML
    private CheckBox confrimStudentCheckbox;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Label nameProfileUploadLabel;

    @FXML
    private PasswordField passwordConfirmField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

//  Add Staff
    @FXML
    private Pane addStaffRegisterPane;





    @FXML
    void handleProfileUpload(MouseEvent event) {
//        ตรงตัวเลยคือ อัพโหลดรูปภาพของแต่ละ register
    }

    public void handleCloseButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleMinimizeButton(javafx.scene.input.MouseEvent mouseEvent) {
        Stage stage = (Stage) ((ImageView) mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    public void handleBackButtonGoToLoginPage(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            OakRouter.goTo("authentication");
        } catch (final Exception e) {
            System.err.println("ไปที่หน้า authentication ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleRegisterUserButton(ActionEvent actionEvent) {
        //        ว่า กด register ได้ไหม ว่าติดปัญหาชื่อ username ซ้ำไรงี้ป่าวต้องเช็คก่อนถึงจะให้ record ได้
    }

    public void handleBackButtonGoToAdminPage(MouseEvent mouseEvent) {
    }

    public void handleRegisterStaffButton(ActionEvent actionEvent) {
    }

    public void handleRegisterOrganizationButton(ActionEvent actionEvent) {
    }
}