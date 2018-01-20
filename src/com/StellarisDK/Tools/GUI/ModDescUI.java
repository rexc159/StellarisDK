package com.StellarisDK.Tools.GUI;

import com.StellarisDK.Tools.FileClasses.ModDescriptor;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class ModDescUI extends AbstractUI {

    public ModDescriptor md;

    public ModDescUI() {
        md = new ModDescriptor();
        init("./FXML/mdFX.fxml");
        window.setText("Mod Descriptor");
    }

    @Override
    public void load(Object object) {
        if(object instanceof String){
            try {
                md.load((String) object);
                System.out.println("Loaded");
                for (Node node : ((GridPane) main.getChildren().get(0)).getChildren()) {
                    if (node.getId() != null) {
                        if (node.getId().equals("tags") || node.getId().equals("dependencies")) {
                            if (md.getValue(node.getId()) != null)
                                ((ListView) node).getItems().addAll((LinkedList) md.getValue(node.getId()));
                        } else {
                            ((TextField) node).setText((String) md.getValue(node.getId()));
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("File Not Found");
            }
        }else{
            System.out.println("Not String lol");
            md = (ModDescriptor) object;
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
                        md.setValue(node.getId(), temp);
                    }
                } else {
                    md.setValue(node.getId(), ((TextField) node).getText());
                }
            }
        }
        itemView.refresh();
        return md;
    }
}