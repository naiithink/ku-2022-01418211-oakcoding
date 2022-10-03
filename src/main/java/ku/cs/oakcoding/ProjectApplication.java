package ku.cs.oakcoding;

import java.util.logging.Level;

import com.github.saacsos.fxrouter.OakRouter;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.helpers.resources.ResourcePrefix;
import ku.cs.oakcoding.app.services.StageManager;
import ku.cs.oakcoding.app.services.StageManager.MalformedFXMLIndexFileException;
import ku.cs.oakcoding.app.services.StageManager.NoControllerSpecifiedException;
import ku.cs.oakcoding.app.services.StageManager.PageNotFoundException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            OakRouter.bind(this, primaryStage, "OakCoding", 300, 500);
            configRoute();
            // configStageManager(primaryStage);
            OakRouter.goTo("signin");
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
        OakRouter.when("login", packageStr + "loginForm.fxml",300,500);

    }

    private void configStageManager(Stage primaryStage) {
        StageManager stageManager = StageManager.getStageManager();

        try {
            stageManager.bindStage(ResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_FILE.key())),
                                   ResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_DIR.key())),
                                   this,
                                   primaryStage,
                                   OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()),
                                   Double.parseDouble(OakAppConfigs.getProperty("app.ui.width")),
                                   Double.parseDouble(OakAppConfigs.getProperty("app.ui.height"))
            );

            stageManager.autoDefineHomePage();
            stageManager.activate();
        } catch (MalformedFXMLIndexFileException e) {
            OakLogger.log(Level.SEVERE, "Malformed FXML index file");
            e.printStackTrace();
        } catch (PageNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Scene not found: " + e.getMessage());
        } catch (NoControllerSpecifiedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
