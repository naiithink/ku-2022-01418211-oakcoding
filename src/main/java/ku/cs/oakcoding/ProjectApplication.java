package ku.cs.oakcoding;

import javafx.application.Application;
import javafx.stage.Stage;
import com.github.saacsos.fxrouter.Router;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Router.bind(this, stage, "OakCoding", 300, 500);
            configRoute();
            Router.goTo("project");
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void configRoute() {
        String packageStr = "views/";
        Router.when("project", packageStr + "project.fxml", 300, 500);
        Router.when("test", packageStr + "test.fxml", 600, 400);
        Router.when("register", packageStr + "RegisterUser.fxml",600,400);
        Router.when("homeUser",packageStr + "HomeUser.fxml",600,400);
        Router.when("ProfileUser", packageStr + "ProfileUser.fxml",600,400);
        Router.when("signIn", packageStr + "LoginForm.fxml",300,500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
