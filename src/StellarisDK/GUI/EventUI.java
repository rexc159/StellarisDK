package StellarisDK.GUI;

import StellarisDK.FileClasses.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;

public class EventUI extends AbstractUI {

    @FXML
    protected TreeView treeView;

    public EventUI() {
        init("FXML/eventUI.fxml");
        window.setText("Event Editor");
    }


    public EventUI(Event obj) {
        init("FXML/eventUI.fxml");
        window.setText("Component Editor");
        load(obj);
    }

    @Override
    public void load(Object object){
        if (object instanceof String) {
            obj.load((String) object);
        } else {
            obj = (Event) object;
        }
        treeView.setRoot(((Event)obj).toTreeItem());
    }

    @Override
    public Object save() {
        return null;
    }
}