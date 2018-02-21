package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.PairLinkedList;
import StellarisDK.FileClasses.Helper.cPair;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;

public class CompUI extends AbstractUI {

    public CompUI() {
        init("FXML/compUI.fxml");
        window.setText("Component Editor");
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
                if(node instanceof TextField){
                    ((TextField) node).setText(((LinkedList<Object>)obj.getValue(node.getId())).getFirst().toString()
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
                    LinkedList temp;
                    String out = ((TextField) node).getText();
                    if(obj.getValue(node.getId()) != null){
                        temp = (PairLinkedList)obj.getValue(node.getId());
                    }else{
                        temp = new PairLinkedList();
                    }
                    try{
                        temp.set(0,((cPair)temp.getFirst()).setValue(out));
                    }catch (IndexOutOfBoundsException e){
                        temp.add(new cPair(node.getId() , out));
                    }
                }
            }
        }
        System.out.println(obj.export());
        return null;
    }
}