package org.example.application.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class WindowLoader extends Application {

    private static Scene scene;
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader = fxmlLoader.load(WindowLoader.class.getResource(fxml + ".fxml").openStream());
        controller = fxmlLoader.getController();
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Object getController() {
        return controller;
    }
}