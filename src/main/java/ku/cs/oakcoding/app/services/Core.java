package ku.cs.oakcoding.app.services;

import com.github.saacsos.fxrouter.Router;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Core application monitor
 *
 * Initialize objects, parse and validate data from resource repository.
 */
public final class Core {

    private static Core instance;

    private static final int THREAD_LIST_INDEX_FRONTEND;

    private static final int THREAD_LIST_INDEX_BACKEND;

    private static final int NUMBER_OF_THREADS;

    private static boolean activeStatus;

    private ArrayList<Long> threadIdList;

    static {
        THREAD_LIST_INDEX_FRONTEND = 0;
        THREAD_LIST_INDEX_BACKEND = THREAD_LIST_INDEX_FRONTEND + 1;
        NUMBER_OF_THREADS = THREAD_LIST_INDEX_BACKEND + 1;
        activeStatus = false;
    }

    private class Frontend
            extends Application
            implements Runnable {

        @Override
        public void start(Stage stage) throws IOException {
            Router.bind(this, stage, "OakCoding", 300, 500);
            configRoute();
            Router.goTo("project");
        }

        /**
         * @todo apply
         */
        private static void configRoute() {
            String packageStr = "views/";
            Router.when("project", packageStr + "project.fxml", 300, 500);
            Router.when("test", packageStr + "test.fxml", 600, 400);
        }

        public void run() {
            launch();
        }
    }

    private class Backend extends Thread {

    }

    /**
     * No direct call to create Core instance
     */
    private Core() {
        threadIdList = new ArrayList<>(NUMBER_OF_THREADS);
    }

    /**
     * Core MUST not have more than one instance.
     * @return the only instance of Core
     */
    public static Core getInstance() {
        if (instance == null) {
            synchronized (Core.class) {
                if (instance == null) {
                    instance = new Core();
                }
            }
        }

        return instance;
    }

    public boolean isActive() {
        return activeStatus;
    }

    /**
     * Start the application.
     * @param args argument from application entry @ref ku.cs.oakcoding.ProjectApplication.main()
     *             same as args of @ref ku.cs.oakcoding.ProjectApplication.main.launch()
     * @warning This method MUST be called before ku.cs.oakcoding.ProjectApplication.main.launch()
     */
    public void start(String[] args) {
        Frontend.launch(args);

        Thread frontend = new Thread(new Core.Frontend());

        threadIdList.set(THREAD_LIST_INDEX_FRONTEND, frontend.getId());

        activeStatus = true;
    }

    /**
     * Terminate the application gracefully.
     * @return final exit status of the application, evaluated from Frontend and Backend threads exit status
     */
    public void stop(int exitStatus) {
        int finalExitStatus = -1;

        // >>> to be implemented
        finalExitStatus = 1;
        // <<<

        System.exit(finalExitStatus);
    }

    public static void main(String[] args) {
        Core coreInstance = Core.getInstance();

        try {
            if (coreInstance.isActive() == true) {
                throw new CoreInstanceAlreadyActiveException("Cannot start application, core instance is already active");
            } else {
                coreInstance.start(args);
            }
        } catch (CoreInstanceAlreadyActiveException e) {
            System.err.println(e.getMessage());
            coreInstance.stop(1);
        }
    }
}
