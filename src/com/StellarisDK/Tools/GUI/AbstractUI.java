package com.StellarisDK.Tools.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public abstract class AbstractUI extends Region {

    @FXML
    protected AnchorPane main;

    protected TitledPane window = new TitledPane();

    public void init(String fxml) {
        double mouse[] = new double[2];

        try {
            main = FXMLLoader.load(getClass().getResource(fxml));
            ((Button) main.getChildren().get(1)).setOnAction(event -> save());
        } catch (IOException e) {
            System.out.println("Editor FXML not found, using default.");
            try {
                main = FXMLLoader.load(getClass().getResource("./FXML/default.fxml"));
            } catch (IOException x) {
                System.out.println("Something really went wrong.");
            }
        }

        Button close = new Button("\u2715");
        window.setContent(main);
        window.setCollapsible(false);
        window.setGraphic(close);
        window.setAlignment(Pos.BASELINE_RIGHT);
        window.setContentDisplay(ContentDisplay.RIGHT);
        this.setCloseButton(close);
        this.setRoot(window);

        // Draggable Window Block
        window.setOnMousePressed(event -> {
            mouse[0] = getLayoutX() - event.getScreenX();
            mouse[1] = getLayoutY() - event.getScreenY();
            toFront();
        });

        window.setOnMouseDragged(event -> {
            setLayoutX(Math.max(event.getScreenX() + mouse[0], 0));
            setLayoutY(Math.max(event.getScreenY() + mouse[1], 0));
        });
        // End Block

        this.setOnMouseClicked(event -> toFront());
    }

    public abstract Object save();

    public abstract void load(Object object);


//    public void init() {
//        double mouse[] = new double[2];
//        Button close = new Button("x");
//        BorderPane titleBar = new BorderPane();
//        window.setStyle("-fx-background-color: white;");
//
//        titleBar.setOnMousePressed(event -> {
//            mouse[0] = getLayoutX() - event.getScreenX();
//            mouse[1] = getLayoutY() - event.getScreenY();
//            toFront();
//        });
//
//        titleBar.setOnMouseDragged(event -> {
//            setLayoutX(Math.max(event.getScreenX() + mouse[0], 0));
//            setLayoutY(Math.max(event.getScreenY() + mouse[1], 0));
//        });
//
//        this.setOnMouseClicked(event -> {
//            toFront();
//        });
//
//        titleBar.setStyle("-fx-background-color: grey; -fx-padding: 3");
//        titleBar.setLeft(title);
//        titleBar.setRight(close);
//        window.setTop(titleBar);
//        this.setCloseButton(close);
//        this.setRoot(window);
//    }

    public void setRoot(Node node) {
        getChildren().add(node);
    }

    public void setCloseButton(Button btn) {
        btn.setOnAction(event -> ((Pane) getParent()).getChildren().remove(this));
    }
}