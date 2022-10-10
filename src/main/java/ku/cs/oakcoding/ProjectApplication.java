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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.configurations.OakSystemDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.stages.OldStageManager;
import ku.cs.oakcoding.app.services.stages.OldStageManager.MalformedFXMLIndexFileException;
import ku.cs.oakcoding.app.services.stages.OldStageManager.NoControllerSpecifiedException;
import ku.cs.oakcoding.app.services.stages.OldStageManager.PageNotFoundException;

public class ProjectApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws NotDirectoryException,
                                                 FileNotFoundException {

        OakLogger.log(Level.INFO, "App is loading...");

        configStageManager(primaryStage);

        OakLogger.log(Level.INFO, "Welcome to " + OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()));
    }

    private void configStageManager(Stage primaryStage) throws NotDirectoryException,
                                                               FileNotFoundException {

        OldStageManager stageManager = OldStageManager.getStageManager();

        stageManager.setLogger(OakLogger.getLogger());
        stageManager.loadFontsFrom(OakResourcePrefix.getPrefix().resolve(OakAppConfigs.getProperty(OakAppDefaults.FONT_DIR.key())), 14.0);
        stageManager.setMenuBar(newMenuBar());

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

            if (OakSystemDefaults.OS_NAME.value().toLowerCase().contains("mac") == false
                && OakSystemDefaults.OS_NAME.value().toLowerCase().contains("darwin") == false) {

                stageManager.setStageControlButtonAlignLeft(false);
            }

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

    private MenuBar newMenuBar() {
        MenuItem oakCodingAboutMenuItem = new MenuItem("OakCoding");
        MenuItem contactUsAboutMenuItem = new MenuItem("ติดต่อเรา");

        Menu aboutMenu = new Menu("เกี่ยวกับ");
        aboutMenu.getItems().addAll(oakCodingAboutMenuItem,
                                    contactUsAboutMenuItem);


        MenuItem loginAccountMenuItem = new MenuItem("ลงชื่อเข้าใช้");

        Menu accountMenu = new Menu("บัญชี");
        accountMenu.getItems().addAll(loginAccountMenuItem);


        MenuItem howTosHelpMenuItem = new MenuItem("วิธีใช้");

        Menu helpMenu = new Menu("ช่วยเหลือ");
        helpMenu.getItems().addAll(howTosHelpMenuItem);

        return new MenuBar(aboutMenu, accountMenu, helpMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
