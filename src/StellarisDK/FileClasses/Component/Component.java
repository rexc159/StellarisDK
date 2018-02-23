package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Locale;
import StellarisDK.GUI.CompUI;

enum CompType {
    UTILITY,
    STRIKE_CRAFT,
    WEAPON
}

public class Component extends GenericData {
    private static int tab = 1;
    private CompType type;

    public Component() {
        super();
        ui = new CompUI(this);
    }

    public Component(String input) {
        super(input);
        ui = new CompUI(this);
    }

    @Override
    public String export() {
        String out = " = {";
        for (String key : data.keySet()) {
            if (data.get(key) != null) {
                String tabs = "\r\n";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                out += data.get(key).toString().replaceAll("#key", tabs + key);
//                out += tabs + key + data.get(key).toString();
            }
        }
        return out + "\r\n}\r\n";
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }

    @Override
    public String toString() {
        if(!data.containsKey("key")){
            return "Empty Name";
        }
        String temp = Locale.getLocale(((PairArrayList)data.get("key")).getFirstString());
        if (temp != null)
            return temp;
        else
            return ((PairArrayList)data.get("key")).getFirstString();
    }
}