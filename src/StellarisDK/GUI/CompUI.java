package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class CompUI extends AbstractUI {

    @FXML
    ChoiceBox compGroup;

    @FXML
    ChoiceBox compSize;

    public CompUI(Component obj) {
        init("FXML/compFX.fxml");
        window.setText("Component Editor");
        loadCompSize();
        load(obj);
    }

    public void loadCompSize(){
        compSize.getItems().add("SMALL");
        compSize.getItems().add("MEDIUM");
        compSize.getItems().add("LARGE");
        compSize.getItems().add("EXTRA_LARGE");
        compSize.getItems().add("TORPEDO");
        compSize.getItems().add("PLANET_KILLER");
        compSize.getItems().add("POINT_DEFENCE");
    }

    public void loadCompSet() {
        compGroup.getItems().removeAll();
        for (Object item : guiController.compSet.getChildren()) {
            if (((TreeItem) item).getValue().toString().contains(".txt")) {
                for (Object compSet : ((TreeItem) item).getChildren())
                    compGroup.getItems().add(((TreeItem) compSet).getValue().toString());
            }
        }
        if(obj.getKey("component_set"))
            compGroup.getSelectionModel().select(((PairArrayList) obj.getValue("component_set")).getFirstString());
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
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
                }
            }
        }
        compSize.getSelectionModel().select(((PairArrayList)obj.getValue("size")).getFirstString().toUpperCase());
        loadCompSet();
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
        itemView.refresh();
        return null;
    }
}