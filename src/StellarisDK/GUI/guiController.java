package StellarisDK.GUI;

import StellarisDK.DataLoc;
import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.DataParser;
import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.ModDescriptor;
import com.sun.javafx.scene.control.skin.LabeledText;
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

import static StellarisDK.FileClasses.DataParser.parseData;

public class guiController extends AnchorPane {
    private Stage stage;
    private File modPath;
    private String mainLoadPath;
    private File path;

    private CompUI compUI = new CompUI();
    private CompSetUI compSetUI = new CompSetUI();
    private EventUI eventUI = new EventUI();
    private ModDescUI modDescUI = new ModDescUI();

    private ModDescriptor mainMd;

    private GenericData test;

    @FXML
    private TreeView itemView;

    @FXML
    private AnchorPane mainWindow;

    public guiController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/guiFX.fxml"));
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void openFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        fc.setTitle("Open File");
        path = fc.showOpenDialog(stage);
        if (path != null) {
            try{
                test = parseData(path.getPath());
            }catch(IOException e){
                System.out.println("Failed");
            }
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
            itemView.getRoot().setExpanded(true);
            itemView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if (event.getClickCount() >= 2) {
                    System.out.println("Starting Editor");
                    Node node = event.getPickResult().getIntersectedNode();
                    if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                        Object temp;
                        if ((node instanceof LabeledText)) {
                            temp = ((TreeCell) node.getParent()).getTreeItem().getValue();
                        } else
                            temp = ((TreeCell) node).getTreeItem().getValue();
                        if (temp instanceof ModDescriptor) {
                            open(temp, modDescUI);
                        } else if(temp instanceof Component)
                            open(temp, compUI);
                        else
                            open(temp, compSetUI);
                    }
                }
            });
            if (mainMd.getValue("path") != null) {
                mainLoadPath = modPath.getParentFile().getParent() + "\\" + (mainMd.getValue("path").toString().replaceAll("/", "\\\\"));
                loadMod();
            }
            modDescUI.load(modPath.getPath());
        }
    }

    protected void loadMod() {
//        for(File folder: new File(mainLoadPath).listFiles()){
//            try{
//                for (File file : new File(folder.getPath()).listFiles()) {
//                    itemView.getRoot().getChildren().addAll(DataParser.parse(file));
//                }
//            } catch (NullPointerException e) {
//                System.out.println("Empty/Missing Folder.");
//            } catch (IOException e){
//                System.out.println("Malformed Input");
//            }
//        }
        try {
            for (File file : new File(mainLoadPath + "\\" + DataLoc.component_sets).listFiles()) {
                itemView.getRoot().getChildren().addAll(DataParser.parseSet(file));
            }
            for (File file : new File(mainLoadPath + "\\" + DataLoc.component_templates).listFiles()) {
                itemView.getRoot().getChildren().addAll(DataParser.parseCompUtil(file));
            }
        } catch (NullPointerException e) {
            System.out.println("Empty/Missing Folder.");
        } catch (IOException e){
            System.out.println("Malformed Input");
        }
    }

    protected void open(Object obj, AbstractUI ui){
        if (!mainWindow.getChildren().contains(ui))
            mainWindow.getChildren().add(ui);
        ui.load(obj);
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
    protected void exportTest() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File (*.txt)", "*.txt"));
        File temp = fc.showSaveDialog(stage);
        if (temp != null) {
            try {
                FileWriter fw = new FileWriter(temp);
                fw.write(test.export());
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