package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.CompUI;

import java.util.ArrayList;
import java.util.Arrays;

public class Component extends GenericData {

    @Override
    public void setDataEntries() {
        DataEntry key = new DataEntry<>("key", "=", "new_component", 1111);
        DataEntry size = new DataEntry<>("size", "=", "size", 1111);
        DataEntry icon = new DataEntry<>("icon", "=", "new_component", 1111);
        DataEntry icon_frame = new DataEntry<>("icon_frame", "=", "new_component", 1111);
        DataEntry power = new DataEntry<>("power", "=", "new_component", 1111);
        DataEntry cost = new DataEntry<>("cost", "=", "new_component", 1111);
        DataEntry component_set = new DataEntry<>("component_set", "=", "new_component", 1111);

        dataEntries = new ArrayList<>(Arrays.asList(key, size, icon, icon_frame, power, cost, component_set));
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
}