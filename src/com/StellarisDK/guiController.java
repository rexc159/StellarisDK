package com.StellarisDK;

import com.StellarisDK.tools.fileClasses.ModDescriptor;
import com.StellarisDK.tools.gui.CompUI;
import com.StellarisDK.tools.gui.EventUI;
import com.StellarisDK.tools.gui.ModDescUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import static com.StellarisDK.tools.fileClasses.dataParser.parseCompUtil;

public class guiController extends AnchorPane {
    private Stage stage;
    private File modPath;
    private File path;

    private CompUI compUI;
    private EventUI eventUI;
    private ModDescUI modDescUI = new ModDescUI();

    @FXML private TreeView objectList;
    @FXML private TextField directory;

    @FXML private AnchorPane mainWindow;

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
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)","*.txt"));
        fc.setTitle("Open File");
        path = fc.showOpenDialog(stage);
        if (path != null){
            System.out.println(path.getPath());
        }
    }

    @FXML
    protected void compEditor(){
        compUI = new CompUI();
        mainWindow.getChildren().add(compUI);
    }

    @FXML
    protected void eventEditor(){
        eventUI = new EventUI();
        mainWindow.getChildren().add(eventUI);
    }

    @FXML
    protected void mdEditor(){
        mainWindow.getChildren().add(modDescUI);
    }

    @FXML
    protected void openMod() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mod Descriptors (*.mod)","*.mod"));
        fc.setTitle("Select Mod Descriptor");
        modPath = fc.showOpenDialog(stage);
        if (modPath != null){
            modDescUI.load(modPath.getPath());
            System.out.println(modPath.getPath());
            ModDescriptor md = new ModDescriptor();
            try{
                md.load(modPath.getPath());
                md.output();
            } catch(IOException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    @FXML
    protected void exit() {
        System.exit(0);
    }
}