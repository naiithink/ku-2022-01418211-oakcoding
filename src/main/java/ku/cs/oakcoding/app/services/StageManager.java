/**
 * @file StageManager.java
 * @version 0.1.0-dev
 * 
 * @attention This version of the StageManager was written to fully support JavaFX version 17.0.2.
 * 
 * @todo Apply Optional fields
 * @todo page tree for navigation
 * @todo Multi-stage management
 * @todo Multi-screen management
 */

package ku.cs.oakcoding.app.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Manager for the primary application Stage
 * 
 * @attention This version of the StageManager was written to fully support JavaFX version 17.0.2.
 * @note This version of the StageManager only supports single-stage application
 * 
 * This class is following the singleton pattern since there must only be one instance
 * of the StageManager that takes control over the primary @link javafx.stage.Stage @unlink
 * provided by the platform.
 * 
 * Controller MUST be declared in either FXML resource file or in FXML index property file, not both.
 * Lines starting with @ref COMMENT_SYMBOL will be ignored.
 * 
 * @section Synopsis
 * 
 * \begin{enumerate}
 *      \item{Bind the primary application Stage with the StageManager's Stage}
 *      \item{Define the home page for your application}
 *      \item{Show your application Stage with page set to home}
 * \end{enumerate}
 */
public final class StageManager {

    /**
     * If required, controller to be used must be written in singleton pattern
     * 
     * Name of the method for getting controller type instance
     */
    private static final String GET_INSTANCE_METHOD_NAME;

    /**
     * Delimiter used to delimit the .fxml resource and its controller name field in a record
     * read from FXML index property file.
     */
    private static final String INDEX_PROPERTY_TOKEN;

    /**
     * Lines in FXML index property file begining with this charactor are comments and will be ignored
     */
    public static final char COMMENT_SYMBOL;

    /**
     * Default width of the Stage
     */
    private static final double DEFAULT_STAGE_WIDTH;

    /**
     * Default height of the Stage
     */
    private static final double DEFAULT_STAGE_HEIGHT;

    /**
     * Default height of the Stage title bar
     */
    private static final double DEFAULT_STAGE_TITLE_BAR_HEIGHT;

    /**
     * Style for platform-undecorated custom stage
     */
    private static final String CUSTOM_STAGE_STYLE;

    private static StageManager instance;

    /**
     * Configuration file for this StageManager
     */
    private static Optional<Properties> stageManagerConfigProperties;

    /**
     * Font resource table
     */
    private static Map<String, Font> fontTable;

    /**
     * Logger instance for this StageManager
     */
    private Logger logger;

    /**
     * Path to the FXML index property file
     */
    private Path fxmlResourceIndexPath;

    /**
     * Prefix Path of FXML files to be loaded
     */
    private Path fxmlResourcePrefixPath;

    /**
     * Table storing all of the page entries each maps to its corresponding ParentMap
     * 
     * @see PageMap
     */
    private Map<String, PageMap> pageTable;

    /**
     * A tree holding the primary Stage hierarchy
     */
    private TreeMap<String, Stage> stageTree;

    /**
     * Properties type storing all the records loaded from the FXML index property file
     */
    private Properties resourceIndexProperties;

    /**
     * Visual bound of the current screen
     */
    private Rectangle2D visualBounds;

    /**
     * The instance of the main nickName point of the JavaFX application
     */
    private Object mainApp;

    /**
     * The primary Stage of the application
     */
    private Stage primaryStage;

    /**
     * Uses platform default title bar
     */
    private StageStyle primaryStageStyle;

    /**
     * Title of the primary Stage
     */
    private String primaryStageTitle;

    /**
     * Title bar of the primary Stage
     */
    private TitleBar primaryStageTitleBar;

    /**
     * Root page of the primary Stage
     */
    private Scene primaryStageScene;

    /**
     * Root page/Node/Parent of the primary Stage
     */
    private AnchorPane primaryStageScenePage;

    /**
     * Root page/Node/Parent of the primary Stage
     */
    private Parent currentPrimaryStageScenePage;

    /**
     * Width of the primary Stage
     * 
     * @see DEFAULT_STAGE_WIDTH
     */
    private double primaryStageWidth;

    /**
     * Height of the primary Stage
     * 
     * @see DEFAULT_STAGE_HEIGHT
     */
    private double primaryStageHeight;

    /**
     * page nickName of the primary page
     */
    private String homePageNick;

    /**
     * The annotation used to annotate types, states, or behaviours required by this StageManager
     */
    @Retention (RetentionPolicy.RUNTIME)
    @Target ({ ElementType.TYPE, ElementType.METHOD })
    public @interface OakStageManager {}

    /**
     * The record type for storing a pair of page and, if required, its corresponding controller
     */
    private record PageMap(Parent parent,
                           Class<?> controllerClass,
                           Optional<Double> prefWidth,
                           Optional<Double> prefHeight,
                           Boolean inheritWidth,
                           Boolean inheritHeight) {}

    /**
     * Extended Stage
     */
    public class XStage
            extends Stage {

        private Parent titleBarPane;

        private Parent navBarPane;

        private Parent mainPage;

        private CopyOnWriteArrayList<Node> pages;

        public XStage() {
            this.titleBarPane = null;
            this.navBarPane = null;
        }

        public XStage(StageStyle style) {
            super(style);

            this.titleBarPane = null;
            this.navBarPane = null;
        }

        public XStage(StageStyle style,
                      Parent titleBarPane,
                      Parent navBarPane) {

            this(style);

            this.titleBarPane = titleBarPane;
            this.navBarPane = navBarPane;
        }

        public XStage(StageStyle style,
                      Parent navBarPane) {

            this(style, null, navBarPane);
        }

        public void addPages(Node... pages) {
            for (Node page : pages)
                this.pages.add(page);
        }

        public void setMainPage(Parent mainPage) {
            this.mainPage = mainPage;
        }
    }

    /**
     * Attribute name in an FXML parent
     */
    private enum FXMLAttribute {

        PANE_PREF_HEIGHT     ("prefHeight"),
        PANE_PREF_WIDTH      ("prefWidth");

        private final String value;

        private FXMLAttribute(String value) {
            this.value = value;
        }

        String value() {
            return value;
        }
    }

    public static class Alignment {

        public static final int[] TOP_LEFT  = { 0, 0 };

        public static final int[] MIDDLE_TOP = { 1, 0 };

        public static final int[] TOP_RIGHT = { 2, 0 };

        public static final int CENTRE      = 0b1111;

        public static final int TOP         = 0b0001;

        public static final int BOTTOM      = 0b0010;

        public static final int LEFT        = 0b0100;

        public static final int RIGHT       = 0b1000;
    }

    /**
     * Thrown to indicate that the code has attempted to manipulate a page that is not in the page table
     * 
     * @see pageTable
     * @see addPage
     * @see removePage
     */
    public final class PageNotFoundException
            extends Exception {

        public PageNotFoundException() {}

        public PageNotFoundException(String pageNick) {
            super(pageNick);
        }

        public PageNotFoundException(Throwable cause) {
            super(cause);
        }

        public PageNotFoundException(String pageNick,
                                       Throwable cause) {

            super(pageNick, cause);
        }

        public PageNotFoundException(String message,
                                       Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {

            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    /**
     * Thrown to indicate that the FXML index property file being read is malformed
     */
    public final class MalformedFXMLIndexFileException
            extends Exception {

        public MalformedFXMLIndexFileException() {}

        public MalformedFXMLIndexFileException(String message) {
            super(message);
        }

        public MalformedFXMLIndexFileException(Throwable cause) {
            super(cause);
        }

        public MalformedFXMLIndexFileException(String message,
                                               Throwable cause) {

            super(message, cause);
        }
    }

    /**
     * Thrown to indicate that there is no controller declared for a certain page
     */
    public final class NoControllerSpecifiedException
            extends Exception {

        public NoControllerSpecifiedException() {}

        public NoControllerSpecifiedException(String message) {
            super(message);
        }

        public NoControllerSpecifiedException(Throwable cause) {
            super(cause);
        }

        public NoControllerSpecifiedException(String message,
                                              Throwable cause) {

            super(message, cause);
        }
    }

    /**
     * Object holding parsed record information of a certain page
     */
    private class parentProperty {

        private String pageNick;

        private String pageNickResourceName;

        private Boolean inheritWidth;

        private Boolean inheritHeight;

        private String controllerClassName;

        public parentProperty(String pageNick,
                              String pageNickResourceName,
                              Boolean inheritWidth,
                              Boolean inheritHeight,
                              String controllerClassName) {

            this.pageNick = pageNick;
            this.pageNickResourceName = pageNickResourceName;
            this.inheritWidth = inheritWidth;
            this.inheritHeight = inheritHeight;
            this.controllerClassName = controllerClassName;
        }

        public String pageNick() {
            return pageNick;
        }

        public String pageNickResourceName() {
            return pageNickResourceName;
        }

        public Boolean getInheritWidth() {
            return inheritWidth;
        }

        public Boolean getInheritHeight() {
            return inheritHeight;
        }

        public String getControllerClassName() {
            return controllerClassName;
        }
    }

    /**
     * Parse property value from FXML index file
     * 
     * <parent_entry> "=" <fxml_resource_name.fxml> "|" <fixed_width "W" | inherit_width "w"> "," <fixed_height "H" | inherit_height "h"> "|" [ "=>" <controller> ]
     * 
     * fixed_width, fixed_height:        construct a stage using width/height defined in fxml resource file (.fxml)
     * inherit_width, inherit_height:    construct a stage using inherited width/height from its parent Stage
     * 
     * e.g.,
     *  home=home.fxml|w,H|=>com.naiithink.app.controllers.HomeController
     * 
     * @note value MUST be written in order
     */
    private static class Utils {

        public static final String RESOURCE_NAME_EXTENSION;

        public static final String MENU_BAR_RESOURCE_NAME;

        static {
            RESOURCE_NAME_EXTENSION = "fxml";
            MENU_BAR_RESOURCE_NAME = "menu_bar" + '.' + RESOURCE_NAME_EXTENSION;
        }

        /**
         * FXML index property parsing pattern
         */
        private enum IndexPropertyRegex {

            WIDTH_INHERIT                           (null,                   "w"),
            HEIGHT_INHERIT                          (null,                   "h"),
            VALIDATE                                (new int[] { 0 },        "(^\\w+)(?:=)(\\w+\\.fxml)(?:\\|)([WwHh])(?:,)([WwHh])(?:\\|)(?:(?:=>)([\\w\\.]+))?"),
            VALIDATE_NO_KEY                         (new int[] { 0 },        "(?<=\\W)*(\\w+\\.fxml)(?:\\|)([WwHh])(?:,)([WwHh])(?:\\|)(?:(?:=>)([\\w\\.]+))?"),
            VALIDATE_NO_KEY_FXML_RESOURCE_NAME      (new int[] { 1 },        null),
            VALIDATE_NO_KEY_WIDTH_INHERITANCE       (new int[] { 2 },        null),
            VALIDATE_NO_KEY_HEIGHT_INHERITANCE      (new int[] { 3 },        null),
            VALIDATE_NO_KEY_CONTROLLER_NAME         (new int[] { 4 },        null),
            VALIDATE_PARENT_ENTRY                   (new int[] { 1 },        null),
            VALIDATE_FXML_RESOURCE_NAME             (new int[] { 2 },        null),
            VALIDATE_WIDTH_INHERITANCE              (new int[] { 3 },        null),
            VALIDATE_HEIGHT_INHERITANCE             (new int[] { 4 },        null),
            VALIDATE_CONTROLLER_NAME                (new int[] { 5 },        null),
            ROOT_DILIMITER                          (new int[] { -1 },       "=" ),
            PARENT_ENTRY                            (new int[] { 0 },        "\\w+(?==)"),
            FXML_RESOURCE_NAME                      (new int[] { 0 },        "(?<=\\W)*\\w+\\.(fxml)(?=\\W+)"),
            DIMENSIONAL_INHERITANCE                 (new int[] { 1, 2 },     "(?<=\\|)([wWhH])(?:,)([wWhH])(?=\\|)"),
            CONTROLLER_NAME                         (new int[] { 0 },        "(?<==>).*");

            private final int[] groupToGet;

            private final String pattern;

            private IndexPropertyRegex(int[] groupToGet, String pattern) {
                this.groupToGet = groupToGet;
                this.pattern = pattern;
            }
        }

        public static parentProperty readProperty(boolean keyExist,
                                                  String propertyValue) throws MalformedFXMLIndexFileException {

            String pageNick;
            String fxmlResourceName;
            Boolean inheritWidth;
            Boolean inheritHeight;
            String controllerClassName;

            Pattern p;

            if (keyExist) {
                p = Pattern.compile(IndexPropertyRegex.VALIDATE.pattern);
            } else {
                p = Pattern.compile(IndexPropertyRegex.VALIDATE_NO_KEY.pattern);
            }

            Matcher m = p.matcher(propertyValue);

            if (m.find() == false) {
                throw instance.new MalformedFXMLIndexFileException();
            }

            int groupCount = m.groupCount();
            String[] result = new String[groupCount+1];

            for (int i = 1, lim = groupCount + 1; i < lim; ++i) {
                result[i] = m.group(i);
            }

            if (keyExist) {
                pageNick = result[IndexPropertyRegex.VALIDATE_PARENT_ENTRY.groupToGet[0]].trim();
                fxmlResourceName = result[IndexPropertyRegex.VALIDATE_FXML_RESOURCE_NAME.groupToGet[0]].trim();
                inheritWidth = result[IndexPropertyRegex.VALIDATE_WIDTH_INHERITANCE.groupToGet[0]].trim().equals(IndexPropertyRegex.WIDTH_INHERIT.pattern);
                inheritHeight = result[IndexPropertyRegex.VALIDATE_HEIGHT_INHERITANCE.groupToGet[0]].trim().equals(IndexPropertyRegex.HEIGHT_INHERIT.pattern);
                controllerClassName = result[IndexPropertyRegex.VALIDATE_CONTROLLER_NAME.groupToGet[0]].trim();
            } else {
                pageNick = null;
                fxmlResourceName = result[IndexPropertyRegex.VALIDATE_NO_KEY_FXML_RESOURCE_NAME.groupToGet[0]].trim();
                inheritWidth = result[IndexPropertyRegex.VALIDATE_NO_KEY_WIDTH_INHERITANCE.groupToGet[0]].trim().equals(IndexPropertyRegex.WIDTH_INHERIT.pattern);
                inheritHeight = result[IndexPropertyRegex.VALIDATE_NO_KEY_HEIGHT_INHERITANCE.groupToGet[0]].trim().equals(IndexPropertyRegex.HEIGHT_INHERIT.pattern);
                controllerClassName = result[IndexPropertyRegex.VALIDATE_NO_KEY_CONTROLLER_NAME.groupToGet[0]].trim();
            }

            return instance.new parentProperty(pageNick, fxmlResourceName, inheritWidth, inheritHeight, controllerClassName);
        }

        // public static parentProperty readPropertyNoKey(String propertyValue) throws MalformedFXMLIndexFileException {
        //     String fxmlResourceName;
        //     Boolean inheritWidth;
        //     Boolean inheritHeight;
        //     String controllerClassName;

        //     Pattern p = Pattern.compile(IndexPropertyRegex.VALIDATE_NO_KEY.pattern);
        //     Matcher m = p.matcher(propertyValue);

        //     if (m.find() == false) {
        //         throw instance.new MalformedFXMLIndexFileException();
        //     }

        //     int groupCount = m.groupCount();
        //     String[] result = new String[groupCount+1];

        //     for (int i = 0; i < groupCount; ++i) {
        //         result[i] = m.group(i);
        //     }

        //     fxmlResourceName = result[IndexPropertyRegex.VALIDATE_NO_KEY_FXML_RESOURCE_NAME.groupToGet[0]];
        //     inheritWidth = result[IndexPropertyRegex.VALIDATE_NO_KEY_WIDTH_INHERITANCE.groupToGet[0]] == IndexPropertyRegex.WIDTH_INHERIT.pattern;
        //     inheritHeight = result[IndexPropertyRegex.VALIDATE_NO_KEY_WIDTH_INHERITANCE.groupToGet[0]] == IndexPropertyRegex.HEIGHT_INHERIT.pattern;
        //     controllerClassName = result[IndexPropertyRegex.VALIDATE_NO_KEY_CONTROLLER_NAME.groupToGet[0]];

        //     return instance.new parentProperty(null, fxmlResourceName, inheritWidth, inheritHeight, controllerClassName);
        // }
    }

    static {
        GET_INSTANCE_METHOD_NAME = "getInstance";
        INDEX_PROPERTY_TOKEN = "\\=>";
        COMMENT_SYMBOL = '#';
        DEFAULT_STAGE_WIDTH = 800.0;
        DEFAULT_STAGE_HEIGHT = 600.0;
        DEFAULT_STAGE_TITLE_BAR_HEIGHT = 30.0;
        CUSTOM_STAGE_STYLE = """
                -fx-background-radius: 11;
                """;
    }

    /**
     * Contructor is kept private, singleton
     */
    private StageManager() {
        logger = Logger.getLogger(getClass().getName());

        visualBounds = Screen.getPrimary().getBounds();

        fontTable = new ConcurrentHashMap<>();
        pageTable = new ConcurrentHashMap<>();
        resourceIndexProperties = new Properties();

        primaryStage = null;
    }

    /**
     * Gets the instance of StageManager
     */
    public static StageManager getStageManager() {
        if (instance == null) {
            synchronized (StageManager.class) {
                if (instance == null) {
                    instance = new StageManager();
                }
            }
        }

        return instance;
    }

    /**
     * Binds the application Stage to StageManager's Stage
     * 
     * @param       fxmlResourceIndexPath      Path to the FXML index property file
     * @param       fxmlResourcePrefixPath     Prefix Path of FXML files to be loaded
     * @param       mainApp                     The instance of the main nickName point of the JavaFX application
     * @param       primaryStage                The primary Stage of the application
     * @param       primaryStageTitle           Title of the primary Stage
     * @param       primaryStageWidth           Width of the primary Stage
     * @param       primaryStageHeight          Height of the primary Stage
     * 
     * @throws      MalformedFXMLIndexFileException     when the FXML index property file is malformed
     */
    public void bindStage(Path fxmlResourceIndexPath,
                          Path fxmlResourcePrefixPath,
                          Object mainApp,
                          Stage primaryStage,
                          StageStyle stageStyle,
                          String primaryStageTitle,
                          double primaryStageWidth,
                          double primaryStageHeight) throws MalformedFXMLIndexFileException,
                                                            NoControllerSpecifiedException {

        Objects.nonNull(fxmlResourceIndexPath);
        Objects.nonNull(mainApp);
        Objects.nonNull(primaryStage);

        if (Application.class.isAssignableFrom(mainApp.getClass()) == false) {
            logger.log(Level.SEVERE, "Unable to take control over main JavaFX nickName, invalid argument type for parameter 'mainApp'");

            System.exit(1);
        }

        this.fxmlResourceIndexPath = fxmlResourceIndexPath;
        this.fxmlResourcePrefixPath = fxmlResourcePrefixPath;
        this.mainApp = mainApp;
        this.primaryStageStyle = stageStyle;
        this.primaryStage = primaryStage;
        this.primaryStageTitle = primaryStageTitle;
        this.primaryStageWidth = primaryStageWidth;
        this.primaryStageHeight = primaryStageHeight;

        // try {
        //     checkIfMainAppObject(mainApp);
        // } catch (NotMainAppObjectException e) {
        //     logger.log(Level.SEVERE, "Attempting to bind with non-main JavaFX application nickName");
        //     e.printStackTrace();
        // }

        try (InputStream in = Files.newInputStream(fxmlResourceIndexPath)) {

            Objects.nonNull(in);

            resourceIndexProperties.load(in);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot get to FXML index file");
        }

        String pageNickResourceName;
        String controllerClassName;
        Class<?> controllerClass;
        Method getInstanceMethod;
        Object controllerInstance;

        Optional<Double> prefWidth;
        Optional<Double> prefHeight;

        FXMLLoader loader;
        Parent parent;

        try {
            Set<String> parentEntries = resourceIndexProperties.stringPropertyNames();

            for (String pageNick : parentEntries) {

                loader = new FXMLLoader();
                controllerClassName = new String();

                prefWidth = Optional.empty();
                prefHeight = Optional.empty();

                parentProperty parentProperty = Utils.readProperty(false, resourceIndexProperties.getProperty(pageNick));

                // String[] parentPropertyFields = resourceIndexProperties.getProperty(pageNick).split(INDEX_PROPERTY_TOKEN);

                // if (parentPropertyFields.length == 0
                //     || parentPropertyFields.length > 2) {

                //     logger.log(Level.SEVERE, "FXML index property '" + pageNick + "' has no property value");

                //     continue;
                // } else if (parentPropertyFields.length > 2) {
                //     logger.log(Level.SEVERE, "Too many property field for '" + pageNick + "'");

                //     continue;
                // }

                // pageNickResourceName = Optional.ofNullable(parentPropertyFields[0])
                //                                  .orElseThrow(MalformedFXMLIndexFileException::new);

                prefWidth = Optional.of(
                    Double.valueOf(
                        getRootXMLAttribute(fxmlResourcePrefixPath.resolve(parentProperty.pageNickResourceName),
                                            null,
                                            FXMLAttribute.PANE_PREF_WIDTH.value).orElse("0.0")
                    )
                );

                prefHeight = Optional.of(
                    Double.valueOf(
                        getRootXMLAttribute(fxmlResourcePrefixPath.resolve(parentProperty.pageNickResourceName),
                                            null,
                                            FXMLAttribute.PANE_PREF_HEIGHT.value).orElse("0.0")
                    )
                );

                // if (parentPropertyFields.length == 1) {
                //     controllerClassName = getRootXMLAttribute(fxmlResourcePrefixPath.resolve(pageNickResourceName),
                //                                               FXMLLoader.FX_NAMESPACE_PREFIX,
                //                                               FXMLLoader.FX_CONTROLLER_ATTRIBUTE).orElseThrow(NoControllerSpecifiedException::new);

                //     logger.log(Level.INFO, "Using controller declared in FXML resource file: " + pageNickResourceName + " for page nickName: '" + pageNick + "'");

                //     pageTable.put(pageNick, new ParentMap(FXMLLoader.load(fxmlResourcePrefixPath.resolve(parentPropertyFields[0]).toUri().toURL()),
                //                                                null,
                //                                                prefWidth,
                //                                                prefHeight));

                //     logger.log(Level.INFO, "page added: " + pageNick + ": **/" + pageNickResourceName + " -> " + controllerClassName); 

                //     continue;
                // } else {
                //     controllerClassName = Optional.ofNullable(parentPropertyFields[1])
                //                                   .orElseThrow(MalformedFXMLIndexFileException::new);

                //     logger.log(Level.INFO, "Using controller declared in FXML index property file: " + fxmlResourceIndexPath.getFileName().toString() + " for page nickName: '" + pageNick + "'");
                // }

                if (parentProperty.controllerClassName == null) {
                    controllerClassName = getRootXMLAttribute(fxmlResourcePrefixPath.resolve(parentProperty.pageNickResourceName),
                                                              FXMLLoader.FX_NAMESPACE_PREFIX,
                                                              FXMLLoader.FX_CONTROLLER_ATTRIBUTE).orElseThrow(NoControllerSpecifiedException::new);
  
                    logger.log(Level.INFO, "Using controller declared in FXML resource file: " + parentProperty.pageNickResourceName + " for page nickName: '" + pageNick + "'");

                    pageTable.put(pageNick, new PageMap(FXMLLoader.load(fxmlResourcePrefixPath.resolve(parentProperty.pageNickResourceName).toUri().toURL()),
                                                               null,
                                                               prefWidth,
                                                               prefHeight,
                                                               parentProperty.inheritWidth,
                                                               parentProperty.inheritHeight));

                    logger.log(Level.INFO, "page added: " + pageNick + ": **/" + parentProperty.pageNickResourceName + " -> " + controllerClassName); 

                    continue;
                } else {
                    controllerClassName = Optional.ofNullable(parentProperty.controllerClassName)
                                                  .orElseThrow(MalformedFXMLIndexFileException::new);

                    logger.log(Level.INFO, "Using controller declared in FXML index property file: " + fxmlResourceIndexPath.getFileName().toString() + " for page nickName: '" + pageNick + "'");
                }

                controllerClass = Class.forName(controllerClassName);

                if (controllerClass.isAnnotationPresent(StageManager.OakStageManager.class) == false) {
                    /**
                     * @danger DO NOT pass any message read from file to any string formatter
                     */
                    String cannotValidateDeclaredControllerMessage = "Singleton controller: cannot check if the controller of file '" + parentProperty.pageNickResourceName + "' with declared controller name '" + controllerClassName + "' is valid.\n"
                                                                     + "FXML controller MUST be annotated with '@" + StageManager.OakStageManager.class.getCanonicalName() + "' interface and define a static method '" + GET_INSTANCE_METHOD_NAME + "' with an annotation '@" + StageManager.OakStageManager.class.getCanonicalName() + "' which returns the singleton instance of the controller itself";

                    logger.log(Level.SEVERE, cannotValidateDeclaredControllerMessage);

                    System.exit(1);
                }

                getInstanceMethod = controllerClass.getMethod(GET_INSTANCE_METHOD_NAME);

                if (getInstanceMethod.isAnnotationPresent(StageManager.OakStageManager.class) == false) {
                    logger.log(Level.SEVERE, "Cannot find a valid static annotated method '" + GET_INSTANCE_METHOD_NAME + "'");

                    System.exit(1);
                }

                controllerInstance = getInstanceMethod.invoke(null);

                loader.setLocation(fxmlResourcePrefixPath.resolve(parentProperty.pageNickResourceName).toUri().toURL());
                loader.setController(controllerInstance);
                parent = loader.load(); // new page(loader.load(), primaryStageWidth, primaryStageHeight);

                pageTable.put(pageNick, new PageMap(parent,
                                                           controllerClass,
                                                           prefWidth,
                                                           prefHeight,
                                                           parentProperty.inheritWidth,
                                                           parentProperty.inheritHeight));

                logger.log(Level.INFO, "page added: " + pageNick + ": **/" + parentProperty.pageNickResourceName + " => " + controllerClassName);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "Got 'IOException' while loading FXML resource: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Controller not found: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            logger.log(Level.SEVERE, "Method '" + GET_INSTANCE_METHOD_NAME + "' not found");
        } catch (IllegalAccessException e) {
            /**
             * An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array),
             * set or get a field, or invoke a method, but the currently executing method does not have access to the definition of
             * the specified class, field, method or constructor.
             */
            logger.log(Level.SEVERE, "Got illegal access: " + e.getMessage());
        } catch (InvocationTargetException e) {
            /**
             * InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
             */
            logger.log(Level.SEVERE, "Got an exception while getting instance of controller");
        }
    }

    /**
     * Binds the application Stage to StageManager's Stage
     * 
     * @param       fxmlResourceIndexPath       Path to the FXML index property file
     * @param       fxmlResourcePrefixPath      Prefix Path of FXML files to be loaded
     * @param       mainApp                     The instance of the main nickName point of the JavaFX application
     * @param       primaryStage                The primary Stage of the application
     * @param       primaryStageTitle           Title of the primary Stage
     * @param       primaryStageWidth           Width of the primary Stage
     * @param       primaryStageHeight          Height of the primary Stage
     * 
     * @throws      MalformedFXMLIndexFileException     when the FXML index property file is malformed
     */
    public void bindStage(Path fxmlResourceIndexPath,
                          Path fxmlResourcePrefixPath,
                          Object mainApp,
                          Stage primaryStage,
                          String primaryStageTitle,
                          double primaryStageWidth,
                          double primaryStageHeight) throws MalformedFXMLIndexFileException,
                                                            NoControllerSpecifiedException {

        bindStage(fxmlResourceIndexPath,
                  fxmlResourcePrefixPath,
                  mainApp,
                  primaryStage,
                  null,
                  primaryStageTitle,
                  primaryStageWidth,
                  primaryStageHeight);
    }

        /**
     * Binds the application Stage to StageManager's Stage
     * 
     * @param       fxmlResourceIndexPath       Path to the FXML index property file
     * @param       fxmlResourcePrefixPath      Prefix Path of FXML files to be loaded
     * @param       mainApp                     The instance of the main nickName point of the JavaFX application
     * @param       primaryStage                The primary Stage of the application
     * @param       transparentStageStyle       Use StageStyle.TRANSPARENT
     * @param       primaryStageTitle           Title of the primary Stage
     * @param       primaryStageWidth           Width of the primary Stage
     * @param       primaryStageHeight          Height of the primary Stage
     * 
     * @throws      MalformedFXMLIndexFileException     when the FXML index property file is malformed
     */
    public void bindStage(Path fxmlResourceIndexPath,
                          Path fxmlResourcePrefixPath,
                          Path titleBarResourcePath,
                          Object mainApp,
                          Stage primaryStage,
                          Boolean transparentStageStyle,
                          String primaryStageTitle,
                          double primaryStageWidth,
                          double primaryStageHeight) throws MalformedFXMLIndexFileException,
                                                            NoControllerSpecifiedException {

        StageStyle stageStyle;

        if (transparentStageStyle == true) {
            stageStyle = StageStyle.TRANSPARENT;
        } else {
            stageStyle = StageStyle.UNDECORATED;
        }

        bindStage(fxmlResourceIndexPath,
                  fxmlResourcePrefixPath,
                  mainApp,
                  primaryStage,
                  stageStyle,
                  primaryStageTitle,
                  primaryStageWidth,
                  primaryStageHeight);
    }

    /**
     * Binds the application Stage to StageManager's Stage
     * 
     * @param       fxmlResourceIndexPathString         String representation of the Path to the FXML index property file
     * @param       fxmlResourcePrefixPathString        String representation of the prefix Path of FXML files to be loaded
     * @param       mainApp                             The instance of the main nickName point of the JavaFX application
     * @param       primaryStage                        The primary Stage of the application
     * @param       stageStyle                          Style of the primary Stage
     * @param       primaryStageTitle                   Title of the primary Stage
     * @param       primaryStageWidth                   Width of the primary Stage
     * @param       primaryStageHeight                  Height of the primary Stage
     * 
     * @throws      MalformedFXMLIndexFileException     when the FXML index property file is malformed
     */
    public void bindStage(String fxmlResourceIndexPathString,
                          String fxmlResourcePrefixPathString,
                          Object mainApp,
                          Stage primaryStage,
                          StageStyle stageStyle,
                          String primaryStageTitle,
                          double primaryStageWidth,
                          double primaryStageHeight) throws MalformedFXMLIndexFileException,
                                                            NoControllerSpecifiedException {

        Objects.nonNull(fxmlResourceIndexPathString);

        if (Files.exists(Paths.get(fxmlResourceIndexPathString)) == false) {
            logger.log(Level.SEVERE, "FXML index file not found");

            System.exit(1);
        }

        bindStage(Paths.get(fxmlResourceIndexPathString),
                  Paths.get(fxmlResourcePrefixPathString),
                  mainApp,
                  primaryStage,
                  stageStyle,
                  primaryStageTitle,
                  primaryStageWidth,
                  primaryStageHeight);
    }

    public void bindStage(String fxmlResourceIndexPathString,
                          String fxmlResourcePrefixPathString,
                          Object mainApp,
                          Stage primaryStage,
                          String primaryStageTitle,
                          double primaryStageWidth,
                          double primaryStageHeight) throws MalformedFXMLIndexFileException,
                                                            NoControllerSpecifiedException {

        Objects.nonNull(fxmlResourceIndexPathString);

        if (Files.exists(Paths.get(fxmlResourceIndexPathString)) == false) {
            logger.log(Level.SEVERE, "FXML index file not found");

            System.exit(1);
        }

        bindStage(Paths.get(fxmlResourceIndexPathString),
                 Paths.get(fxmlResourcePrefixPathString),
                 mainApp,
                 primaryStage,
                 null,
                 primaryStageTitle,
                 primaryStageWidth,
                 primaryStageHeight);
    }

    /**
     * Gets XML attribute in the root parent
     * 
     * @param       xmlDocumentPath     Path to XML file
     * @param       nsPrefix            Attribute prefix
     * @param       attribute           Attribute name
     * 
     * @return  An Optional object containing value of the given attribute
     */
    private Optional<String> getRootXMLAttribute(Path xmlDocumentPath,
                                                 String nsPrefix,
                                                 String attribute) {

        Optional<String> result = Optional.empty();

        try (InputStream in = Files.newInputStream(xmlDocumentPath)) {

            DocumentBuilderFactory builders = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builders.newDocumentBuilder();
            Document doc = builder.parse(in);
            Element xmlRoot = doc.getDocumentElement();

            if (nsPrefix == null) {
                result = Optional.ofNullable(xmlRoot.getAttributeNode(attribute).getNodeValue());
            } else {
                result = Optional.ofNullable(xmlRoot.getAttributeNode(nsPrefix + ':' + attribute).getNodeValue());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Got 'IOException' while reading FXML file: " + e.getMessage());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Defines page nickName of the primary page to the first record in FXML index property file
     * 
     * @throws      PageNotFoundException       when the first record in FXML index property file define a page that is not in the page table
     * 
     * @see pageNick
     */
    public void autoDefineHomePage() throws PageNotFoundException {
        String parentProperty = new String();

        try (BufferedReader in = Files.newBufferedReader(fxmlResourceIndexPath, StandardCharsets.UTF_8)) {

            while (in.ready()) {
                parentProperty = in.readLine();

                if ((parentProperty.charAt(0) == COMMENT_SYMBOL) == false) {
                    break;
                }
            }

                homePageNick = Optional.ofNullable(parentProperty.split("\\=")[0])
                                      .filter(s -> pageTable.containsKey(s))
                                      .get();

                logger.log(Level.INFO, "Defined home page to '" + homePageNick + "'");

                return;
        } catch (NoSuchElementException e) {
            logger.log(Level.SEVERE, "Attempting to define unrecognizable home page nickName");
            throw new PageNotFoundException(parentProperty);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Got IOException while attempting to define home page from FXML index file");
            throw new PageNotFoundException(parentProperty, e);
        }
    }

    /**
     * Defines home page nickName to the first record in FXML index property file.
     * 
     * @param       pageNick          NickName of a page to be defined to
     * 
     * @throws      PageNotFoundException       when pageNick is not in the page table
     * 
     * @see pageNick
     */
    public boolean defineHomePageTo(String pageNick) throws PageNotFoundException {
        if (pageTable.containsKey(pageNick)) {
            this.homePageNick = pageNick;

            logger.log(Level.INFO, "Defined home page to '" + this.homePageNick + "'");
            return true;
        }

        logger.log(Level.SEVERE, "Attempting to define unrecognizable home page nickName");
        throw new PageNotFoundException(pageNick);
    }

    /**
     * Adds a page to the page table
     * 
     * @param       pageNick                    NickName for the page to be added
     * @param       sceneResourcePathString     String representation of the Path to FXML resource
     * 
     * @throws      PageNotFoundException       when pageNick is not in the page table
     * 
     * @see pageTable
     * 
     * @todo draft
     */
    public void addPage(String pageNick,
                        String sceneResourcePathString,
                        Boolean inheritWidth,
                        Boolean inheritHeight) throws FileNotFoundException {

        if (pageTable.containsKey(pageNick)) {
            logger.log(Level.SEVERE, "page nickName already exist: " + pageNick);

            return;
        }

        Path fxmlPath = fxmlResourcePrefixPath.resolve(sceneResourcePathString);

        if (Files.exists(fxmlPath)
            && Files.isRegularFile(fxmlPath)) {

            String controllerClassName;
            Class<?> controllerClass;
            Method getInstanceMethod;
            Object controllerInstance;

            FXMLLoader loader;
            Parent parent;

            try {
                controllerClassName = new String();
                loader = new FXMLLoader();

                Optional<Double> prefWidth = Optional.of(
                    Double.valueOf(
                        getRootXMLAttribute(fxmlPath,
                                            null,
                                            FXMLAttribute.PANE_PREF_WIDTH.value).orElse("0.0")
                    )
                );

                Optional<Double> prefHeight = Optional.of(
                    Double.valueOf(
                        getRootXMLAttribute(fxmlPath,
                                            null,
                                            FXMLAttribute.PANE_PREF_HEIGHT.value).orElse("0.0")
                    )
                );

                controllerClassName = getRootXMLAttribute(fxmlPath,
                                                          FXMLLoader.FX_NAMESPACE_PREFIX,
                                                          FXMLLoader.FX_CONTROLLER_ATTRIBUTE).get();

                controllerClass = Class.forName(controllerClassName);

                if (controllerClass.isAnnotationPresent(StageManager.OakStageManager.class) == false) {
                    /**
                     * @danger DO NOT pass any message read from file to any string formatter
                     */
                    String cannotValidateDeclaredControllerMessage = "Singleton controller: cannot check if the controller of file '" + sceneResourcePathString + "' with declared controller name '" + controllerClassName + "' is valid.\n"
                                                                     + "FXML controller MUST be annotated with '@" + StageManager.OakStageManager.class.getCanonicalName() + "' interface and define a static method '" + GET_INSTANCE_METHOD_NAME + "' with an annotation '@" + StageManager.OakStageManager.class.getCanonicalName() + "' which returns the singleton instance of the controller itself";

                    logger.log(Level.SEVERE, cannotValidateDeclaredControllerMessage);

                    System.exit(1);
                }

                getInstanceMethod = controllerClass.getMethod(GET_INSTANCE_METHOD_NAME);

                if (getInstanceMethod.isAnnotationPresent(StageManager.OakStageManager.class) == false) {
                    logger.log(Level.SEVERE, "Cannot find a valid static annotated method '" + GET_INSTANCE_METHOD_NAME + "'");

                    System.exit(1);
                }

                controllerInstance = getInstanceMethod.invoke(null);

                loader.setLocation(fxmlPath.toUri().toURL());
                loader.setController(controllerInstance);

                // scene = new page(loader.load(), primaryStageWidth, primaryStageHeight);

                pageTable.put(pageNick, new PageMap(loader.load(),
                                                     controllerClass,
                                                     prefWidth,
                                                     prefHeight,
                                                     inheritWidth,
                                                     inheritHeight));

                logger.log(Level.INFO, "page added: " + pageNick + ": **/" + sceneResourcePathString);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Got 'IOException' while loading FXML resource: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                logger.log(Level.SEVERE, "Controller not found: " + e.getMessage());
            } catch (NoSuchMethodException e) {
                logger.log(Level.SEVERE, "Method '" + GET_INSTANCE_METHOD_NAME + "' not found");
            } catch (IllegalAccessException e) {
                /**
                 * An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array),
                 * set or get a field, or invoke a method, but the currently executing method does not have access to the definition of
                 * the specified class, field, method or constructor.
                 */
                logger.log(Level.SEVERE, "Got illegal access: " + e.getMessage());
            } catch (InvocationTargetException e) {
                /**
                 * InvocationTargetException is a checked exception that wraps an exception thrown by an invoked method or constructor.
                 */
                logger.log(Level.SEVERE, "Got an exception while getting instance of controller");
            }
        } else {
            throw new FileNotFoundException(sceneResourcePathString);
        }
    }

    /**
     * Removes a page from the page table
     * 
     * @param       pageNick          NickName for the page to be removed
     * 
     * @throws      PageNotFoundException       when pageNick is not in the page table
     * 
     * @see pageTable
     */
    public void removePage(String pageNick) throws PageNotFoundException {
        if (pageTable.containsKey(pageNick) == false) {
            logger.log(Level.SEVERE, "Attempting to remove page that is not in the page table");
            throw new PageNotFoundException(pageNick);
        }

        pageTable.remove(pageNick);
    }

    /**
     * Sets the current page
     * 
     * @param       pageNick                      NickName for a page in the page table to be used
     * 
     * @throws      PageNotFoundException         when pageNick is not in the page table
     * 
     * @see pageTable
     * @see addPage
     * @see removePage
     * 
     * @todo option to perform smooth Stage resizing
     * @bug Smooth Stage resizing animation task cancellation
     */
    public void setPage(Stage stage,
                        String pageNick) throws PageNotFoundException {

        if (pageNick == null) {
            // defineHomeSceneFromIndexFile();
            logger.log(Level.SEVERE, "Home page has not been set");
            throw new PageNotFoundException();
        }

        if (pageTable.containsKey(pageNick) == false) {
            logger.log(Level.SEVERE, "Attempting to set unrecognized page nickName '" + pageNick + "', current page will not be changed");
        }

        PageMap mapOfPageToSet = pageTable.get(pageNick);

        if (mapOfPageToSet.inheritWidth == false) {
            double prefWidth = mapOfPageToSet.prefWidth.get();

            /**
             * @bug
             *
             * final int P = 10;
             *
             * double d = prefWidth - stage.getWidth();
             * double dx = d / P;
             * long t = (long) Math.ceil(Math.abs(d) / P);
             *
             * Timer resize = new Timer();
             *
             * resize.scheduleAtFixedRate(new TimerTask() {
             *
             *     @Override
             *     public void run() {
             *
             *         if (stage.getWidth() != prefWidth) {
             *             stage.setWidth(stage.getWidth() + dx);
             *         } else {
             *             this.cancel();
             *         }
             *     }
             * }, 0, t);
             */

            stage.setWidth(prefWidth);
        }

        if (mapOfPageToSet.inheritHeight == false) {
            stage.setHeight(mapOfPageToSet.prefHeight.get());
        }

        if (this.primaryStageStyle == null
            || this.primaryStageStyle == StageStyle.DECORATED) {

            this.primaryStageScene.setRoot(pageTable.get(pageNick).parent);
        } else {
            /**
             * Children management for Stage with custom layout
             */
            this.primaryStageScenePage.getChildren().remove(this.currentPrimaryStageScenePage);
            this.primaryStageScenePage.getChildren().add(pageTable.get(pageNick).parent);
            this.currentPrimaryStageScenePage = pageTable.get(pageNick).parent;
        }

        logger.log(Level.INFO, "Current page has been set to '" + pageNick + "'");
    }

    public void setPage(String pageNick) throws PageNotFoundException {
        setPage(this.primaryStage, pageNick);
    }

    // !!!
    /**
     * Shows the primary Stage containing the home page
     * 
     * @throws      PageNotFoundException       when the home page has not been set
     * 
     * @see pageNick
     * @see defineHomeSceneTo
     */
    public void activate() throws PageNotFoundException {
        if (homePageNick == null) {
            // autoDefineHomePage();
            logger.log(Level.SEVERE, "Cannot set initial page, home page has not been set");

            throw new PageNotFoundException();
        }

        if (primaryStageScene != null) {
            logger.log(Level.WARNING, "Stage is already active");

            return;
        }

        if (primaryStageStyle == null
            || primaryStageStyle == StageStyle.DECORATED) {

            this.primaryStageScene = new Scene(pageTable.get(homePageNick).parent,
                                               pageTable.get(homePageNick).prefWidth.get(),
                                               pageTable.get(homePageNick).prefHeight.get());

            this.primaryStage.initStyle(StageStyle.DECORATED);
            this.primaryStage.setTitle(this.primaryStageTitle);
            this.primaryStage.setScene(this.primaryStageScene);
        } else {
            craftPrimaryStage(primaryStageStyle);
        }

        this.primaryStage.show();

        logger.log(Level.INFO, "Activated Stage");
    }

    private void craftPrimaryStage(StageStyle stageStyle) {

        // this.primaryStage.setResizable(true);

        if (stageStyle != StageStyle.UNDECORATED
            && stageStyle != StageStyle.TRANSPARENT) {

            logger.log(Level.SEVERE, "Attempted to craft Stage with invalid StageStyle: " + stageStyle.name());

            System.exit(1);
        }

        this.primaryStage.initStyle(stageStyle);

        this.primaryStage.setHeight(DEFAULT_STAGE_TITLE_BAR_HEIGHT + this.primaryStageHeight);

        AnchorPane rootNode = new AnchorPane();

        rootNode.setStyle(CUSTOM_STAGE_STYLE);

        rootNode.widthProperty().addListener((observer, oldValue, newValue) -> {
            this.primaryStageTitleBar.setWidth(newValue);
        });

        rootNode.widthProperty().addListener((observer, oldValue, newValue) -> {
            this.primaryStageScenePage.setPrefWidth((double) newValue);
        });

        rootNode.heightProperty().addListener((observer, oldValue, newValue) -> {
            this.primaryStageScenePage.setPrefHeight((double) newValue - DEFAULT_STAGE_TITLE_BAR_HEIGHT);
        });

        rootNode.setPrefHeight(DEFAULT_STAGE_TITLE_BAR_HEIGHT + this.primaryStageHeight);

        this.primaryStageTitleBar = new TitleBar(this.primaryStage,
                                                 this.primaryStageTitle,
                                                 this.primaryStageWidth,
                                                 DEFAULT_STAGE_TITLE_BAR_HEIGHT);

        // add title bar here
        // rootNode.getChildren().add(pageTable.get(pageNick).parent);
        // ObservableList<Node> children = FXCollections.observableList();

        rootNode.getChildren().add(this.primaryStageTitleBar);
        rootNode.getChildren().add(pageTable.get(homePageNick).parent);

        AnchorPane.setTopAnchor(this.primaryStageTitleBar, 0.0);
        AnchorPane.setBottomAnchor(pageTable.get(homePageNick).parent, 1.0);

        Scene rootScene = new Scene(rootNode);
        //                             pageTable.get(homePageNick).prefWidth.get(),
        //                             pageTable.get(homePageNick).prefHeight.get());

        rootScene.setFill(Color.TRANSPARENT);
        rootScene.setRoot(rootNode);

        this.currentPrimaryStageScenePage = pageTable.get(homePageNick).parent;
        this.primaryStageScene = rootScene;
        this.primaryStageScenePage = rootNode;

        this.primaryStage.setScene(rootScene);
    }

    /**
     * Activates a non
     */
    public void activateChildStage(String pageNick) {
    }

    /**
     * Activates a 
     */
    public void activateSubstage(Stage parentStage,
                                 String pageNick) throws PageNotFoundException {

        Objects.nonNull(parentStage);
        Objects.nonNull(pageNick);

        if (pageTable.containsKey(pageNick) == false) {
            throw new PageNotFoundException(pageNick);
        }

        Parent newSceneParent = new Pane();
        Scene newScene = new Scene(newSceneParent);
        Stage newStage = new Stage();

        parentStage.initModality(Modality.WINDOW_MODAL);
        parentStage.initOwner(parentStage);
    }

    /**
     * Activates a sub-Stage with its parent/owner set to the primaryStage
     * 
     * @param       pageNick          page nickName of the constructing sub-Stage
     */
    public void activateSubstage(String pageNick) throws PageNotFoundException {
        activateSubstage(primaryStage, pageNick);
    }

    /**
     * Custom title bar
     */
    public final class TitleBar
            extends HBox {
        
        private final double BUTTON_PADDING = 8.0;

        private final String BUTTON_STYLE = """
            -fx-background-radius: 160;
            -fx-pref-width: 14;
            -fx-pref-height: 14;
            -fx-min-width: 1;
            -fx-min-height: 1;
        """;

        private final String CLOSE_BUTTON_COLOR = "-fx-background-color: #ed6a5e;";

        private final String MINIMIZE_BUTTON_COLOR = "-fx-background-color: #f5bf4f;";

        private final String FULL_SCREEN_BUTTON_COLOR = "-fx-background-color: #62c555;";

        private Stage stage;

        private Robot robot;

        private EventHandler<MouseEvent> handlerOnCloseRequest = e -> {
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        };

        private EventHandler<MouseEvent> handlerOnMinimizeRequest = e -> {
            stage.setIconified(true);
        };

        private EventHandler<MouseEvent> handlerOnFullScreenRequest = e -> {
            if (stage.isFullScreen()) {
                stage.setFullScreen(false);
                stage.getScene().getRoot().setStyle(CUSTOM_STAGE_STYLE);
            } else {
                stage.setFullScreen(true);
                stage.getScene().getRoot().setStyle(null);
            }
        };

        @FXML
        private Button closeButton;

        @FXML
        private Button minimizeButton;

        @FXML
        private Button fullScreenButton;

        public TitleBar(Stage stage,
                        String stageTitle,
                        double width,
                        double height) {

            this.stage = stage;

            closeButton = new Button();
            minimizeButton = new Button();
            fullScreenButton = new Button();

            closeButton.setStyle(BUTTON_STYLE + CLOSE_BUTTON_COLOR);
            closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnCloseRequest);

            minimizeButton.setStyle(BUTTON_STYLE + MINIMIZE_BUTTON_COLOR);
            minimizeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnMinimizeRequest);

            fullScreenButton.setStyle(BUTTON_STYLE + FULL_SCREEN_BUTTON_COLOR);
            fullScreenButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnFullScreenRequest);

            super.setStyle("-fx-background-color: white;");
            super.setPadding(new Insets(0, 10, 0, 10));
            super.setPrefWidth(width);
            super.setPrefHeight(height);
            super.setAlignment(Pos.CENTER_RIGHT);
            
            super.getChildren().add(closeButton);
            super.getChildren().add(minimizeButton);
            super.getChildren().add(fullScreenButton);

            super.setSpacing(BUTTON_PADDING);
            
            // titleBar.setPrefWidth(prefWidth);
            // titleBar.prefHeight(prefHeight);

            // addStageTitle(this, stageTitle);
            // addStageControlButtonStack(this);

            // if (titleBar == null) {
            //     synchronized (StageManager.TitleBarController.class) {
            //         if (titleBar == null) {
            //             titleBar = new HBox();
            //             titleBar.setPadding(new Insets(0, 10, 10, 10));
            //             // titleBar.setPrefWidth(prefWidth);
            //             // titleBar.prefHeight(prefHeight);

            //             addStageTitle(titleBar, stageTitle);
            //             addStageControlButtonStack(titleBar);
            //         }
            //     }
            // }
        }

        // public TitleBar newTitleBar(/* double prefWidth, */
        //                             /* double prefHeight, */
        //                             String stageTitle) {

        //     titleBar = new TitleBar();
        //     titleBar.setPadding(new Insets(0, 10, 10, 10));
        //     // titleBar.setPrefWidth(prefWidth);
        //     // titleBar.prefHeight(prefHeight);

        //     addStageTitle(titleBar, stageTitle);
        //     addStageControlButtonStack(titleBar);

        //     // if (titleBar == null) {
        //     //     synchronized (StageManager.TitleBarController.class) {
        //     //         if (titleBar == null) {
        //     //             titleBar = new HBox();
        //     //             titleBar.setPadding(new Insets(0, 10, 10, 10));
        //     //             // titleBar.setPrefWidth(prefWidth);
        //     //             // titleBar.prefHeight(prefHeight);

        //     //             addStageTitle(titleBar, stageTitle);
        //     //             addStageControlButtonStack(titleBar);
        //     //         }
        //     //     }
        //     // }

        //     return titleBar;
        // }

        private void addStageControlButtonStack(TitleBar titleBar) {
            StackPane buttonBar = new StackPane();

            closeButton = new Button();
            minimizeButton = new Button();
            fullScreenButton = new Button();

            closeButton.setStyle(BUTTON_STYLE + CLOSE_BUTTON_COLOR);
            closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnCloseRequest);

            minimizeButton.setStyle(BUTTON_STYLE + MINIMIZE_BUTTON_COLOR);
            minimizeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnMinimizeRequest);

            fullScreenButton.setStyle(BUTTON_STYLE + FULL_SCREEN_BUTTON_COLOR);
            fullScreenButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handlerOnFullScreenRequest);

            buttonBar.getChildren().addAll(closeButton, minimizeButton, fullScreenButton);
            buttonBar.setAlignment(Pos.CENTER_RIGHT);

            titleBar.getChildren().add(buttonBar);
        }

        private void addStageTitle(TitleBar titleBar,
                                   String title) {

            Text titleText = new Text(title);
            super.getChildren().add(titleText);
        }

        public void setWidth(Number width) {
            super.setPrefWidth((double) width);
        }

        public void setHeight(Number height) {
            super.setPrefHeight((double) height);
        }

        @FXML
        public void initialize() {}

        /**
         * For custom Parent
         */
        @FXML
        public void onClose(ActionEvent event) {
            closeButton.fire();
        }

        @FXML
        public void onMinimize(ActionEvent event) {
            minimizeButton.fire();
        }

        @FXML
        public void onFullScreen(ActionEvent event) {
            fullScreenButton.fire();
        }
        /**
         * For custom Parent
         */
    }

    public final class RootSceneController {}
}
