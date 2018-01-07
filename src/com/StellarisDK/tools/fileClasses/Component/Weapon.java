package com.StellarisDK.tools.fileClasses.Component;

import java.util.regex.Matcher;

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

    public void load(String input){
        Matcher kv_match = kv.matcher(input);
        Matcher cv_match = cv.matcher(input);

        System.out.println(kv_match.group());

        System.out.println(cv_match.group());
    }
}
