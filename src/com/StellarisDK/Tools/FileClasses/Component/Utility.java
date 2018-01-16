package com.StellarisDK.Tools.FileClasses.Component;

import com.StellarisDK.Tools.FileClasses.Locale;

public class Utility extends Component_Template {
    protected String keys[] = {"key", "hidden", "size", "icon", "icon_frame", "power", "cost",
            "ftl", "armor_value",
            "ship_modifier", "modifier", "ai_weight", "prerequisites",
            "friendly_aura", "hostile_aura",
            "class_restriction", "component_set", "upgrades_to"};

    public Utility(){
        for (String key : keys) {
            data.put(key, null);
        }
    }

    public Utility(String input){
        this();
        parse(input);
    }

    public String output() {
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

    @Override
    public String toString() {
        String temp = Locale.getLocale((String)data.get("key"));
        if(temp != null)
            return temp;
        else
            return data.get("key").toString();
    }
}