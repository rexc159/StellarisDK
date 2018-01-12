package com.StellarisDK.tools.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public abstract class AbstractUI extends Region {

    protected BorderPane window = new BorderPane();
    protected Label title = new Label();

    protected String key_labels[];

    public void test_init(){
        try{
            window = FXMLLoader.load(getClass().getResource("./FXML/mdFX.fxml"));
            this.setRoot(window);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void init() {
        double mouse[] = new double[2];
        Button close = new Button("x");
        BorderPane titleBar = new BorderPane();
        window.setStyle("-fx-background-color: white;");

        titleBar.setOnMousePressed(event -> {
            mouse[0] = getLayoutX() - event.getScreenX();
            mouse[1] = getLayoutY() - event.getScreenY();
            toFront();
        });

        titleBar.setOnMouseDragged(event -> {
            setLayoutX(Math.max(event.getScreenX() + mouse[0], 0));
            setLayoutY(Math.max(event.getScreenY() + mouse[1], 0));
        });

        this.setOnMouseClicked(event -> {
            toFront();
        });

        titleBar.setStyle("-fx-background-color: grey; -fx-padding: 3");
        titleBar.setLeft(title);
        titleBar.setRight(close);
        window.setTop(titleBar);
        this.setCloseButton(close);
        this.setRoot(window);
    }


    public void setRoot(Node node) {
        getChildren().add(node);
    }

    public void setCloseButton(Button btn) {
        btn.setOnAction(event -> ((Pane) getParent()).getChildren().remove(this));
    }
}