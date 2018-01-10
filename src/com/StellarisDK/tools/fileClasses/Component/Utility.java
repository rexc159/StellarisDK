package com.StellarisDK.tools.fileClasses.Component;

import com.StellarisDK.tools.fileClasses.Locale;

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
        return Locale.getLocale((String)data.get("key"));
    }
}