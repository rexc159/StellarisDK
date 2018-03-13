package StellarisDK.GUI;

import StellarisDK.FileClasses.AmbientObject;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class AmbientObjectUI extends AbstractUI {

    @FXML
    protected TextField name;

    @FXML
    protected TextField tooltip;

    @FXML
    protected TextField description;

    @FXML
    protected TextField entity;

    @FXML
    protected CheckBox show_name;

    @FXML
    protected CheckBox selectable;

    public AmbientObjectUI(AmbientObject obj) {
        init("FXML/ambientObjectFX.fxml");
        window.setText("Ambient Object Editor");
        this.obj = obj;
    }

    @Override
    public void load() {
        name.setText(obj.getFirstValue(name.getId()));
        tooltip.setText(obj.getFirstValue(tooltip.getId()));
        description.setText(obj.getFirstValue(description.getId()));
        entity.setText(obj.getFirstValue(entity.getId()));
        if (obj.getFirstValue(show_name.getId()) != null && obj.getFirstValue(show_name.getId()).toLowerCase().equals("yes")) {
            show_name.setSelected(true);
        } else {
            show_name.setSelected(false);
        }
        if (obj.getFirstValue(selectable.getId()) != null && obj.getFirstValue(selectable.getId()).toLowerCase().equals("yes")) {
            selectable.setSelected(true);
        } else {
            selectable.setSelected(false);
        }
    }

    private void saveNode(TextField node){
        if (node.getText() == null || node.getText().equals("")) {
            obj.setValue(node.getId(), null, true, 0);
        } else {
            obj.setValue(node.getId(), node.getText(), true, 0);
        }
    }

    @Override
    public Object save() {
        if(name.getText() == null || name.getText().equals("")){
            obj.setValue(name.getId(), "Unnamed Object", true, 0);
        } else {
            obj.setValue(name.getId(), name.getText(), true, 0);
        }
        saveNode(tooltip);
        saveNode(description);
        saveNode(entity);

        if (show_name.isSelected()) {
            obj.setValue(show_name.getId(), "yes", true, 0);
        } else {
            obj.setValue(show_name.getId(), "no", true, 0);
        }
        if (selectable.isSelected()) {
            obj.setValue(selectable.getId(), "yes", true, 0);
        } else {
            obj.setValue(selectable.getId(), "no", true, 0);
        }

        System.out.println(obj.export());
        itemView.refresh();
        return obj;
    }
}