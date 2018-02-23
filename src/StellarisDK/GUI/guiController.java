package StellarisDK.GUI;

import StellarisDK.DataLoc;
import StellarisDK.FileClasses.DataParser;
import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Locale;
import StellarisDK.FileClasses.ModDescriptor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Can probably cut some RAM cost by using Cell Factories
        itemView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() >= 2) {
                Node node = event.getPickResult().getIntersectedNode();
                if ((node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    if (((TreeCell) node).getTreeItem().isLeaf()) {
                        if (((TreeCell) node).getTreeItem().getValue() instanceof GenericData) {
                            System.out.println("Starting Editor");
                            open((GenericData) ((TreeCell) node).getTreeItem().getValue());
                        }
                    }
                }
            }
        });
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
            try {
                test = parseData(path.getPath());
            } catch (IOException e) {
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
    protected void createNewMD() {
        mainMd = new ModDescriptor();
        itemView.setRoot(new TreeItem<>(mainMd));
        itemView.getRoot().getChildren().add(new TreeItem<>(mainMd));
        itemView.getRoot().setExpanded(true);
        open(mainMd);
    }

    @FXML
    protected void openMod() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mod Descriptors (*.mod)", "*.mod"));
        fc.setTitle("Select Mod Descriptor");
        modPath = fc.showOpenDialog(stage);
        if (modPath != null) {
            mainMd = new ModDescriptor(modPath.getPath());
            itemView.setRoot(new TreeItem<>(mainMd));
            itemView.getRoot().getChildren().add(new TreeItem(mainMd));
            itemView.getRoot().setExpanded(true);

            if (mainMd.getValue("path") != null) {
                mainLoadPath = modPath.getParentFile().getParent() + "\\" + (mainMd.getValue("path").toString().replaceAll("/", "\\\\"));
                loadMod();
            }
        }
    }

    protected void loadFiles(File files, TreeItem item) {
        try {
            for (File file : files.listFiles()) {
                System.out.println("Loading: " + file.getPath());
                TreeItem temp = new TreeItem<>(file.getName());
                if (!file.isDirectory()) {
                    item.getChildren().add(temp);
                    try{
                        temp.getChildren().addAll(DataParser.parseAll(file));
                    }catch (IOException e){
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[ERROR] IGNORE: Empty/Missing Folder, FROM: " + files.getPath());
        }
    }

    protected void loadEvents(){
        TreeItem<String> event = new TreeItem<>("events");
        itemView.getRoot().getChildren().add(event);
        loadFiles(new File(mainLoadPath + "\\events"), event);
    }

    protected void loadLocale(){
        TreeItem<String> locale = new TreeItem<>("localisation");
        itemView.getRoot().getChildren().add(locale);
        try{
            for (File file : new File(mainLoadPath + "\\localisation").listFiles()) {
                TreeItem<String> temp = new TreeItem<>(file.getName());
                locale.getChildren().add(temp);
                try{
                    temp.getChildren().add(new TreeItem(new Locale(file.getPath())));
                }catch (IOException e){
                }
            }
        }catch (NullPointerException e){
            System.out.println("[ERROR] Locale Not Found");
        }
    }

    protected void loadCommon() {
        TreeItem<String> common = new TreeItem<>("common");
        itemView.getRoot().getChildren().add(common);
        for (String folder : DataLoc.common) {
            TreeItem<String> temp = new TreeItem<>(folder);
            TreeItem<String> subfolder;
            String sF_name;
            switch (folder) {
                case "governments":
                    sF_name = "authorities";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(mainLoadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);

                    sF_name = "civics";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(mainLoadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;

                case "random_names":
                    sF_name = "base";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(mainLoadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;

                case "technology":
                    sF_name = "category";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(mainLoadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);

                    sF_name = "tier";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(mainLoadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;
            }

            common.getChildren().add(temp);
            loadFiles(new File(mainLoadPath + "\\common\\" + folder), temp);
        }
    }

    protected void loadMod() {
//        LinkedList<Component> compList = new LinkedList<>();
//        try {
//            for (File file : new File(mainLoadPath + "\\" + DataLoc.component_sets).listFiles()) {
//                itemView.getRoot().getChildren().addAll(DataParser.parseSet(file));
//            }
//            for (File file : new File(mainLoadPath + "\\" + DataLoc.component_templates).listFiles()) {
//                compList.addAll(DataParser.parseCompUtil(file));
//            }
//            for (Component comp : compList) {
//                for (Object set : itemView.getRoot().getChildren()) {
//                    if (((TreeItem) set).getValue().toString().equals(((PairLinkedList) comp.getValue("component_set")).getFirstString())) {
//                        ((CompSetUI) ((CompSet) ((TreeItem) set).getValue()).ui).addComp(comp);
//                    }
//
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Empty/Missing Folder.");
//        } catch (NullPointerException e) {
//            System.out.println("Error: Malformed Input");
//            e.printStackTrace();
//        }
        loadCommon();
        loadEvents();
        loadLocale();
    }

    protected void open(GenericData obj) {
        obj.ui.setTree(itemView);
        if (!mainWindow.getChildren().contains(obj.ui))
            mainWindow.getChildren().add(obj.ui);
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