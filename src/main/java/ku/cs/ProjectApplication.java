package ku.cs;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.Router;


import java.io.IOException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //FXRouter.bind(this, stage, "OakCoding", 300, 500);
        //configRoute();
        //FXRouter.goTo("project");

        Router.bind(this,stage,"OakCoding",300,500);
        configRoute();
        Router.goTo("project");

    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        Router.when("project",packageStr+"project.fxml");
    }




    public static void main(String[] args) {
        launch();
    }
}
