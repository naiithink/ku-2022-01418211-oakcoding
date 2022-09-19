package ku.cs.oakcoding;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.fxrouter.OakRouter;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            OakRouter.bind(this, stage, "OakCoding", 300, 500);
            configRoute();
            OakRouter.goTo("project");
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void configRoute() {
        String packageStr = "views/";
        OakRouter.when("project", packageStr + "project.fxml", 300, 500);
        OakRouter.when("test", packageStr + "test.fxml", 600, 400);
        OakRouter.when("register", packageStr + "register.fxml",600,400);
        OakRouter.when("homeUser",packageStr + "homeUser.fxml",600,400);
        OakRouter.when("ProfileUser", packageStr + "ProfileUser.fxml",600,400);
        OakRouter.when("signIn", packageStr + "signIn.fxml",300,500);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
