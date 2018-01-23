package com.StellarisDK;

import com.StellarisDK.Tools.FileClasses.ModDescriptor;
import com.StellarisDK.Tools.GUI.CompUI;
import com.StellarisDK.Tools.GUI.EventUI;
import com.StellarisDK.Tools.GUI.ModDescUI;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class guiController extends AnchorPane {
    private Stage stage;
    private File modPath;
    private File path;

    private CompUI compUI = new CompUI();
    private EventUI eventUI = new EventUI();
    private ModDescUI modDescUI = new ModDescUI();

    private ModDescriptor mainMd;

    @FXML
    private TreeView itemView;

    @FXML
    private AnchorPane mainWindow;

    public guiController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("guiFX.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            compUI.setTree(itemView);
            eventUI.setTree(itemView);
            modDescUI.setTree(itemView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void openFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        fc.setTitle("Open File");
        path = fc.showOpenDialog(stage);
        if (path != null) {
            System.out.println(path.getPath());
        }
    }

    @FXML
    protected void openLocale() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Locale files (*.yml)", "*.yml"));
        fc.setTitle("Open File");
        path = fc.showOpenDialog(stage);
        if (path != null) {
            System.out.println(path.getPath());
        }
    }

    @FXML
    protected void openMod() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mod Descriptors (*.mod)", "*.mod"));
        fc.setTitle("Select Mod Descriptor");
        modPath = fc.showOpenDialog(stage);
        if (modPath != null) {
            mainMd = new ModDescriptor(modPath.getPath());
            itemView.setRoot(new TreeItem(mainMd));
            itemView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getClickCount() >= 2){
                        Node node = event.getPickResult().getIntersectedNode();
                        if(node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)){
                            openMD(((TreeCell)node).getTreeItem().getValue());
                        }
                    }
                }
            });
            modDescUI.load(modPath.getPath());
        }
    }

    protected void openMD(Object md) {
        if (!mainWindow.getChildren().contains(modDescUI))
            mainWindow.getChildren().add(modDescUI);
        modDescUI.load(md);
    }

    @FXML
    protected void export() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mod Descriptors (*.mod)", "*.mod"));
        File mod = fc.showSaveDialog(stage);
        if (mod != null) {
            try {
                FileWriter fw = new FileWriter(mod);
                fw.write(mainMd.export());
                fw.close();
            } catch (IOException e) {
                System.out.println("Export Failed.");
            }
        }
    }

    @FXML
    protected void exit() {
        System.exit(0);
    }
}