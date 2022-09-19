module oakcoding.app {
    requires java.logging;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // ku.cs.oakcoding.ProjectApplication.launch()
    exports ku.cs.oakcoding to javafx.graphics;

    // ku.cs.oakcoding.ProjectController
    opens ku.cs.oakcoding to javafx.fxml;

    // ku.cs.oakcoding.app.controllers.*
    opens ku.cs.oakcoding.app.controllers to javafx.fxml;

    // com.github.saacsos.fxrouter
    opens com.github.saacsos.fxrouter to javafx.fxml;
}
