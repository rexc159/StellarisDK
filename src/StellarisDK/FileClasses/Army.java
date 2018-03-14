package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.ArmyUI;

public class Army extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    public Army() {
        super();
        this.type = new DataEntry("army", 1011);
        ui = new ArmyUI(this);
    }

    public Army(String input, String type) {
        super(input, type);
        ui = new ArmyUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Army();
    }

}
