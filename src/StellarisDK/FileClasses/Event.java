package StellarisDK.FileClasses;

import StellarisDK.GUI.EventUI;
import javafx.scene.control.TreeItem;

public class Event extends GenericData {

    private String namespace;

    public Event() {
        super();
        ui = new EventUI(this);
    }

    public Event(String input) {
        super(input);
        this.type = "country_event";
        ui = new EventUI(this);
    }

    public TreeItem toTreeItem(){
        return data.toTreeItem(type);
    }

    @Override
    public GenericData createNew() {
        return new Event();
    }
}
