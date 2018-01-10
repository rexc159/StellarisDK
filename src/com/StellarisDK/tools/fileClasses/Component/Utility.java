package com.StellarisDK.tools.fileClasses.Component;

public class Utility extends Component_Template {
    protected String keys[] = {"key", "hidden", "size", "icon", "icon_frame", "power", "cost",
            "ftl", "armor_value",
            "ship_modifier", "modifier", "ai_weight", "prerequisites",
            "class_restriction", "component_set"};

    public Utility(){
        for (String key : keys) {
            data.put(key, null);
        }
    }

    public Utility(String input){
        this();
        parse(input);
    }

    @Override
    public String toString() {
        String out = "";
        for (String key : keys) {
            if(data.get(key)!=null){
                if (data.get(key) instanceof String)
                    out += key + " = \"" + data.get(key).toString() + "\"\n";
                else
                    out += key + " = " + data.get(key).toString();
            }
        }
        return out;
    }
}