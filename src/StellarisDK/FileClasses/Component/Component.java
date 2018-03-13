package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.CompUI;
import javafx.scene.control.TreeItem;

public class Component extends GenericData {

    @Override
    public void setRequiredSet() {
        requiredSet = new DataEntry[]{};
    }

    public Component() {
        super();
        setType(0);
        ui = new CompUI(this);
    }

    public Component(String input) {
        super(input);
        ui = new CompUI(this);
    }

    public Component(String input, int type) {
        super(input);
        setType(type);
        ui = new CompUI(this);
    }

    public Component(String input, String type) {
        super(input, type);
        ui = new CompUI(this);
    }

    public void setType(int type){
        switch (type) {
            default:
            case 0:
                this.type = new DataEntry("utility_component_template", 1001);
                break;
            case 1:
                this.type = new DataEntry("weapon_component_template", 1001);
                break;
            case 2:
                this.type = new DataEntry("strike_craft_component_template", 1001);
                break;
        }
    }

    @Override
    public Component createNew() {
        return new Component();
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }

    @Override
    public TreeItem getRequiredTreeSet() {
        return null;
    }
}