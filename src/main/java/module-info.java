module ku.cs.oakcoding {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens ku.cs.oakcoding to javafx.controls, javafx.graphics, javafx.fxml;

    // exports ku.cs.oakcoding to javafx.fxml;

    // opens ku.cs.oakcoding.app.controllers to javafx.fxml;
    // exports ku.cs.oakcoding.app.controllers to javafx.fxml;
}
