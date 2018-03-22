package StellarisDK.GUI;

import StellarisDK.FileClasses.ModDescriptor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.VBox;

import javax.swing.filechooser.FileSystemView;
import java.util.ArrayList;

public class ModLoader extends AbstractUI {

    @FXML
    private VBox list;

    private ArrayList<TreeItem> modList;

    public ModLoader(ArrayList<TreeItem> modList) {
        init("FXML/modListFX.fxml");
        window.setText("Mod Loader");
        this.modList = modList;
    }

    @Override
    public void load() {
        for (TreeItem modList : modList) {
            CheckBox temp = new CheckBox(modList.getValue().toString());
            temp.setId(temp.getText());
            list.getChildren().add(temp);
        }
        Button load = new Button("Load Selected");
        load.setOnAction(event -> removeUnchecked());
        list.getChildren().add(load);
    }

    private void removeUnchecked() {
        for (TreeItem item : modList) {
            itemView.getRoot().getChildren().remove(item);
            if (((CheckBox) list.getChildren().get(modList.indexOf(item))).isSelected()) {
                itemView.getRoot().getChildren().add(item);
                String mainLoadPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() +
                        "\\Paradox Interactive\\Stellaris\\" +
                        (((ModDescriptor) item.getValue()).getValue("path").toString().replaceAll("/", "\\\\"));
                guiController.loadMod(mainLoadPath, item, true);
            }else{
                modList.set(modList.indexOf(item), null);
            }
        }
        root.getChildren().remove(this);
    }
}