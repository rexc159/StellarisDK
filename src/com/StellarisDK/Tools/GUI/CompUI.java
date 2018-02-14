package com.StellarisDK.Tools.GUI;

import com.StellarisDK.Tools.FileClasses.Component.Utility;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CompUI extends AbstractUI {

    private Utility util;

    public CompUI() {
        init("./FXML/compUI.fxml");
        window.setText("Component Editor");
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            util.load((String) object);
            System.out.println("Loaded");
        } else {
            util = (Utility) object;
        }
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if(node instanceof TextField){
                    ((TextField) node).setText((String) util.getValue(node.getId()));
                }
            }
        }
    }

    @Override
    public Object save() {
        return null;
    }
}