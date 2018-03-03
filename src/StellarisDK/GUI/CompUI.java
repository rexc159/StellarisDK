package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.PairArrayList;
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

    public void loadCompSize() {
        compSize.getItems().add("SMALL");
        compSize.getItems().add("MEDIUM");
        compSize.getItems().add("LARGE");
        compSize.getItems().add("EXTRA_LARGE");
        compSize.getItems().add("AUX");
        compSize.getItems().add("TORPEDO");
        compSize.getItems().add("POINT_DEFENCE");
        compSize.getItems().add("PLANET_KILLER");
    }

    public void loadCompSet() {
        compGroup.getItems().removeAll();
        compGroup.getItems().add("COMBAT_COMPUTERS");
        compGroup.getItems().add("FTL_COMPONENTS");
        compGroup.getItems().add("SHIP_SENSOR_COMPONENTS");
        compGroup.getItems().add("THRUSTER_COMPONENTS");
        for (Object item : guiController.compSet.getChildren()) {
            if (((TreeItem) item).getValue().toString().contains(".txt")) {
                for (Object compSet : ((TreeItem) item).getChildren())
                    compGroup.getItems().add(((TreeItem) compSet).getValue().toString());
            }
        }
        if (obj.getKey("component_set"))
            compGroup.getSelectionModel().select(((PairArrayList) obj.getValue("component_set")).getFirstString().toUpperCase());
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
//        if(obj.getKey("size"))
//            compSize.getSelectionModel().select(((PairArrayList) obj.getValue("size")).getFirstString().toUpperCase());
//        loadCompSet();
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node instanceof TextField) {
                    obj.setValue(node.getId(),((TextField) node).getText(),true);
                }
            }
        }
        obj.setValue("component_set", compGroup.getSelectionModel().getSelectedItem(), true);
        obj.setValue("size", compSize.getSelectionModel().getSelectedItem(), true);
        System.out.println(obj.export());
        itemView.refresh();
        return null;
    }
}