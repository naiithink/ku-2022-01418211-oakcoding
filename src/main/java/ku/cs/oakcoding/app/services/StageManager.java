/**
 * @file StageManager.java
 * @version 0.1.0-dev
 * 
 * @attention This version of the StageManager was written to fully support JavaFX version 17.0.2.
 * 
 * @todo Apply Optional fields
 * @todo page tree for navigation
 * @todo Multi-stage management
 * @todo Stage binding, the JavaBeans way
 * @todo Multi-screen management
 * @todo Multi-cursor/touch dragging controller
 * 
 * @section References
 * 
 * - Event Processing
 *   https://docs.oracle.com/javafx/2/events/processing.htm
 * 
 * - Draggable Panels Example
 *   https://docs.oracle.com/javase/8/javafx/events-tutorial/draggablepanelsexamplejava.htm
 */

package ku.cs.oakcoding.app.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Rectangle;
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

    /**
     * Arc for custom Stage corner
     */
    private static final double CUSTOM_STAGE_CORNER_ARC;

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
     * Current Font
     * 
     * @note Use during development
     */
    private Font currentFont;

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
     * Icon of the primary Stage
     */
    private Node primaryStageTitleIcon;

    /**
     * Title of the primary Stage
     */
    private String primaryStageTitle;

    /**
     * Title of the primary Stage
     */
    private Pos primaryStageTitlePosition;

    /**
     * Title bar of the primary Stage
     */
    private Node primaryStageTitleBar;

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

    @Inherited
    @Retention (RetentionPolicy.RUNTIME)
    @Target (ElementType.FIELD)
    public @interface Draggable {}

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
    }

    static {
        GET_INSTANCE_METHOD_NAME = "getInstance";
        INDEX_PROPERTY_TOKEN = "\\=>";
        COMMENT_SYMBOL = '#';
        DEFAULT_STAGE_WIDTH = 800.0;
        DEFAULT_STAGE_HEIGHT = 600.0;
        DEFAULT_STAGE_TITLE_BAR_HEIGHT = 30.0;
        CUSTOM_STAGE_STYLE = """
                -fx-background-radius: 11.0;
                """;
        CUSTOM_STAGE_CORNER_ARC = 22.0;
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

        constructDev();

        primaryStage = null;
    }

    private void constructDev() {
        primaryStageTitlePosition = Pos.CENTER_LEFT;
        currentFont = new Font("Noto Sans Display SemiBold", 14);
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

        try (InputStream in = Files.newInputStream(fxmlResourceIndexPath)) {

            Objects.nonNull(in);

            resourceIndexProperties.load(in);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot get to FXML index file");
        }

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
                parent = loader.load();

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

    public Parent getPage(String pageNick) throws PageNotFoundException {
        if (pageTable.containsKey(pageNick) == false) {
            logger.log(Level.SEVERE, "Attempting to add a child to non existing page: " + pageNick);

            throw new PageNotFoundException(pageNick);
        }

        return pageTable.get(pageNick).parent;
    }

    /**
     * Sets the current page
     * 
     * @param       stage                         Stage for page to be set
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

            this.primaryStageTitleBar.toFront();
        }

        logger.log(Level.INFO, "Current page has been set to '" + pageNick + "'");
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
    public void setPage(String pageNick) throws PageNotFoundException {
        setPage(this.primaryStage, pageNick);
    }

    /**
     * Add child element to page
     */
    public void addChildToPage(String pageNick,
                               Node child) throws PageNotFoundException {

        if (pageTable.containsKey(pageNick) == false) {
            logger.log(Level.SEVERE, "Attempting to add a child to non existing page: " + pageNick);

            throw new PageNotFoundException(pageNick);
        }
    }

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
        if (stageStyle != StageStyle.UNDECORATED
            && stageStyle != StageStyle.TRANSPARENT) {

            logger.log(Level.SEVERE, "Attempted to craft Stage with invalid StageStyle: " + stageStyle.name());

            System.exit(1);
        }

        this.primaryStage.initStyle(stageStyle);

        this.primaryStage.setHeight(DEFAULT_STAGE_TITLE_BAR_HEIGHT + this.primaryStageHeight);

        AnchorPane rootNode = new AnchorPane();

        clipChildren(rootNode, CUSTOM_STAGE_CORNER_ARC);

        TitleBar titleBar = new TitleBar(this.primaryStage,
                                         this.primaryStageTitle,
                                         this.primaryStageWidth,
                                         DEFAULT_STAGE_TITLE_BAR_HEIGHT);

        rootNode.widthProperty().addListener((observer, oldValue, newValue) -> {
            titleBar.setWidth(newValue);
        });

        rootNode.widthProperty().addListener((observer, oldValue, newValue) -> {
            this.primaryStageScenePage.setPrefWidth((double) newValue);
        });

        rootNode.heightProperty().addListener((observer, oldValue, newValue) -> {
            this.primaryStageScenePage.setPrefHeight((double) newValue - DEFAULT_STAGE_TITLE_BAR_HEIGHT);
        });

        rootNode.setPrefHeight(DEFAULT_STAGE_TITLE_BAR_HEIGHT + this.primaryStageHeight);

        this.primaryStageTitleBar = titleBar;

        rootNode.getChildren().add(this.primaryStageTitleBar);
        rootNode.getChildren().add(pageTable.get(homePageNick).parent);

        this.primaryStageTitleBar.toFront();

        AnchorPane.setTopAnchor(this.primaryStageTitleBar, 0.0);
        AnchorPane.setBottomAnchor(pageTable.get(homePageNick).parent, 1.0);

        Scene rootScene = new Scene(rootNode);

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

        private final Insets TITLE_BAR_ELEMENT_INSETS = new Insets(0, 10, 0, 10);

        private final String BUTTON_STYLE = """
            -fx-background-radius: 160;
            -fx-pref-width: 14;
            -fx-pref-height: 14;
            -fx-min-width: 1;
            -fx-min-height: 1;
        """;

        private class StageControlButton
                extends Button {

            public enum StageControlButtonType {
                CLOSE_BUTTON,
                MINIMIZE_BUTTON,
                FULL_SCREEN_TOGGLE_BUTTON;
            }

            private final StageControlButtonType BUTTON_TYPE;

            public StageControlButton(StageControlButtonType buttonType) {
                this.BUTTON_TYPE = buttonType;
            }

            public StageControlButton(String text,
                                      StageControlButtonType buttonType) {

                super(text);
                this.BUTTON_TYPE = buttonType;
            }

            public StageControlButton(String text,
                                      StageControlButtonType buttonType,
                                      Node graphic) {

                super(text, graphic);
                this.BUTTON_TYPE = buttonType;
            }
        }

        private final Insets STAGE_CONTROL_BUTTON_PADDING = new Insets(1, 1, 1, 1);

        private final String TITLE_BAR_COLOR = "-fx-background-color: #ffffff";

        private final String HOVERED_BUTTON_STYLE = "-fx-border-color: #000000";

        private final String CLOSE_BUTTON_COLOR = "-fx-background-color: #ed6a5e;";

        private final String MINIMIZE_BUTTON_COLOR = "-fx-background-color: #f5bf4f;";

        private final String FULL_SCREEN_TOGGLE_BUTTON_COLOR = "-fx-background-color: #62c555;";

        private final boolean STAGE_CONTROL_BUTTON_BOX_POSITION_LEFT = false;

        private Stage stage;

        private Robot robot;

        private Button closeButton;

        private Button minimizeButton;

        private Button fullScreenToggleButton;

        private final StageDragContext stageDragContext;

        private static final class StageDragContext {

            public double offSetX;

            public double offSetY;
        }

        private class TitleBarButtonEvent<T extends StageControlButton>
                    extends MouseEvent {

            public TitleBarButtonEvent(EventType<? extends MouseEvent> eventType,
                                       double x,
                                       double y,
                                       double screenX,
                                       double screenY,
                                       MouseButton button,
                                       int clickCount,
                                       boolean shiftDown,
                                       boolean controlDown,
                                       boolean altDown,
                                       boolean metaDown,
                                       boolean primaryButtonDown,
                                       boolean middleButtonDown,
                                       boolean secondaryButtonDown,
                                       boolean synthesized,
                                       boolean popupTrigger,
                                       boolean stillSincePress,
                                       PickResult pickResult) {

                super(eventType,
                      x,
                      y,
                      screenX,
                      screenY,
                      button,
                      clickCount,
                      shiftDown,
                      controlDown,
                      altDown,
                      metaDown,
                      primaryButtonDown,
                      middleButtonDown,
                      secondaryButtonDown,
                      synthesized,
                      popupTrigger,
                      stillSincePress,
                      pickResult);
            }

            public TitleBarButtonEvent(EventType<? extends MouseEvent> eventType,
                                       double x,
                                       double y,
                                       double screenX,
                                       double screenY,
                                       MouseButton button,
                                       int clickCount,
                                       boolean shiftDown,
                                       boolean controlDown,
                                       boolean altDown,
                                       boolean metaDown,
                                       boolean primaryButtonDown,
                                       boolean middleButtonDown,
                                       boolean secondaryButtonDown,
                                       boolean backButtonDown,
                                       boolean forwardButtonDown,
                                       boolean synthesized,
                                       boolean popupTrigger,
                                       boolean stillSincePress,
                                       PickResult pickResult) {

                super(eventType,
                      x,
                      y,
                      screenX,
                      screenY,
                      button,
                      clickCount,
                      shiftDown,
                      controlDown,
                      altDown,
                      metaDown,
                      primaryButtonDown,
                      middleButtonDown,
                      secondaryButtonDown,
                      backButtonDown,
                      forwardButtonDown,
                      synthesized,
                      popupTrigger,
                      stillSincePress,
                      pickResult);
            }

            public TitleBarButtonEvent(Object source,
                                       EventTarget target,
                                       EventType<? extends MouseEvent> eventType,
                                       double x,
                                       double y,
                                       double screenX,
                                       double screenY,
                                       MouseButton button,
                                       int clickCount,
                                       boolean shiftDown,
                                       boolean controlDown,
                                       boolean altDown,
                                       boolean metaDown,
                                       boolean primaryButtonDown,
                                       boolean middleButtonDown,
                                       boolean secondaryButtonDown,
                                       boolean synthesized,
                                       boolean popupTrigger,
                                       boolean stillSincePress,
                                       PickResult pickResult) {

                super(source,
                      target,
                      eventType,
                      x,
                      y,
                      screenX,
                      screenY,
                      button,
                      clickCount,
                      shiftDown,
                      controlDown,
                      altDown,
                      metaDown,
                      primaryButtonDown,
                      middleButtonDown,
                      secondaryButtonDown,
                      synthesized,
                      popupTrigger,
                      stillSincePress,
                      pickResult);
            }

            public TitleBarButtonEvent(Object source,
                                       EventTarget target,
                                       EventType<? extends MouseEvent> eventType,
                                       double x,
                                       double y,
                                       double screenX,
                                       double screenY,
                                       MouseButton button,
                                       int clickCount,
                                       boolean shiftDown,
                                       boolean controlDown,
                                       boolean altDown,
                                       boolean metaDown,
                                       boolean primaryButtonDown,
                                       boolean middleButtonDown,
                                       boolean secondaryButtonDown,
                                       boolean backButtonDown,
                                       boolean forwardButtonDown,
                                       boolean synthesized,
                                       boolean popupTrigger,
                                       boolean stillSincePress,
                                       PickResult pickResult) {

                super(source,
                      target,
                      eventType,
                      x,
                      y,
                      screenX,
                      screenY,
                      button,
                      clickCount,
                      shiftDown,
                      controlDown,
                      altDown,
                      metaDown,
                      primaryButtonDown,
                      middleButtonDown,
                      secondaryButtonDown,
                      backButtonDown,
                      forwardButtonDown,
                      synthesized,
                      popupTrigger,
                      stillSincePress,
                      pickResult);
            }
        }

        private EventHandler<MouseEvent> handleOnCloseRequest = e -> {
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        };

        private EventHandler<MouseEvent> handleOnMinimizeRequest = e -> {
            stage.setIconified(true);
        };

        private EventHandler<MouseEvent> handleOnFullScreenToggleRequest = e -> {
            if (stage.isFullScreen()) {
                stage.setFullScreen(false);
                stage.getScene().getRoot().setStyle(CUSTOM_STAGE_STYLE);
            } else {
                stage.setFullScreen(true);
                stage.getScene().getRoot().setStyle(null);
            }
        };

        @FXML
        private Button customCloseButton;

        @FXML
        private Button customMinimizeButton;

        @FXML
        private Button customFullScreenToggleButton;

        public TitleBar(Stage stage,
                        String stageTitle,
                        double width,
                        double height) {

            this.stage = stage;

            stageDragContext = new StageDragContext();

            super.setPrefWidth(width);
            super.setPrefHeight(height);
            super.setStyle(TITLE_BAR_COLOR);

            super.setFillHeight(true);
            super.setMaxWidth(Double.MAX_VALUE);

            HBox stageGrabBarBox = createStageGrabBarBox(primaryStageTitleIcon, stageTitle);
            HBox stageControlButtonBox = createStageControlButtonBox();

            if (STAGE_CONTROL_BUTTON_BOX_POSITION_LEFT) {
                super.getChildren().add(stageControlButtonBox);
                super.getChildren().add(stageGrabBarBox);

                super.setAlignment(Pos.CENTER_LEFT);
            } else {
                super.getChildren().add(stageGrabBarBox);
                super.getChildren().add(stageControlButtonBox);

                super.setAlignment(Pos.CENTER_RIGHT);
            }

            HBox.setHgrow(stageGrabBarBox, Priority.ALWAYS);
        }

        private HBox createStageControlButtonBox() {
            HBox stageControlButtonBox = new HBox();

            closeButton = new Button();
            minimizeButton = new Button();
            fullScreenToggleButton = new Button();

            closeButton.setPadding(STAGE_CONTROL_BUTTON_PADDING);
            minimizeButton.setPadding(STAGE_CONTROL_BUTTON_PADDING);
            fullScreenToggleButton.setPadding(STAGE_CONTROL_BUTTON_PADDING);

            closeButton.setStyle(BUTTON_STYLE + CLOSE_BUTTON_COLOR);
            closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnCloseRequest);

            minimizeButton.setStyle(BUTTON_STYLE + MINIMIZE_BUTTON_COLOR);
            minimizeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnMinimizeRequest);

            fullScreenToggleButton.setStyle(BUTTON_STYLE + FULL_SCREEN_TOGGLE_BUTTON_COLOR);
            fullScreenToggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, handleOnFullScreenToggleRequest);

            stageControlButtonBox.setPadding(TITLE_BAR_ELEMENT_INSETS);

            stageControlButtonBox.setAlignment(Pos.CENTER);

            // wheater to use custom or default
            stageControlButtonBox.getChildren().add(closeButton);
            stageControlButtonBox.getChildren().add(minimizeButton);
            stageControlButtonBox.getChildren().add(fullScreenToggleButton);

            stageControlButtonBox.setSpacing(BUTTON_PADDING);

            return stageControlButtonBox;
        }

        private HBox createStageGrabBarBox(Node stageIcon,
                                           String stageTitle) {

            HBox stageGrabBarBox = new HBox();

            Node stageIconNode = createStageTitleIconNode(stageIcon);
            Text stageTitleText = createStageTitleText(stageTitle);

            if (stageIconNode != null) {
                stageGrabBarBox.getChildren().add(stageIconNode);
            }

            stageGrabBarBox.getChildren().add(stageTitleText);
            stageGrabBarBox.setAlignment(Pos.CENTER_LEFT);
            stageGrabBarBox.setPadding(TITLE_BAR_ELEMENT_INSETS);

            HBox.setHgrow(stageTitleText, Priority.ALWAYS);

            stageGrabBarBox.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    stageDragContext.offSetX = primaryStage.getX() - event.getScreenX();
                    stageDragContext.offSetY = primaryStage.getY() - event.getScreenY();
                }
            });

            stageGrabBarBox.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() + stageDragContext.offSetX);
                    primaryStage.setY(event.getScreenY() + stageDragContext.offSetY);
                }
            });

            return stageGrabBarBox;
        }

        private Text createStageTitleText(String stageTitle) {
            Text titleText = new Text(stageTitle);

            titleText.setFont(currentFont);

            return titleText;
        }

        private Node createStageTitleIconNode(Node icon) {
            return icon;
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
            customCloseButton.fire();
        }

        @FXML
        public void onMinimize(ActionEvent event) {
            customMinimizeButton.fire();
        }

        @FXML
        public void onFullScreenToggle(ActionEvent event) {
            customFullScreenToggleButton.fire();
        }
        /**
         * For custom Parent
         */
    }

    /**
     * @section Draggability
     */

    /**
     * Property holding drag mode status
     */
    private SimpleBooleanProperty dragModeActiveProperty = new SimpleBooleanProperty(this.primaryStage, "dragModeActive", true);

    /**
     * Mouse dragging context
     */
    private static final class DragContext {

        public double mouseAnchorX;

        public double mouseAnchorY;

        public double initialTranslateX;

        public double initialTranslateY;
    }

    /**
     * Make a Node draggable
     */
    public <T extends Node> Node makeDraggable(final T node) {
        final DragContext dragContext = new DragContext();
        final Group wrapGroup = new Group(node);

        wrapGroup.maxWidth(Double.MAX_VALUE);

        wrapGroup.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent mouseEvent) {
                if (dragModeActiveProperty.get()) {
                    mouseEvent.consume();
                }
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent mouseEvent) {
                if (dragModeActiveProperty.get()) {
                    dragContext.mouseAnchorX = mouseEvent.getX();
                    dragContext.mouseAnchorY = mouseEvent.getY();

                    dragContext.initialTranslateX = node.getTranslateX();
                    dragContext.initialTranslateY = node.getTranslateY();
                }
            }
        });

        wrapGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent mouseEvent) {
                if (dragModeActiveProperty.get()) {
                    node.setTranslateX(dragContext.initialTranslateX
                                       + mouseEvent.getX()
                                       - dragContext.mouseAnchorX);
                    node.setTranslateY(dragContext.initialTranslateY
                                       + mouseEvent.getY()
                                       - dragContext.mouseAnchorY);
                }
            }
        });

        return wrapGroup;
    }

    private static void clipChildren(Region region,
                                     double arc) {

        final Rectangle rootRegion = new Rectangle();

        rootRegion.setArcWidth(arc);
        rootRegion.setArcHeight(arc);
        region.setClip(rootRegion);

        region.layoutBoundsProperty().addListener((observer, oldValue, newValue) -> {
            rootRegion.setWidth(newValue.getWidth());
            rootRegion.setHeight(newValue.getHeight());
        });
    }
}
