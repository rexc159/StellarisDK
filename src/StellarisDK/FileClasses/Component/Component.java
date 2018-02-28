package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.GUI.CompUI;

public class Component extends GenericData {
    private static int tab = 1;

    public Component() {
        super();
        ui = new CompUI(this);
    }

    public Component(String input) {
        super(input);
        ui = new CompUI(this);
    }

    public Component(String input, String type) {
        super(input, type);
        ui = new CompUI(this);
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