package StellarisDK.GUI;

import StellarisDK.FileClasses.Attitude;
import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.EntryArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class AttitudeUI extends AbstractUI {

    @FXML
    private TextField type;

    @FXML
    private CheckBox attack;

    @FXML
    private CheckBox weaken;

    @FXML
    private CheckBox alliance;

    @FXML
    private CheckBox vassalize;

    @FXML
    private CheckBox trade;

    @FXML
    private CheckBox coexist;

    public AttitudeUI(Attitude obj) {
        init("FXML/attitudeFX.fxml");
        window.setText("Attitude Editor");
        this.obj = obj;
    }

    @Override
    public void load() {
        type.setText(obj.getFirstString(type.getId()));
        DataMap temp = (DataMap)((DataEntry)obj.getFirstValue("behaviour")).getValue();
        if (temp.get(attack.getId()) != null && ((DataEntry)temp.getFirstValue(attack.getId())).getValue().toString().toLowerCase().equals("yes")) {
            attack.setSelected(true);
        } else {
            attack.setSelected(false);
        }
        if (temp.get(weaken.getId()) != null && ((DataEntry)temp.getFirstValue(weaken.getId())).getValue().toString().toLowerCase().equals("yes")) {
            weaken.setSelected(true);
        } else {
            weaken.setSelected(false);
        }
        if (temp.get(alliance.getId()) != null && ((DataEntry)temp.getFirstValue(alliance.getId())).getValue().toString().toLowerCase().equals("yes")) {
            alliance.setSelected(true);
        } else {
            alliance.setSelected(false);
        }
        if (temp.get(vassalize.getId()) != null && ((DataEntry)temp.getFirstValue(vassalize.getId())).getValue().toString().toLowerCase().equals("yes")) {
            vassalize.setSelected(true);
        } else {
            vassalize.setSelected(false);
        }
        if (temp.get(trade.getId()) != null && ((DataEntry)temp.getFirstValue(trade.getId())).getValue().toString().toLowerCase().equals("yes")) {
            trade.setSelected(true);
        } else {
            trade.setSelected(false);
        }
        if (temp.get(coexist.getId()) != null && ((DataEntry)temp.getFirstValue(coexist.getId())).getValue().toString().toLowerCase().equals("yes")) {
            coexist.setSelected(true);
        } else {
            coexist.setSelected(false);
        }
    }

    @Override
    public Object save() {
        DataMap temp = (DataMap)((DataEntry)obj.getFirstValue("behaviour")).getValue();
        if(type.getText() == null || type.getText().equals("")){
            obj.setValue(type.getId(), "Unnamed Object", true, 0);
        } else {
            obj.setValue(type.getId(), type.getText(), true, 0);
        }
        int size = 0;
        if (attack.isSelected()) {
            temp.put(attack.getId(), new EntryArrayList<>(new DataEntry<>(attack.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(attack.getId());
        }
        if (weaken.isSelected()) {
            temp.put(weaken.getId(), new EntryArrayList<>(new DataEntry<>(weaken.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(weaken.getId());
        }
        if (alliance.isSelected()) {
            temp.put(alliance.getId(), new EntryArrayList<>(new DataEntry<>(alliance.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(alliance.getId());
        }
        if (vassalize.isSelected()) {
            temp.put(vassalize.getId(), new EntryArrayList<>(new DataEntry<>(vassalize.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(vassalize.getId());
        }
        if (trade.isSelected()) {
            temp.put(trade.getId(), new EntryArrayList<>(new DataEntry<>(trade.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(trade.getId());
        }
        if (coexist.isSelected()) {
            temp.put(coexist.getId(), new EntryArrayList<>(new DataEntry<>(coexist.getId(), "yes", size++, 1110)));
        }else{
            temp.remove(coexist.getId());
        }

        System.out.println(obj.export());
        itemView.refresh();
        return obj;
    }
}