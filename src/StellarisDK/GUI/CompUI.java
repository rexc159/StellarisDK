package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.cPair;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;

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
                    ((TextField) node).setText(((LinkedList<Object>)util.getValue(node.getId())).getFirst().toString()
                            .replaceAll("\"",""));
                }
            }
        }
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if(node instanceof TextField){
                    LinkedList temp = ((LinkedList<Object>)util.getValue(node.getId()));
                    String out = ((TextField) node).getText();
                    if(temp.getFirst().toString().contains("\"")){
                        out = "\"" + out +"\"";
                    }
                    temp.set(0,((cPair)temp.getFirst()).setValue(out));
                }
            }
        }
        System.out.println(util.export());
        return null;
    }
}