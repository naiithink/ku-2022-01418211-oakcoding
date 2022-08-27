module oakcoding.app {
    requires javafx.controls;
    requires javafx.fxml;

    // ku.cs.oakcoding.ProjectApplication.launch()
    exports ku.cs.oakcoding to javafx.graphics;

    // ku.cs.oakcoding.ProjectController
    opens ku.cs.oakcoding to javafx.fxml;

    // ku.cs.oakcoding.app.controllers.*
    opens ku.cs.oakcoding.app.controllers to javafx.fxml;

    // com.github.saacsos.fxrouter
    opens com.github.saacsos.fxrouter to javafx.fxml;
}
