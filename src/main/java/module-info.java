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
    opens ku.cs.oakcoding.app.services to javafx.fxml;
}
