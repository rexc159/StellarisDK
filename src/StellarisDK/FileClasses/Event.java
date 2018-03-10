package StellarisDK.FileClasses;

import StellarisDK.GUI.EventUI;
import javafx.scene.control.TreeItem;

public class Event extends GenericData {

    private String namespace;

    public Event() {
        super();
        this.type = "event";
        ui = new EventUI(this);
    }

    public Event(String input) {
        super(input);
        this.type = "country_event";
        ui = new EventUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Event();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}
