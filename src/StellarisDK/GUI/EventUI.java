package StellarisDK.GUI;

import StellarisDK.FileClasses.Event;
import StellarisDK.FileClasses.Helper.DataCell;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class EventUI extends AbstractUI {

    @FXML
    protected TreeView treeView;

    public EventUI() {
        init("FXML/eventFX.fxml");
        window.setText("Event Editor");
    }


    public EventUI(Event obj) {
        init("FXML/eventFX.fxml");
        window.setText("Component Editor");
        load(obj);
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
        } else {
            obj = (Event) object;
        }
        treeView.setEditable(true);
        treeView.setCellFactory(param -> new DataCell());
        treeView.setRoot(obj.toTreeItem());
    }

    @Override
    public Object save() {
        return null;
    }
}