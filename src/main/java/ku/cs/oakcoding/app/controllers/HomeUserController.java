package ku.cs.oakcoding.app.controllers;

import com.github.saacsos.fxrouter.OakRouter;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HomeUserController {

    @FXML
    public void handleProfileUser(MouseEvent mouseEvent) {
        try {
            OakRouter.goTo("ProfileUser", OakRouter.getData());
        } catch (IOException e){
            System.err.println("ไปที่หน้า ProfileUser ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
