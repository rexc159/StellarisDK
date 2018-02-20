package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CompUI extends AbstractUI {

    private Component util;

    public CompUI() {
        init("FXML/compUI.fxml");
        window.setText("Component Editor");
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            util.load((String) object);
            System.out.println("Loaded");
        } else {
            util = (Component) object;
        }
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if(node instanceof TextField){
                    ((TextField) node).setText(util.getValue(node.getId()).toString());
                }
            }
        }
    }

    @Override
    public Object save() {
        return null;
    }
}