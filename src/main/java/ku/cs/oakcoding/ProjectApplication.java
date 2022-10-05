/**
 * @file ProjectApplication.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding;

import java.util.logging.Level;

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
        OakLogger.log(Level.INFO, "App Started");
        configStageManager(primaryStage);
    }

    private void configStageManager(Stage primaryStage) {
        StageManager stageManager = StageManager.getStageManager();

        stageManager.setLogger(OakLogger.getLogger());

        try {
            stageManager.bindStage(ResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_DIR.key()))
                                                             .resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_FILE.key())),
                                   ResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_DIR.key())),
                                   this,
                                   primaryStage,
                                   true,
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
