package StellarisDK.FileClasses;

import StellarisDK.GUI.AgendaUI;
import javafx.scene.control.TreeItem;

public class Agenda extends GenericData {

    private String[] requiredSet = {"weight_modifier", "modifier"};

    public Agenda() {
        super();
        this.type = "agenda";
        ui = new AgendaUI(this);
    }

    public Agenda(String input, String type) {
        super(input, type);
        ui = new AgendaUI(this);
    }

    @Override
    public TreeItem getRequiredTreeSet(){
        TreeItem root = new TreeItem<>("agenda");
        for(String set : requiredSet){
            root.getChildren().addAll(new TreeItem<>(set));
        }
        return root;
    }

    @Override
    public GenericData createNew() {
        return new Agenda();
    }
}
