package StellarisDK.GUI;

import StellarisDK.FileClasses.ModDescriptor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;

import java.util.ArrayList;
import java.util.Collections;

public class ModDescUI extends AbstractUI {

    @FXML
    private TextField path;

    @FXML
    private TextField supported_version;

    @FXML
    private TextField remote_file_id;

    @FXML
    private TextField name;

    @FXML
    private TextField archive;

    @FXML
    private TextField picture;

    @FXML
    private ListView dependencies;

    @FXML
    private ListView tags;

    public ModDescUI(ModDescriptor obj) {
        init("FXML/mdFX.fxml");
        window.setText("Mod Descriptor");
        this.obj = obj;
        makeEditable(dependencies);
        makeEditable(tags);
    }

    public void makeEditable(ListView node) {
        node.setEditable(true);
        node.setCellFactory(TextFieldListCell.forListView());
        node.setOnEditCommit(new EventHandler<ListView.EditEvent>() {
            @Override
            public void handle(ListView.EditEvent event) {
                if (event.getNewValue().toString().length() != 0)
                    node.getItems().set(event.getIndex(), event.getNewValue());
                else
                    node.getItems().remove(event.getIndex());
            }
        });
        node.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Node cell = event.getPickResult().getIntersectedNode();
                if (cell instanceof ListView || ((ListCell) cell).getItem() == null) {
                    node.getItems().add("");
                }
            }
        });
    }

    @Override
    public void load() {
        if(obj.getKey(dependencies.getId()))
            dependencies.getItems().setAll((ArrayList) obj.getValue(dependencies.getId()));
        if(obj.getKey(tags.getId()))
            tags.getItems().setAll((ArrayList) obj.getValue(tags.getId()));
        path.setText((String) obj.getValue(path.getId()));
        supported_version.setText((String) obj.getValue(supported_version.getId()));
        remote_file_id.setText((String) obj.getValue(remote_file_id.getId()));
        name.setText((String) obj.getValue(name.getId()));
        archive.setText((String) obj.getValue(archive.getId()));
        picture.setText((String) obj.getValue(picture.getId()));
    }

    private void saveNode(TextField node){
        if (node.getText() == null || node.getText().equals("")) {
            obj.setValue(node.getId(), null, true, 0);
        } else {
            obj.setValue(node.getId(), node.getText(), true, 0);
        }
    }

    @Override
    public Object save() {
        if(name.getText() == null || name.getText().equals("")){
            obj.setValue(name.getId(), "Unnamed Mod", true, 0);
        } else {
            obj.setValue(name.getId(), name.getText(), true, 0);
        }
        saveNode(path);
        saveNode(supported_version);
        saveNode(remote_file_id);
        saveNode(archive);
        saveNode(picture);
        if (tags.getItems().size()>0){
            ArrayList<Object> temp = new ArrayList<Object>() {
                @Override
                public String toString() {
                    String out = "{\n";
                    for (Object i : this) {
                        out += "\t\"" + ((String) i).trim() + "\"\n";
                    }
                    return out + "}\n";
                }
            };
            Collections.addAll(temp, tags.getItems().toArray());
            obj.setValue(tags.getId(), temp, true, 0);
        }
        if (dependencies.getItems().size()>0){
            ArrayList<Object> temp = new ArrayList<Object>() {
                @Override
                public String toString() {
                    String out = "{\n";
                    for (Object i : this) {
                        out += "\t\"" + ((String) i).trim() + "\"\n";
                    }
                    return out + "}\n";
                }
            };
            Collections.addAll(temp, dependencies.getItems().toArray());
            obj.setValue(dependencies.getId(), temp, true, 0);
        }
        itemView.refresh();
        return obj;
    }
}