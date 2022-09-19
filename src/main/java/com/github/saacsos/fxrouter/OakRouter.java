package com.github.saacsos.fxrouter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class OakRouter extends FXRouter {

    private static double x;

    private static double y;

    private static int popWindowPerTime = 0;
    private static int appearWindow = 0;

    public OakRouter() {
    }

    public static void goTo(String routeLabel) throws IOException {
        RouteScene route = routes.get(routeLabel);
        loadNewRoute(route);
    }

    public static void goTo(String routeLabel, Object data) throws IOException {
        RouteScene route = routes.get(routeLabel);
        route.data = data;
        loadNewRoute(route);
    }

    public static void popup(String fxml) throws IOException {
        RouteScene route = routes.get(fxml);
        if (popWindowPerTime == 0) {
            loadPopUp(route);
            popWindowPerTime++;
        }

    }

    public static void popup(String fxml, Object data) throws IOException {
        RouteScene route = routes.get(fxml);
        if (popWindowPerTime == 0) {
            route.data = data;
            loadPopUp(route);
            popWindowPerTime++;
        }
    }

    public static void closePopup() {
        popWindowPerTime = 0;
    }

    public static void loadNewRoute(RouteScene route) throws IOException {
        try {
            currentRoute = route;
            String scenePath = "/" + route.scenePath;
            Parent resource = (Parent) FXMLLoader.load((new Object() {
            }).getClass().getResource(scenePath));
            if (appearWindow == 0) {
                window.initStyle(StageStyle.UNDECORATED);
                window.initStyle(StageStyle.TRANSPARENT);
                appearWindow++;
            }
            window.setTitle(route.windowTitle);
            Scene scene = new Scene(resource,route.sceneWidth,route.sceneHeight);
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();

            resource.setOnMousePressed(mouseEvent ->{
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });

            resource.setOnMouseDragged(mouseEvent ->{
                window.setX(mouseEvent.getScreenX() - x);
                window.setY(mouseEvent.getScreenY() - y);
            });

            routeAnimation(resource);
        } catch (IllegalStateException e) {
            System.err.println("IllegalStateException: " + e.getMessage());
        }
    }


    public static void loadPopUp(RouteScene route) throws IOException {
        try {
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

            resource.setOnMousePressed(mouseEvent ->{
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });
            resource.setOnMouseDragged(mouseEvent ->{
                stage.setX(mouseEvent.getScreenX() - x);
                stage.setY(mouseEvent.getScreenY() - y);
            });
        } catch (IllegalStateException e) {
            System.err.println("IllegalStateException: " + e.getMessage());
        }
    }
}
