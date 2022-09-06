package ku.cs.oakcoding.app.controllers;

import com.github.saacsos.fxrouter.Router;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import ku.cs.oakcoding.app.models.User.User;

import java.io.IOException;

public class homeUserController {






    @FXML
    public void handleProfileUser(MouseEvent mouseEvent) {
        try {
            Router.goTo("ProfileUser",Router.getData());
        } catch (IOException e){
            System.err.println("ไปที่หน้า ProfileUser ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
