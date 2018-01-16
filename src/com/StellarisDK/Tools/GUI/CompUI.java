package com.StellarisDK.Tools.GUI;

import com.StellarisDK.Tools.FileClasses.Component.Utility;
import javafx.scene.control.TextField;

public class CompUI extends AbstractUI {

    private TextField text = new TextField("YAY");
    private Utility utility;

    public CompUI() {
        init("");
        window.setText("Component Editor");
    }

    @Override
    public void load(Object object){

    }

    @Override
    public Object save() {
        return null;
    }
}
