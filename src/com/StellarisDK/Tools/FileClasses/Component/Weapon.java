package com.StellarisDK.Tools.FileClasses.Component;

public class Weapon extends Component_Template{
    protected String keys[] = {"key", "hidden", "size", "icon", "icon_frame", "power", "cost",
            "ftl", "armor_value",
            "ship_modifier", "modifier", "ai_weight", "prerequisites",
            "class_restriction", "component_set"};

    public Weapon(){
        for (String key : keys) {
            data.put(key, null);
        }
    }
}
