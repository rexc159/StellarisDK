package com.StellarisDK.tools.gui;

import com.StellarisDK.tools.fileClasses.Component.Utility;
import javafx.scene.control.TextField;

public class CompUI extends AbstractUI {

    private TextField text = new TextField("YAY");
    private Utility utility;

    public CompUI(){
        init();
        title.setText("Comp Editor");
        window.setCenter(text);
    }
}
