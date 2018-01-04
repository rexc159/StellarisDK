package com.StellarisDK.tools.fileClasses.ship;

import com.StellarisDK.tools.fileClasses.gfx.Icon;

public class ComponentSet {
    private String key;
    private Icon Icon;

    public ComponentSet(String key, Icon Icon) {
        this.key = key;
        this.Icon = Icon;
    }

    public void setKey(String key){
        this.key = key;
    }
    public void setIcon(Icon Icon){
        this.Icon = Icon;
    }

    public String toString(){
        return "";
    }
}
