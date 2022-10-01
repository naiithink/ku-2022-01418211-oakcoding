module oakcoding.app {
    requires java.desktop;
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    /* 
     * javafx.graphics is transitively required by javafx.controls
     * 
     * - ku.cs.oakcoding.ProjectApplication.launch()
     */
    exports ku.cs.oakcoding                     to  javafx.graphics;

    /* 
     * ku.cs.oakcoding.ProjectController
     */
    opens   ku.cs.oakcoding                     to  javafx.fxml;

    /* 
     * ku.cs.oakcoding.app.controllers.*
     */
    opens   ku.cs.oakcoding.app.controllers     to  javafx.fxml;

    /* 
     * com.github.saacsos.fxrouter
     */
    opens   com.github.saacsos.fxrouter         to  javafx.fxml;
    opens ku.cs.oakcoding.app.services to javafx.fxml;
    opens ku.cs.oakcoding.app.services.data_source.CSV to javafx.fxml;
}
