package com.StellarisDK.Tools.GUI;

import com.StellarisDK.Tools.FileClasses.Component.Utility;

public class CompUI extends AbstractUI {

    private Utility utility;

    public CompUI() {
        init("./FXML/compUI.fxml");
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
