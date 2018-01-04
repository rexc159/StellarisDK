package com.StellarisDK.tools.fileClasses.ship;

import com.StellarisDK.tools.fileClasses.modifier.weight_modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompUtil extends Component_Template {
    private String modifier;
    private weight_modifier weight;

    public CompUtil(){
    }

    public CompUtil(String data){
        Pattern p = Pattern.compile("");   // the pattern to search for
        Matcher m = p.matcher(data);

    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public weight_modifier getWeight() {
        return weight;
    }

    public void setWeight(weight_modifier weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return getKey();
    }
}