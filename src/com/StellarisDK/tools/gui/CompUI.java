package com.StellarisDK.tools.gui;

import com.StellarisDK.tools.fileClasses.ship.CompUtil;
import javafx.scene.control.TextField;

import java.util.TreeMap;

public class CompUI extends AbstractUI {

    private TextField text = new TextField("YAY");
    private CompUtil compUtil;

    public CompUI(){
        init();
        title.setText("Comp Editor");
        window.setCenter(text);
    }
}
