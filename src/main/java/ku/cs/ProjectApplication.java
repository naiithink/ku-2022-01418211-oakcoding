package ku.cs;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.github.saacsos.FXRouter;
import ku.cs.app.stage.Stageup;

import java.io.IOException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXRouter.bind(this, stage, "OakCoding", 300, 500);
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
