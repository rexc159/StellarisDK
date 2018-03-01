package StellarisDK.GUI;

import StellarisDK.FileClasses.GenericData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public abstract class AbstractUI extends Region {

    FXMLLoader loader;

    @FXML
    AnchorPane main;

    @FXML
    Button btn_save;

    TitledPane window = new TitledPane();

    TreeView itemView;

    GenericData obj;

    public void reload(){
        load(obj);
    }

    void init(String fxml) {
        double mouse[] = new double[2];

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            loader = new FXMLLoader(getClass().getResource("FXML/default.fxml"));
            try{
                loader.load();
            }catch (IOException x){
            }
        }
        try{
            btn_save.setOnAction(event -> save());
        }catch (NullPointerException e){
            System.out.println("[ERROR] FXML Error: Missing Save Button (def: btn_save)");
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
    
    void setRoot(Node node) {
        getChildren().add(node);
    }

    public void setTree(TreeView view) {
        itemView = view;
    }

    private void setCloseButton(Button btn) {
        btn.setOnAction(event -> ((Pane) getParent()).getChildren().remove(this));
    }

    public abstract Object save();

    public abstract void load(Object object);

    @Override
    public String toString() {
        return obj.toString();
    }
}