package com.StellarisDK.Tools.GUI;

public class EventUI extends AbstractUI {

    public EventUI() {
        init("./FXML/eventUI.fxml");
        window.setText("Event Editor");
    }

    @Override
    public void load(Object object){

    }

    @Override
    public Object save() {
        return null;
    }
}