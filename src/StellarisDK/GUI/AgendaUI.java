package StellarisDK.GUI;

import StellarisDK.FileClasses.Agenda;
import StellarisDK.FileClasses.Helper.DataCell;

public class AgendaUI extends AbstractUI {

    public AgendaUI(Agenda obj) {
        init("FXML/agendaFX.fxml");
        window.setText("Agenda Editor");
        this.obj = obj;
    }

    @Override
    public void load() {
        treeView.setEditable(true);
        treeView.setCellFactory(param -> new DataCell());
        if(obj.getSize() != 0){
            treeView.setRoot(obj.toTreeItem());
        }else{
            treeView.setRoot(obj.getRequiredTreeSet());
        }
    }
}
