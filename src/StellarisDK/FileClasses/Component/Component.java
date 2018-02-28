package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.CompUI;

public class Component extends GenericData {
    private static int tab = 1;

    public Component() {
        super();
        ui = new CompUI(this);
    }

    public Component(int type) {
        super();
        switch(type){
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
    public Component createNew(){
        return new Component(type, 0);
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }

//    @Override
//    public String toString() {
//        if(!data.containsKey("key")){
//            return "Empty Name";
//        }
//        String temp = Locale.getLocale(((PairArrayList)data.get("key")).getFirstString());
//        if (temp != null)
//            return temp;
//        else
//            return ((PairArrayList)data.get("key")).getFirstString();
//    }
}