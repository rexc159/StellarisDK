package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.CompUI;

public class Component extends GenericData {

    public Component() {
        super();
        ui = new CompUI(this);
    }

    public Component(int type) {
        super();
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
                this.type = "utility_component_template";
                break;
            case 1:
                this.type = "weapon_component_template";
                break;
            case 2:
                this.type = "strike_craft_component_template";
                break;
        }
    }

    @Override
    public Component createNew() {
        return new Component(0);
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }
}