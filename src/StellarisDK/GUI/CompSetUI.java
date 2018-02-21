package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.CompSet;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CompSetUI extends AbstractUI {

    public CompSetUI() {
        init("FXML/compSetFX.fxml");
        window.setText("Component Set Editor");
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
            System.out.println("Loaded");
        } else {
            obj = (CompSet) object;
        }
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node instanceof TextField) {
                    ((TextField) node).setText((String) obj.getValue(node.getId()));
                }
            }
        }
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node instanceof TextField) {
                    obj.setValue(node.getId(), ((TextField) node).getText(), true);
                }
            }
        }
        itemView.refresh();
        return obj;
    }
}