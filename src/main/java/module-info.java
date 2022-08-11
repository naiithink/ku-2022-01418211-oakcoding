module cs.ku {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens ku.cs to javafx.fxml;
    exports ku.cs;

}
