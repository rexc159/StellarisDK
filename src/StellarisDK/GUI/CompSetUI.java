package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CompSetUI extends AbstractUI {

    private TabPane tabPane;

//    private LinkedList<Component> compList = new LinkedList<>();

    public CompSetUI(CompSet obj) {
        init("FXML/compSetFX.fxml");
        tabPane = (TabPane) main.getChildren().get(2);
        window.setText("Component Set Editor");
        load(obj);
    }

    public void addComp(Component comp){
//        compList.add(comp);
        Tab tab = new Tab(comp.toString());
        tab.setContent(comp.ui.main);
        tab.getContent().setStyle("-fx-background-color: #FFFFFF;");
        tabPane.getTabs().add(tab);
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
        System.out.println(obj.export());
        itemView.refresh();
        return obj;
    }
}