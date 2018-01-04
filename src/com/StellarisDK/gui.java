package com.StellarisDK;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class gui extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        guiController controller = new guiController();
        primaryStage.setTitle("Test");

        Scene scene = new Scene(controller);
        controller.setStage(primaryStage);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
