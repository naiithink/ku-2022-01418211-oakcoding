package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;

import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "OakCoding", 800, 600);
        configRoute();
        FXRouter.goTo("project");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("project",packageStr+"project.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}
