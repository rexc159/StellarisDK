package com.StellarisDK.tools.fileClasses.ship;

import com.StellarisDK.tools.fileClasses.modifier.weight_modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompUtil extends Component_Template {
    private String modifier;
    private weight_modifier weight;

    public CompUtil(){
    }

    public CompUtil(String input){

        Matcher kv_match = kv.matcher(input);
        Matcher cv_match = cv.matcher(input);

        while(kv_match.find()){
            System.out.println(kv_match.group(1)+":"+kv_match.group(2));
        }

        while(cv_match.find()){
            System.out.println(cv_match.group());
        }
    }

    @Override
    public String toString() {
        return data.get("key").toString();
    }
}