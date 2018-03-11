package StellarisDK.GUI;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataCell;
import StellarisDK.FileClasses.Helper.DataMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.io.IOException;

public abstract class AbstractUI extends Region {

    AnchorPane root;

    @FXML
    AnchorPane main;

    @FXML
    Button btn_save;

    @FXML
    TreeView treeView;

    TitledPane window = new TitledPane();

    TreeView itemView;

    GenericData obj;

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    void init(String fxml) {
        double mouse[] = new double[2];

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        loader.setController(this);
        try {
            loader.load();
        } catch (Exception e) {
            System.out.println("[ERROR] FXML Missing: " + fxml + ", switching to default.");
            loader = new FXMLLoader(getClass().getResource("FXML/default.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException x) {
            }
        }
        try {
            btn_save.setOnAction(event -> save());
        } catch (NullPointerException e) {
            System.out.println("[ERROR] FXML Error: Missing Save Button (def: btn_save), Editor under construction?");
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

    public void load() {
        treeView.setEditable(true);
        treeView.setCellFactory(param -> new DataCell());
        if(obj.getSize() != 0){
            treeView.setRoot(obj.toTreeItem());
        }else{
            treeView.setRoot(obj.getRequiredTreeSet());
        }
    }

    public Object save() {
        obj.setType(treeView.getRoot().getValue().toString());
        obj.setData((DataMap)obj.load(unparse(treeView.getRoot())));
        System.out.println(obj.export());
        itemView.refresh();
        return obj;
    }

    public String unparse(TreeItem root) {
        String tabs = "\r\n";
        for (int k = 0; k <= treeView.getTreeItemLevel(root); k++) {
            tabs += "\t";
        }
        String out = "{";
        for (Object item : root.getChildren()) {
            if (((TreeItem) item).isLeaf()) {
                if (((TreeItem) item).getValue().toString().contains("#tabs"))
                    out += ((TreeItem) item).getValue().toString().replaceAll("#tabs", tabs);
                else
                    out += tabs + ((TreeItem) item).getValue().toString();
            } else {
                out += tabs + ((TreeItem) item).getValue() + " = " + unparse(((TreeItem) item));
            }
        }
        return out + tabs.replaceFirst("\t", "") + "}";
    }

    @Override
    public String toString() {
        return obj.toString();
    }
}