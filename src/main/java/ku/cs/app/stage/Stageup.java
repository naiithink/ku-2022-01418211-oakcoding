package ku.cs.app.stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Stageup {
    private String fxmlFile;
    private Class classes;
    public Stageup(String fxmlFile, Class classes){
        this.fxmlFile = fxmlFile;
        this.classes = classes;
    }
    public void showStage(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(this.classes.getResource(fxmlFile));
        try {
            Scene scene = new Scene(fxmlLoader.load(),800,600);
            stage.setScene(scene);
            stage.show();

        }
        catch(IOException e) {
            System.out.println("no page");
        }

    }
}
