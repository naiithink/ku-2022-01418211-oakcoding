package ku.cs.oakcoding;

import java.util.logging.Level;

import com.github.saacsos.fxrouter.OakRouter;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.StageManager;
import ku.cs.oakcoding.app.services.StageManager.MalformedFXMLIndexFileException;
import ku.cs.oakcoding.app.services.StageManager.NoControllerSpecifiedException;
import ku.cs.oakcoding.app.services.StageManager.SceneNotFoundException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            OakRouter.bind(this, primaryStage, "OakCoding", 300, 500);
            configRoute();
            // configStageManager(primaryStage);
            OakRouter.goTo("admin");
        } catch (final Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void configRoute() {
        String packageStr = "views/";
        OakRouter.when("signin", packageStr + "Signin.fxml", 300, 500);
        OakRouter.when("test", packageStr + "test.fxml", 600, 400);
        OakRouter.when("register", packageStr + "UserRegister.fxml",600,400);
        OakRouter.when("homeUser",packageStr + "UserHomepage.fxml",600,400);
        OakRouter.when("ProfileUser", packageStr + "ProfileUser.fxml",600,400);
        OakRouter.when("login", packageStr + "login.fxml",300,500);
        OakRouter.when("sign_in", packageStr + "sign_in.fxml",300,500);
//        UserRegister
        OakRouter.when("UserRegister", packageStr + "UserRegister.fxml",300,500);
        OakRouter.when("authentication", packageStr + "authentication.fxml",300,500);
        OakRouter.when("register", packageStr + "register.fxml",806,567);
        OakRouter.when("user", packageStr + "user.fxml",806,567);
        OakRouter.when("staff", packageStr + "staff.fxml",806,567);
        OakRouter.when("admin", packageStr + "admin.fxml",870,567);
    }

    private void configStageManager(Stage primaryStage) {
        StageManager stageManager = StageManager.getStageManager();

        try {
            stageManager.dispatch(OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_FILE.key())),
                                  OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_DIR.key())),
                                  this,
                                  primaryStage,
                                  OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()),
                                  Double.parseDouble(OakAppConfigs.getProperty("app.ui.width")),
                                  Double.parseDouble(OakAppConfigs.getProperty("app.ui.height"))
            );

            stageManager.defineHomeSceneFromIndexFile();
            stageManager.activate();
        } catch (MalformedFXMLIndexFileException e) {
            OakLogger.log(Level.SEVERE, "Malformed FXML index file");
            e.printStackTrace();
        } catch (SceneNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Scene not found: " + e.getMessage());
        } catch (NoControllerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
