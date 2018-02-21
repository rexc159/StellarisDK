package StellarisDK.GUI;

import StellarisDK.FileClasses.ModDescriptor;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;

import java.util.Collections;
import java.util.LinkedList;

public class ModDescUI extends AbstractUI {

    public ModDescUI() {
        init("FXML/mdFX.fxml");
        window.setText("Mod Descriptor");
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
            System.out.println("Loaded");
        } else {
            obj = (ModDescriptor) object;
        }
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node.getId().equals("tags") || node.getId().equals("dependencies")) {
                    ((ListView) node).setEditable(true);
                    ((ListView) node).setCellFactory(TextFieldListCell.forListView());
                    ((ListView) node).setOnEditCommit(new EventHandler<ListView.EditEvent>() {
                        @Override
                        public void handle(ListView.EditEvent event) {
                            if (event.getNewValue().toString().length() != 0)
                                ((ListView) node).getItems().set(event.getIndex(), event.getNewValue());
                            else
                                ((ListView) node).getItems().remove(event.getIndex());
                        }
                    });
                    node.setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2) {
                            Node cell = event.getPickResult().getIntersectedNode();
                            if (cell instanceof ListView || ((ListCell) cell).getItem() == null) {
                                ((ListView) node).getItems().add(((ListView) node).getItems().size(), new String());
                            }
                        }
                    });
                    if (obj.getValue(node.getId()) != null)
                        ((ListView) node).getItems().setAll((LinkedList) obj.getValue(node.getId()));
                } else {
                    ((TextField) node).setText((String) obj.getValue(node.getId()));
                }
            }
        }
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if (node.getId().equals("tags") || node.getId().equals("dependencies")) {
                    LinkedList<Object> temp = new LinkedList<Object>() {
                        @Override
                        public String toString() {
                            String out = "{\n";
                            for (Object i : this) {
                                out += "\t\"" + ((String) i).trim() + "\"\n";
                            }
                            return out + "}\n";
                        }
                    };
                    if (((ListView) node).getItems().toArray().length != 0) {
                        Collections.addAll(temp, ((ListView) node).getItems().toArray());
                        obj.setValue(node.getId(), temp);
                    }
                } else {
                    obj.setValue(node.getId(), ((TextField) node).getText());
                }
            }
        }
        itemView.refresh();
        return obj;
    }
}