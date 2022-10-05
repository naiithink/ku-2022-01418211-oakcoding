/**
 * @file ProjectApplication.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding;

import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.StageManager;
import ku.cs.oakcoding.app.services.StageManager.MalformedFXMLIndexFileException;
import ku.cs.oakcoding.app.services.StageManager.NoControllerSpecifiedException;
import ku.cs.oakcoding.app.services.StageManager.PageNotFoundException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws NotDirectoryException,
                                                 FileNotFoundException {

        OakLogger.log(Level.INFO, "App is loading...");

        configStageManager(primaryStage);

        OakLogger.log(Level.INFO, "Welcome to " + OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()));
    }

    private void configStageManager(Stage primaryStage) throws NotDirectoryException, FileNotFoundException {
        StageManager stageManager = StageManager.getStageManager();

        stageManager.setLogger(OakLogger.getLogger());
        stageManager.loadFontsFrom(OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty("app.resource.fonts.dir")), 14.0);

        try {
            stageManager.bindStage(OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_DIR.key()))
                                                                .resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_INDEX_FILE.key())),
                                   OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FXML_DIR.key())),
                                   this,
                                   primaryStage,
                                   true,
                                   OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()),
                                   Double.parseDouble(OakAppConfigs.getProperty("app.ui.width")),
                                   Double.parseDouble(OakAppConfigs.getProperty("app.ui.height"))
            );

            stageManager.setStageControlButtonAlignLeft(false);
            stageManager.autoDefineHomePage();
            stageManager.activate();
        } catch (MalformedFXMLIndexFileException e) {
            OakLogger.log(Level.SEVERE, "Malformed FXML index file");
        } catch (PageNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Scene not found: " + e.getMessage());
        } catch (NoControllerSpecifiedException e) {
            OakLogger.log(Level.SEVERE, "No controller specified: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
