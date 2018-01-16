package com.StellarisDK;

import com.StellarisDK.Tools.FileClasses.Locale;
import com.StellarisDK.Tools.GUI.CompUI;
import com.StellarisDK.Tools.GUI.EventUI;
import com.StellarisDK.Tools.GUI.ModDescUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class guiController extends AnchorPane {
    private Stage stage;
    private File modPath;
    private File path;

    private CompUI compUI;
    private EventUI eventUI;
    private ModDescUI modDescUI = new ModDescUI();

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
            modDescUI.load(modPath.getPath());
//            System.out.println(modDescUI.md.getValue("path"));
//            if (modDescUI.md.getValue("path") == null) {
//                System.out.println(modDescUI.md.getValue("archive"));
//            }
//            System.out.println(modPath.getParent());
//            System.out.println(modPath.getPath());
        }
    }

    @FXML
    protected void compEditor() {
        compUI = new CompUI();
        mainWindow.getChildren().add(compUI);
//        try{
//            parseData(path.getPath());
//        } catch(IOException e){
//
//        }
    }

    @FXML
    protected void eventEditor() {
        eventUI = new EventUI();
        mainWindow.getChildren().add(eventUI);
    }

    @FXML
    protected void localeTest() {
        try {
            System.out.println(new Locale(path.getPath()));
        } catch (IOException e) {
            System.out.println("Locale Failed");
        }
    }

    @FXML
    protected void mdEditor() {
        if (!mainWindow.getChildren().contains(modDescUI))
            mainWindow.getChildren().add(modDescUI);
        else
            modDescUI.load(modPath.getPath());
    }

    @FXML
    protected void export() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mod Descriptors (*.mod)", "*.mod"));
        File mod = fc.showSaveDialog(stage);
        if (mod != null) {
            try {
                FileWriter fw = new FileWriter(mod);
                fw.write(modDescUI.md.toString());
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