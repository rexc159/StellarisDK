package StellarisDK.GUI;

import StellarisDK.DataLoc;
import StellarisDK.FileClasses.DataParser;
import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Locale;
import StellarisDK.FileClasses.ModDescriptor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static StellarisDK.FileClasses.DataParser.parseData;

public class guiController extends AnchorPane {
    private Stage stage;
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

        itemView.setEditable(true);
        itemView.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView param) {
                TreeCell<Object> cell = new TreeCell<Object>(){
                    @Override
                    public void startEdit(){
                        if(this.getTreeItem().getValue() instanceof GenericData){
                            System.out.println("Starting Editor");
                            super.startEdit();
                            open((GenericData) this.getTreeItem().getValue());
                            super.cancelEdit();
                        }
                    }

                    @Override
                    protected void updateItem(Object item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.toString());
                        }
                    }
                };
                ContextMenu contextMenu = createCM(cell);
                cell.setContextMenu(contextMenu);
                return cell;
            }
        });
    }

    private ContextMenu createCM(TreeCell cell) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createNew = new MenuItem("New..");
        createNew.setOnAction(event -> {
            if (cell.getTreeItem().getValue() instanceof GenericData) {
                cell.getTreeItem().getParent().getChildren().add(new TreeItem<>("Test"));
            } else if (cell.getTreeItem().getValue().toString().contains(".txt")) {
                cell.getTreeItem().getChildren().add(new TreeItem<>("Item"));
            } else if (cell.getTreeItem().getParent() != null) {
                if (cell.getTreeItem().getParent().getValue().equals("common")) {
                    cell.getTreeItem().getChildren().add(new TreeItem<>("Test.txt"));
                }
            }
        });
        contextMenu.getItems().add(createNew);
        return contextMenu;
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
        File modPath = fc.showOpenDialog(stage);
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

    private void loadFiles(File files, TreeItem item) {
        try {
            for (File file : files.listFiles()) {
                System.out.println("Loading: " + file.getPath());
                TreeItem temp = new TreeItem<>(file.getName());
                if (!file.isDirectory()) {
                    item.getChildren().add(temp);
                    try {
                        temp.getChildren().addAll(DataParser.parseAll(file));
                    } catch (IOException e) {
                    }
                }
                System.gc();
            }
        } catch (NullPointerException e) {
            System.out.println("[ERROR] IGNORE: Empty/Missing Folder, FROM: " + files.getPath());
        }
    }

    private void loadEvents() {
        TreeItem<String> event = new TreeItem<>("events");
        itemView.getRoot().getChildren().add(event);
        loadFiles(new File(mainLoadPath + "\\events"), event);
    }

    private void loadLocale() {
        TreeItem<String> locale = new TreeItem<>("localisation");
        itemView.getRoot().getChildren().add(locale);
        try {
            for (File file : new File(mainLoadPath + "\\localisation").listFiles()) {
                TreeItem<String> temp = new TreeItem<>(file.getName());
                locale.getChildren().add(temp);
                try {
                    temp.getChildren().add(new TreeItem(new Locale(file.getPath())));
                } catch (IOException e) {
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[ERROR] Locale Not Found");
        }
    }

    private void loadCommon() {
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

    private void loadMod() {
        loadCommon();
        loadEvents();
        loadLocale();
    }

    private void open(GenericData obj) {
        obj.ui.setTree(itemView);
        if (!mainWindow.getChildren().contains(obj.ui))
            mainWindow.getChildren().add(obj.ui);
    }

    private void saveFiles(File saveLoc, TreeItem current) {
        boolean output = false;
        String temp = "";
        for (Object item : current.getChildren()) {
            if (((TreeItem) item).getValue() instanceof GenericData || ((TreeItem) item).getValue().equals("Constants")) {
                if (((TreeItem) item).getChildren().size() != 0) {
                    for (Object constant : ((TreeItem) item).getChildren()) {
                        output = true;
                        temp += ((TreeItem) constant).getValue() + "\r\n";
                    }
                    temp += "\r\n";
                } else {
                    output = true;
                    temp += ((GenericData) ((TreeItem) item).getValue()).export() + "\r\n";
                }
            } else if (((TreeItem) item).getValue() instanceof Locale) {
                output = true;
                temp = ((Locale) ((TreeItem) item).getValue()).export();
            } else if (((TreeItem) item).getChildren().size() != 0) {
                if (((TreeItem) item).getValue().equals("Constants")) {
                    saveFiles(saveLoc, (TreeItem) item);
                } else {
                    saveLoc.mkdir();
                    File test = new File(saveLoc.getPath() + "\\" + ((TreeItem) item).getValue());
                    saveFiles(test, (TreeItem) item);
                }
            }
        }
        if(output){
            System.out.println("Exporting to:" + saveLoc.getPath());
            File out = new File(saveLoc.getPath());
            try{
                if(out.isDirectory()){
                    FileWriter fw = new FileWriter(new File(out.getParent()+"\\"+itemView.getRoot().getValue()+".mod"));
                    fw.write(mainMd.export());
                    fw.close();
                }else{
                    if(!out.exists()){
                        out.createNewFile();
                    }
                    FileWriter fw = new FileWriter(out);
                    fw.write(temp);
                    fw.close();
                }
            }catch (IOException e){
                System.out.println("[ERROR] Export Failed, FROM: "+saveLoc.getPath());
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void saveAll(){
        DirectoryChooser fc = new DirectoryChooser();
        File main = fc.showDialog(stage);
        if(main != null){
            main = new File(main.getPath()+ "\\"+ ((ModDescriptor)itemView.getRoot().getValue()).getDir());
            saveFiles(main, itemView.getRoot());
        }
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