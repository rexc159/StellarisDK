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

public abstract class AbstractUI extends Region {

    @FXML
    AnchorPane main;

    TitledPane window = new TitledPane();

    TreeView itemView;

    GenericData obj;

    void init(String fxml) {
        double mouse[] = new double[2];

        try {
            main = FXMLLoader.load(getClass().getResource(fxml));
            ((Button) main.getChildren().get(1)).setOnAction(event -> save());
        } catch (Exception e) {
            System.out.println("Editor .FXML not found, using default.");
            try {
                main = FXMLLoader.load(getClass().getResource("FXML/default.fxml"));
            } catch (Exception x) {
                System.out.println("Something really went wrong.");
                return;
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