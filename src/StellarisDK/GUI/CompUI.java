package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CompUI extends AbstractUI {

    public CompUI(Component obj) {
        init("FXML/compFX.fxml");
        window.setText("Component Editor");
        load(obj);
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
            System.out.println("Loaded");
        } else {
            obj = (Component) object;
        }
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node instanceof TextField) {
                    try {
                        ((TextField) node).setText(((ArrayList<Object>) obj.getValue(node.getId())).get(0).toString()
                                .replaceAll("\"", ""));
                    } catch (NullPointerException e) {
//                        System.out.println("Empty Key, Ignore");
                    }
                }else if (node instanceof ChoiceBox && node.getId().equals("component_set")){
                    for(Object item: guiController.compSet.getChildren()){
                        if(((TreeItem) item).getValue().toString().contains(".txt")){
                            for(Object compSet : ((TreeItem) item).getChildren())
                                ((ChoiceBox) node).getItems().add(((TreeItem)compSet).getValue().toString());
                        }
                    }
                    System.out.println(obj.getValue("component_set").toString());
                    ((ChoiceBox) node).getSelectionModel().select(((PairArrayList)obj.getValue("component_set")).getFirstString());
                }
            }
        }
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node instanceof TextField) {
                    PairArrayList temp;
                    String out = ((TextField) node).getText();
                    if (obj.getValue(node.getId()) != null) {
                        temp = (PairArrayList) obj.getValue(node.getId());
                    } else {
                        temp = new PairArrayList();
                        obj.setValue(node.getId(), temp, true);
                    }
                    try {
                        temp.set(0, ((ValueTriplet) temp.get(0)).setValue(out));
                    } catch (IndexOutOfBoundsException e) {
                        temp.add(new ValueTriplet<>("=", out, obj.incSize()));
                    }
                }else if (node instanceof ChoiceBox && node.getId().equals("component_set")){
                    ((ChoiceBox) node).getSelectionModel().getSelectedItem();
                }
            }
        }
        System.out.println(obj.export());
        return null;
    }
}