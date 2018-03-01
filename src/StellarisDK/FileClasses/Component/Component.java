package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import StellarisDK.GUI.CompUI;

public class Component extends GenericData {

    public Component() {
        super();
        ui = new CompUI(this);
    }

    public Component(int type) {
        super();
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
        ui = new CompUI(this);
    }

    public Component(String input) {
        super(input);
        ui = new CompUI(this);
    }

    public Component(String type, int x) {
        this();
        this.type = type;
    }

    public Component(String input, String type) {
        super(input, type);
        ui = new CompUI(this);
    }

    @Override
    public void setValue(String key, Object value, boolean addIfAbsent) {
        if (addIfAbsent && !data.containsKey(key)) {
            PairArrayList temp = new PairArrayList();
            temp.add(new ValueTriplet<>("=", value, data.size()));
            data.putIfAbsent(key, temp);
        } else {
            ((ValueTriplet)((PairArrayList)data.get(key)).get(0)).setValue(value);
        }
    }

    @Override
    public Component createNew() {
        return new Component(type, 0);
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }
}