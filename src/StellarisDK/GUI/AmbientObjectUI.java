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
        name.setText(obj.getFirstString(name.getId()));
        tooltip.setText(obj.getFirstString(tooltip.getId()));
        description.setText(obj.getFirstString(description.getId()));
        entity.setText(obj.getFirstString(entity.getId()));
        if (obj.getFirstString(show_name.getId()) != null && obj.getFirstString(show_name.getId()).toLowerCase().equals("yes")) {
            show_name.setSelected(true);
        } else {
            show_name.setSelected(false);
        }
        if (obj.getFirstString(selectable.getId()) != null && obj.getFirstString(selectable.getId()).toLowerCase().equals("yes")) {
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