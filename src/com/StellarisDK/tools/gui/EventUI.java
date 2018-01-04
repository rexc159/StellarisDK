package com.StellarisDK.tools.gui;

import javafx.scene.control.Label;

public class EventUI extends AbstractUI {

    public EventUI(){
        init();
        title.setText("Event Editor");
        window.setCenter(new Label("Different Thing"));
    }
}
