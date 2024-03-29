module oakcoding.app {
    requires java.desktop;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;

    /* 
     * javafx.graphics is transitively required by javafx.controls
     * 
     * - ku.cs.oakcoding.ProjectApplication.launch()
     */
    exports ku.cs.oakcoding                         to  javafx.graphics;

    /* 
     * ku.cs.oakcoding.ProjectController
     */
    opens ku.cs.oakcoding                           to  javafx.fxml;

    /* 
     * ku.cs.oakcoding.app.controllers.*
     */
    opens ku.cs.oakcoding.app.controllers           to  javafx.fxml;

    /* 
     * ku.cs.oakcoding.app.services.stages.StageManager -- FXML
     */
    opens ku.cs.oakcoding.app.services              to javafx.fxml;

    /**
     * ku.cs.oakcoding.app.models.* -- TableView<S>
     */
    opens ku.cs.oakcoding.app.models.complaints     to javafx.base;
    opens ku.cs.oakcoding.app.models.org            to javafx.base;
    opens ku.cs.oakcoding.app.models.reports        to javafx.base;
    opens ku.cs.oakcoding.app.models.users          to javafx.base;
    opens ku.cs.oakcoding.app.services.filter to javafx.base;
}
