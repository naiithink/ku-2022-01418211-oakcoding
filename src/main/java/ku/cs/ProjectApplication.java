package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.Router;


import java.io.IOException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Router.bind(this,stage,"OakCoding",300,500);
        configRoute();
        Router.start("project");

    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        Router.when("project",packageStr+"project.fxml",300,500);
        Router.when("test", packageStr+"test.fxml",600,400);
    }




    public static void main(String[] args) {
        launch();
    }
}
