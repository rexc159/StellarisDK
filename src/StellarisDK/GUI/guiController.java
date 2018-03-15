package StellarisDK.GUI;

import StellarisDK.DataLoc;
import StellarisDK.FileClasses.*;
import StellarisDK.FileClasses.Anomaly.Anomaly;
import StellarisDK.FileClasses.Anomaly.AnomalyCategory;
import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static StellarisDK.FileClasses.DataParser.parseToConsole;


public class guiController extends AnchorPane {
    private Stage stage;
    private String mainLoadPath;
    private File path;

    public static TreeItem compSet;

    private ModDescriptor mainMd;

    private GenericData test;

    private TreeItem modRoot;
    private TreeItem vanillaRoot = new TreeItem<>("Stellaris");

    private GenericData cContent;
    private String cParent;

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

        itemView.setRoot(new TreeItem<>("root"));
        itemView.setShowRoot(false);
        itemView.setEditable(true);
        itemView.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView param) {
                TreeCell<Object> cell = new TreeCell<Object>() {
                    private TextField txt;

                    @Override
                    public void startEdit() {
                        if (getItem() instanceof GenericData) {
                            System.out.println("Starting Editor");
                            super.startEdit();
                            ((GenericData) this.getItem()).ui.setRoot(mainWindow);
                            open((GenericData) this.getItem());
                            super.cancelEdit();
                        } else if (getItem().toString().endsWith(".txt")) {
                            super.startEdit();
                            if (txt == null) {
                                txt = new TextField(getItem().toString());
                                txt.setOnKeyReleased(event -> {
                                    if (event.getCode() == KeyCode.ENTER) {
                                        if (!txt.getText().trim().endsWith(".txt")) {
                                            txt.setText(txt.getText().trim() + ".txt");
                                        }
                                        commitEdit(txt.getText());
                                    } else if (event.getCode() == KeyCode.ESCAPE) {
                                        cancelEdit();
                                    }
                                });
                            }
                            setText(null);
                            setGraphic(txt);
                            txt.selectAll();
                        }
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        setText(getItem().toString());
                        setGraphic(null);
                    }

                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (isEditing()) {
                                if (txt != null) {
                                    txt.setText(getItem().toString());
                                }
                                setText(null);
                                setGraphic(txt);
                            } else {
                                setText(getItem().toString());
                                setGraphic(null);
                            }
                        }
                    }
                };

                cell.setOnDragDetected(event -> {
                    TreeItem item = cell.getTreeItem();
                    if (item.isLeaf() && item.getParent().getValue().toString().endsWith(".txt")) {
                        Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent clipboard = new ClipboardContent();
                        clipboard.putString(Integer.toString(item.getParent().getChildren().indexOf(item)));
                        db.setContent(clipboard);
                    }
                    event.consume();
                });

                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell) {
                        TreeItem source;
                        TreeItem item = cell.getTreeItem();
                        if (item.getValue().toString().endsWith(".txt") || item.getParent().getValue().toString().endsWith(".txt")) {
                            TreeItem targetParent = item.getParent();
                            if (event.getGestureSource() instanceof LabeledText) {
                                source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
                            } else {
                                source = ((TreeCell) event.getGestureSource()).getTreeItem();
                            }
                            TreeItem parent = source.getParent().getParent();
                            if (targetParent.getValue().equals(parent.getValue()) || targetParent.getParent().getValue().equals(parent.getValue())) {
                                event.acceptTransferModes(TransferMode.MOVE);
                            }
                        }
                    }
                    event.consume();
                });

                cell.setOnDragEntered(event -> {
                    if (event.getGestureSource() != cell) {
                        cell.updateSelected(true);
                    }
                    event.consume();
                });

                cell.setOnDragExited(event -> {
                    cell.updateSelected(false);
                    event.consume();
                });

                cell.setOnDragDropped(event -> {
                    System.out.println("FROM: " + event.getGestureSource());
                    System.out.println("TO: " + cell);
                    TreeItem target = cell.getTreeItem();
                    TreeItem source;
                    if (event.getGestureSource() instanceof LabeledText) {
                        source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
                    } else {
                        source = ((TreeCell) event.getGestureSource()).getTreeItem();
                    }
                    int index = target.getParent().getChildren().indexOf(target);
                    source.getParent().getChildren().remove(source);
                    if (target.getValue().toString().endsWith(".txt")) {
                        target.getChildren().add(source);
                    } else {
                        if (index == target.getParent().getChildren().size()) {
                            target.getParent().getChildren().add(source);
                        } else {
                            target.getParent().getChildren().add(index, source);
                        }
                    }
                    event.setDropCompleted(true);
                    event.consume();
                });

                cell.setOnDragDone(event -> {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        System.out.println("Done");
                    }
                    event.consume();
                });

                setCM(cell);

                return cell;
            }

            private MenuItem createItem(TreeCell cell, String type) {
                MenuItem item = new MenuItem(type);
                item.setOnAction(event -> {
                    cell.getTreeItem().getChildren().add(createNew(type));
                });
                return item;
            }

            private void setCM(TreeCell cell) {
                ContextMenu contextMenu = new ContextMenu();
                Menu create = new Menu("New..");

                MenuItem createNew = new MenuItem("New");
                createNew.setOnAction(event -> {
                    if (cell.getTreeItem().getValue() instanceof GenericData) {
                        TreeItem newCell = new TreeItem<>(((GenericData) cell.getTreeItem().getValue()).createNew());
                        cell.getTreeItem().getParent().getChildren().add(newCell);
                    } else if (cell.getTreeItem().getValue().toString().endsWith(".txt")) {
                        cell.getTreeItem().getChildren().add(createNew(cell.getTreeItem().getParent().getValue().toString()));
                    } else if (cell.getTreeItem().getParent() != null) {
                        if (cell.getItem().equals("events") || cell.getTreeItem().getParent().getValue().toString().equals("common")) {
                            cell.getTreeItem().getChildren().add(new TreeItem<>("New File.txt"));
                        } else if (cell.getTreeItem().getParent().getValue().toString().equals("localisation")) {
                            cell.getTreeItem().getChildren().add(new TreeItem<>("New Locale.yml"));
                        }
                    }
                });

                MenuItem edit = new MenuItem("Rename");
                edit.setOnAction(event -> {
                    cell.startEdit();
                });

                MenuItem delete = new MenuItem("Delete");
                delete.setOnAction(event -> {
                    if (cell.getItem().toString().contains(".txt") || cell.getTreeItem().getParent().getValue().toString().contains(".txt")) {
                        cell.getTreeItem().getParent().getChildren().remove(cell.getTreeItem());
                    }
                });

                MenuItem cut = new MenuItem("Cut");
                cut.setOnAction(event -> {
                    if (cell.getTreeItem().getParent().getValue().toString().endsWith(".txt")) {
                        cContent = (GenericData) cell.getItem();
                        cParent = cell.getTreeItem().getParent().getParent().getValue().toString();
                        cell.getTreeItem().getParent().getChildren().remove(cell.getTreeItem());
                    }
                });

                MenuItem copy = new MenuItem("Copy");
                copy.setOnAction(event -> {
                    if (cell.getTreeItem().getParent().getValue().toString().endsWith(".txt")) {
                        cContent = ((GenericData) cell.getItem()).clone();
                        cParent = cell.getTreeItem().getParent().getParent().getValue().toString();
                    }
                });

                MenuItem paste = new MenuItem("Paste");
                paste.setOnAction(event -> {
                    if (cContent.getClass().equals(cell.getItem().getClass())) {
                        cell.getTreeItem().getParent().getChildren().add(new TreeItem<>(cContent.clone()));
                    } else if (cell.getTreeItem().getValue().toString().endsWith(".txt")) {
                        if (cell.getTreeItem().getParent().getValue().equals(cParent))
                            cell.getTreeItem().getChildren().add(new TreeItem<>(cContent.clone()));
                    }
                });

                cell.setOnContextMenuRequested(event -> {
                    cell.getContextMenu().getItems().clear();
                    create.getItems().clear();
                    if (cContent == null) {
                        paste.setDisable(true);
                    } else {
                        paste.setDisable(false);
                    }
                    if (cell.getItem() != null && cell.getTreeItem().getParent() != null) {
                        if (cell.getTreeItem().getParent().getValue().equals("common") || cell.getItem().equals("events")) {
                            cell.getContextMenu().getItems().add(createNew);
                        } else if (cell.getItem().toString().endsWith(".txt")) {
                            if (cell.getTreeItem().getParent().getValue().toString().equals("anomalies")) {
                                cell.getContextMenu().getItems().addAll(create, edit, paste, delete);
                                create.getItems().addAll(createItem(cell, "anomaly"), createItem(cell, "anomaly_category"));
                            } else {
                                cell.getContextMenu().getItems().addAll(createNew, edit, paste, delete);
                            }
                        } else if (cell.getTreeItem().getParent().getValue().toString().endsWith(".txt")) {
                            cell.getContextMenu().getItems().addAll(createNew, cut, copy, paste, delete);
                        }
                    }
                });

                contextMenu.getItems().addAll(createNew, cut, copy, paste, delete);

                cell.setContextMenu(contextMenu);
            }
        });

        itemView.setOnKeyPressed(event -> {
            TreeItem selected = (TreeItem) itemView.getSelectionModel().getSelectedItem();
            if (new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN).match(event)) {
                if (selected.getParent() != null && selected.getParent().getValue().toString().endsWith(".txt")) {
                    System.out.println("CTRL+X");
                    cContent = (GenericData) selected.getValue();
                    cParent = selected.getParent().getParent().getValue().toString();
                    selected.getParent().getChildren().remove(selected);
                }
            } else if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN).match(event)) {
                if (selected.getParent() != null && selected.getParent().getValue().toString().endsWith(".txt")) {
                    System.out.println("CTRL+C");
                    cContent = ((GenericData) selected.getValue()).clone();
                    cParent = selected.getParent().getParent().getValue().toString();
                }
            } else if (new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN).match(event)) {
                System.out.println("CTRL+V");
                if (cContent.getClass().equals(selected.getValue().getClass())) {
                    selected.getParent().getChildren().add(new TreeItem<>(cContent.clone()));
                } else if (selected.getValue().toString().endsWith(".txt")) {
                    if (selected.getParent().getValue().equals(cParent))
                        selected.getChildren().add(new TreeItem<>(cContent.clone()));
                }
            } else if (new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN).match(event)) {
                System.out.println("CTRL+N");
                if (!(selected.getValue() instanceof ModDescriptor) && selected.getValue() instanceof GenericData) {
                    TreeItem newCell = new TreeItem<>(((GenericData) selected.getValue()).createNew());
                    selected.getParent().getChildren().add(newCell);
                } else if (selected.getValue().toString().endsWith(".txt")) {
                    selected.getChildren().add(createNew(selected.getParent().getValue().toString()));
                } else if (selected.getParent() != null) {
                    if (selected.getValue().equals("events") || selected.getParent().getValue().toString().equals("common")) {
                        selected.getChildren().add(new TreeItem<>("New File.txt"));
                    } else if (selected.getParent().getValue().toString().equals("localisation")) {
                        selected.getChildren().add(new TreeItem<>("New Locale.yml"));
                    }
                }
            } else if (event.getCode() == KeyCode.DELETE) {
                if (selected.getValue().toString().contains(".txt") || selected.getParent().getValue().toString().contains(".txt")) {
                    selected.getParent().getChildren().remove(selected);
                }
            }
            event.consume();
        });
    }

    private TreeItem createNew(String type) {
        switch (type) {
            case "agendas":
                return new TreeItem<>(new Agenda());
            case "ambient_objects":
                return new TreeItem<>(new AmbientObject());
            case "anomalies":
            case "anomaly":
                return new TreeItem<>(new Anomaly());
            case "anomaly_category":
                return new TreeItem<>(new AnomalyCategory());
            case "armies":
                return new TreeItem<>(new Army());
            case "ascension_perks":
                return new TreeItem<>(new Tradition(false));
            case "attitudes":
                return new TreeItem<>(new Attitude());
            case "bombardment_stances":
                break;
            case "buildable_pops":
                break;
            case "building tags":
                break;
            case "buildings":
                break;
            case "colors":
                break;
            case "component_sets":
                return new TreeItem<>(new CompSet());
            case "component_templates":
                return new TreeItem<>(new Component());
            case "country_types":
            case "defines":
            case "deposits":
            case "diplo_phrases":
            case "edicts":
            case "event_chains":
            case "fallen_empires":
            case "game_rules":
            case "global_ship_designs":
            case "governments":
            case "authorities":
            case "civics":
            case "graphical_culture":
            case "mandates":
            case "map_modes":
            case "megastructures":
            case "name_lists":
            case "notification_modifiers":
            case "observation_station_missions":
            case "on_actions":
            case "opinion_modifiers":
            case "personalities":
            case "planet_classes":
            case "planet_modifiers":
            case "policies":
            case "pop_faction_types":
            case "precursor_civilizations":
            case "random_names":
            case "base":
            case "scripted_effects":
            case "scripted_loc":
            case "scripted_triggers":
            case "scripted_variables":
            case "section_templates":
            case "sector_settings":
            case "sector_types":
            case "ship_behaviors":
            case "ship_sizes":
            case "solar_system_initializers":
            case "special_projects":
            case "species_archetypes":
            case "species_classes":
            case "species_names":
            case "species_rights":
            case "star_classes":
            case "starbase_buildings":
            case "starbase_levels":
            case "starbase_modules":
            case "starbase_types":
            case "start_screen_messages":
            case "static_modifiers":
            case "strategic_resources":
            case "subjects":
            case "system_types":
            case "technology":
            case "category":
            case "tier":
            case "terraform":
            case "tile_blockers":
            case "tradition_categories":
            case "traditions":
                return new TreeItem<>(new Tradition(true));
            case "traits":
                break;
            case "events":
                return new TreeItem<>(new Event());
            case "localisation":
        }
        return new TreeItem<>("Not Implemented");
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
                parseToConsole(path);
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
        if (modRoot != null) {
            itemView.getRoot().getChildren().remove(modRoot);
        }
        modRoot = new TreeItem<>(mainMd);
        itemView.getRoot().getChildren().add(modRoot);
        modRoot.getChildren().add(new TreeItem<>(mainMd));
        itemView.getRoot().setExpanded(true);
        loadMod("", modRoot, false);
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
            if (modRoot != null) {
                itemView.getRoot().getChildren().remove(modRoot);
            }
            modRoot = new TreeItem<>(mainMd);
            itemView.getRoot().getChildren().add(modRoot);
            modRoot.getChildren().add(new TreeItem<>(mainMd));
            itemView.getRoot().setExpanded(true);
            if (mainMd.getValue("path") != null) {
                mainLoadPath = modPath.getParentFile().getParent() + "\\" + (mainMd.getValue("path").toString().replaceAll("/", "\\\\"));
                loadMod(mainLoadPath, modRoot, true);
            }
        }
    }

    @FXML
    protected void loadVanilla() {
        DirectoryChooser fc = new DirectoryChooser();
        File main = fc.showDialog(stage);
        if (main != null) {
            if (main.getPath().endsWith("Stellaris")) {
                if (!itemView.getRoot().getChildren().contains(vanillaRoot))
                    itemView.getRoot().getChildren().add(0, vanillaRoot);
                loadCommon(main.getPath(), vanillaRoot, true);
                loadEvents(main.getPath(), vanillaRoot, true);
                loadLocale(main.getPath(), vanillaRoot, true);
            } else {
                openWarningBox("Please select Stellaris main folder.");
            }
        }
    }

    private void openWarningBox(String msg) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);
        HBox box = new HBox();
        box.getChildren().add(new Text(msg));
        dialog.setScene(new Scene(box));
        dialog.show();
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
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[ERROR] IGNORE: Empty/Missing Folder, FROM: " + files.getPath());
        }
        System.gc();
    }

    private void loadEvents(String loadPath, TreeItem root, boolean load) {
        TreeItem<String> event = new TreeItem<>("events");
        root.getChildren().add(event);
        if (load)
            loadFiles(new File(loadPath + "\\events"), event);
    }

    private void loadLocale(String loadPath, TreeItem root, boolean load) {
        TreeItem<String> locale = new TreeItem<>("localisation");
        root.getChildren().add(locale);
        if (load) {
            try {
                for (File file : new File(loadPath + "\\localisation").listFiles()) {
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
    }

    private void loadCommon(String loadPath, TreeItem root, boolean load) {
        TreeItem<String> common = new TreeItem<>("common");
        root.getChildren().add(common);
//        itemView.getRoot().getChildren().add(common);
        for (String folder : DataLoc.common) {
            TreeItem<String> temp = new TreeItem<>(folder);
            if (folder.equals("component_sets"))
                compSet = temp;
            TreeItem<String> subfolder;
            String sF_name;
            switch (folder) {
                case "governments":
                    sF_name = "authorities";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(loadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);

                    sF_name = "civics";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(loadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;

                case "random_names":
                    sF_name = "base";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(loadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;

                case "technology":
                    sF_name = "category";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(loadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);

                    sF_name = "tier";
                    subfolder = new TreeItem<>(sF_name);
                    temp.getChildren().add(subfolder);
                    loadFiles(new File(loadPath + "\\common\\" + folder + "\\" + sF_name), subfolder);
                    break;
            }
            common.getChildren().add(temp);
            if (load)
                loadFiles(new File(loadPath + "\\common\\" + folder), temp);
        }
    }

    private void loadMod(String loadPath, TreeItem root, boolean load) {
        loadCommon(loadPath, root, load);
        loadEvents(loadPath, root, load);
        loadLocale(loadPath, root, load);
    }

    private void open(GenericData obj) {
        obj.ui.setTree(itemView);
        obj.ui.load();
        if (!mainWindow.getChildren().contains(obj.ui))
            mainWindow.getChildren().add(obj.ui);
    }

    private void saveFiles(File saveLoc, TreeItem current) {
        boolean output = false;
        String temp = "";
        for (Object item : current.getChildren()) {
            if (((TreeItem) item).getValue() instanceof GenericData || ((TreeItem) item).getValue().equals("Constants") || ((TreeItem) item).getValue().equals("Namespace")) {
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
                output = false;
                temp = ((Locale) ((TreeItem) item).getValue()).export();
            } else if (((TreeItem) item).getChildren().size() != 0) {
                if (((TreeItem) item).getValue().equals("Constants") || ((TreeItem) item).getValue().equals("Namespace")) {
                    saveFiles(saveLoc, (TreeItem) item);
                } else {
                    saveLoc.mkdir();
                    File test = new File(saveLoc.getPath() + "\\" + ((TreeItem) item).getValue());
                    saveFiles(test, (TreeItem) item);
                }
            }
        }
        if (output) {
            System.out.println("Exporting to:" + saveLoc.getPath());
            File out = new File(saveLoc.getPath());
            try {
                if (out.isDirectory()) {
                    FileWriter fw = new FileWriter(new File(out.getParent() + "\\" + modRoot.getValue() + ".mod"));
                    fw.write(mainMd.export());
                    fw.close();
                } else {
                    if (!out.exists()) {
                        out.createNewFile();
                    }
                    FileWriter fw = new FileWriter(out);
                    fw.write(temp);
                    fw.close();
                }
            } catch (IOException e) {
                System.out.println("[ERROR] Export Failed, FROM: " + saveLoc.getPath());
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void saveAll() {
        if (mainMd != null && mainMd.getValue("path") != null) {
            DirectoryChooser fc = new DirectoryChooser();
            File main = fc.showDialog(stage);
            if (main != null) {
                String folder = mainMd.getDir();
                main = new File(main.getPath(), folder);
                if (main.exists()) {
                    DateTimeFormatter dTF = DateTimeFormatter.ofPattern("_yyyy-MM-dd_HH-mm-ss");
                    main.renameTo(new File(main.getParent(), folder + dTF.format(LocalDateTime.now())));
                    main = new File(main.getParent(), folder);
                }
                main.mkdir();
                saveFiles(main, modRoot);
                openWarningBox("Saving Complete! Remember to transfer resources from original folder.");
            }
        } else {
            openWarningBox("No Mod Loaded/No Mod Path Defined!");
        }
    }

    @FXML
    protected void closeMod() {
        itemView.getRoot().getChildren().remove(modRoot);
        modRoot = null;
        System.gc();
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