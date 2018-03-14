package StellarisDK.GUI;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataCell;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.FileClasses.Helper.DataMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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

    private static TreeItem copiedItem;

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
        obj.lockEntries();
        treeView.setEditable(true);
        treeView.setCellFactory(param -> new DataCell());
        treeView.setOnKeyPressed(event -> {
            TreeItem selected = (TreeItem)treeView.getSelectionModel().getSelectedItem();
            if (new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN).match(event)) {
                if(     selected.getParent() != null &&
                        selected.getValue() instanceof DataEntry &&
                        !((DataEntry) selected.getValue()).isRequired()
                        ){
                    System.out.println("CTRL+X");
                    copiedItem = selected;
                    selected.getParent().getChildren().remove(selected);
                }
            } else if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(event)) {
                if(selected.getParent() != null){
                    System.out.println("CTRL+C");
                    copiedItem = DataCell.clone(selected);
                }else{
                    copiedItem = null;
                }
            } else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(event)) {
                if (copiedItem != null){
                    System.out.println("CTRL+V");
                    if(selected.getParent() != null || ((DataEntry) selected.getValue()).isSingleEntry())
                        selected.getParent().getChildren().add(DataCell.clone(copiedItem));
                    else
                        selected.getChildren().add(DataCell.clone(copiedItem));
                }
            } else if (new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN).match(event)) {
                System.out.println("CTRL+N");
                if (!((DataEntry) selected.getValue()).isSingleEntry()) {
                    selected.getChildren().add(new TreeItem<>("Click_to_Edit"));
                }else {
                    selected.getParent().getChildren().add(new TreeItem<>("Click_to_Edit"));
                }
            } else if (event.getCode() == KeyCode.DELETE) {
                if (selected.getParent() != null || !((DataEntry) selected.getValue()).isRequired())
                    selected.getParent().getChildren().remove(selected);
            }
            event.consume();
        });
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