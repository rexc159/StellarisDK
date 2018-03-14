package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.EventUI;

public class Event extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    private String namespace;

    public Event() {
        super();
        this.type = new DataEntry("event", 1011);
        ui = new EventUI(this);
    }

    public Event(String input, String type) {
        super(input, type);
        ui = new EventUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Event();
    }

}
