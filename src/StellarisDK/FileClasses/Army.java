package StellarisDK.FileClasses;

import StellarisDK.GUI.ArmyUI;
import javafx.scene.control.TreeItem;

public class Army extends GenericData {

    public Army() {
        super();
        this.type = "army";
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

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}
