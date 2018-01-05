package com.StellarisDK.tools.fileClasses.ship;

import java.util.regex.Matcher;

public class CompWeap extends Component_Template{
    private String keys[] = {"key", "size", "icon", "icon_frame", "power", "cost", "prerequisites", "component_set"};

    public CompWeap(){
    }

    public void load(String input){
        Matcher kv_match = kv.matcher(input);
        Matcher cv_match = cv.matcher(input);

        System.out.println(kv_match.group());

        System.out.println(cv_match.group());
    }
}
