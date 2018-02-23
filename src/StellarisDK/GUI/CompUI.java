package StellarisDK.GUI;

import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.cPair;
import javafx.scene.Node;
import javafx.scene.control.TextField;
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
                if(node instanceof TextField){
                    try{
                        ((TextField) node).setText(((ArrayList<Object>)obj.getValue(node.getId())).get(0).toString()
                                .replaceAll("\"",""));
                    }catch(NullPointerException e){
//                        System.out.println("Empty Key, Ignore");
                    }
                }
            }
        }
    }

    @Override
    public Object save() {
        for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
            if (node.getId() != null) {
                if(node instanceof TextField){
                    ArrayList temp;
                    String out = ((TextField) node).getText();
                    if(obj.getValue(node.getId()) != null){
                        temp = (PairArrayList)obj.getValue(node.getId());
                    }else{
                        temp = new PairArrayList();
                    }
                    try{
                        temp.set(0,((cPair)temp.get(0)).setValue(out));
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