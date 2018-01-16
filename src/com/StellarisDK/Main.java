package com.StellarisDK;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        guiController controller = new guiController();
        primaryStage.setTitle("Stellaris Development Kit");

        Scene scene = new Scene(controller);
        controller.setStage(primaryStage);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
