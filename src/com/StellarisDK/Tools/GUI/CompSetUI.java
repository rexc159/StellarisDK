package com.StellarisDK.Tools.GUI;

import com.StellarisDK.Tools.FileClasses.Component.CompSet;

public class CompSetUI extends AbstractUI{

    private CompSet set;

    public CompSetUI() {
        init("");
        window.setText("Component Set Editor");
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            set = new CompSet((String)object);
            System.out.println("Component Set Loaded");
        } else {
            set = (CompSet) object;
        }
    }

    @Override
    public Object save() {
        return null;
    }
}
