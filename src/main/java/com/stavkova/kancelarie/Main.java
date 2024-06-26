package com.stavkova.kancelarie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/style.fxml"));

        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Stavkova Kancelarie");
        stage.setScene(scene);
        stage.show();
    }

    public static void openNewWindow() throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/new_window.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("New Window");
        newStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
