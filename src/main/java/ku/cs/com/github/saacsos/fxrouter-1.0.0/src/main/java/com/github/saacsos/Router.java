package com.github.saacsos;
import com.github.saacsos.FXRouter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Router extends FXRouter {
    private static double x;
    private static double y;



    public Router(){
    }

    public static void start(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRouteINIT(route);
    }
    public static void goTo(String routeLabel) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        loadNewRouteNOINIT(route);
    }

    public static void goTo(String routeLabel, Object data) throws IOException {
        RouteScene route = (RouteScene)routes.get(routeLabel);
        route.data = data;
        loadNewRouteNOINIT(route);
    }

    public static void popup(String fxml) throws IOException {
        RouteScene route = (RouteScene)routes.get(fxml);
        loadPopUp(route);

    }

    protected static void loadNewRouteINIT(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent) FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.initStyle(StageStyle.UNDECORATED);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(route.windowTitle);
        Scene scene = new Scene(resource,route.sceneWidth,route.sceneHeight);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();

        resource.setOnMousePressed(mouseEvent ->{ // On Mouse Press get x , y on pressed on the scene
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        resource.setOnMouseDragged(mouseEvent ->{ // On Dragged set x , y to where the cursor on the screen(while press)
            window.setX(mouseEvent.getScreenX() - x);
            window.setY(mouseEvent.getScreenY() - y);
        });


        routeAnimation(resource);
    }

    protected static void loadNewRouteNOINIT(RouteScene route) throws IOException {
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Parent resource = (Parent) FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));
        window.setTitle(route.windowTitle);
        Scene scene = new Scene(resource,route.sceneWidth,route.sceneHeight);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.show();

        resource.setOnMousePressed(mouseEvent ->{ // On Mouse Press get x , y on pressed on the scene
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        resource.setOnMouseDragged(mouseEvent ->{ // On Dragged set x , y to where the cursor on the screen(while press)
            window.setX(mouseEvent.getScreenX() - x);
            window.setY(mouseEvent.getScreenY() - y);
        });


        routeAnimation(resource);
    }

    public static void loadPopUp(RouteScene route) throws IOException{
        currentRoute = route;
        String scenePath = "/" + route.scenePath;
        Stage stage = new Stage();
        Parent resource = (Parent) FXMLLoader.load((new Object() {
        }).getClass().getResource(scenePath));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle(route.windowTitle);
        Scene scene = new Scene(resource,route.sceneWidth,route.sceneHeight);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        resource.setOnMousePressed(mouseEvent ->{ // On Mouse Press get x , y on pressed on the scene
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        resource.setOnMouseDragged(mouseEvent ->{ // On Dragged set x , y to where the cursor on the screen(while press)
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

    }



}
