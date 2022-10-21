/**
 * @file ProjectApplication.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NotDirectoryException;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.oakcoding.app.helpers.configurations.OakAppConfigs;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.configurations.OakSystemDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResource;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.services.AccountService;
import ku.cs.oakcoding.app.services.IssueService;
import ku.cs.oakcoding.app.services.WorkspaceService;
import ku.cs.oakcoding.app.services.stages.StageManager;
import ku.cs.oakcoding.app.services.stages.StageManager.MalformedFXMLIndexFileException;
import ku.cs.oakcoding.app.services.stages.StageManager.NoControllerSpecifiedException;
import ku.cs.oakcoding.app.services.stages.StageManager.PageNotFoundException;

public class ProjectApplication extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;

        OakLogger.log(Level.INFO, "App is loading...");

        OakResource.fixDataDirectoryMissingNodes();

        startServices();

        configStageManager(primaryStage);

        OakLogger.log(Level.INFO, "Welcome to " + OakAppConfigs.getProperty(OakAppDefaults.APP_NAME.key()));
    }

    private void startServices() {
        AccountService accountService = new AccountService();
        accountService.start();

        WorkspaceService workspaceService = new WorkspaceService();
        workspaceService.start();

        IssueService issueService = new IssueService();
        issueService.start();
    }

    private void configStageManager(Stage primaryStage) throws NotDirectoryException,
                                                               FileNotFoundException {

        StageManager stageManager = StageManager.getStageManager();

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

            stageManager.activate(null);
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

        oakCodingAboutMenuItem.setOnAction(event -> {
            Scene creatorScene = null;

            try {
                creatorScene = new Scene(FXMLLoader.load(OakResourcePrefix.getPrefix().resolve("fxml").resolve("creator.fxml").toUri().toURL()), 600.0, 400.0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Stage creatorStage = new Stage();
    
            creatorStage.initModality(Modality.WINDOW_MODAL);
            creatorStage.setScene(creatorScene);
            creatorStage.initOwner(this.primaryStage);

            creatorStage.show();
        });

        Menu aboutMenu = new Menu("เกี่ยวกับ");
        aboutMenu.getItems().addAll(oakCodingAboutMenuItem);

        MenuItem howTosHelpMenuItem = new MenuItem("วิธีใช้");

        Menu helpMenu = new Menu("ช่วยเหลือ");
        helpMenu.getItems().addAll(howTosHelpMenuItem);

        return new MenuBar(aboutMenu, helpMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
