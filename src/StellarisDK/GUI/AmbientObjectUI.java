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

    @Override
    public Object save() {
        if (name.getText().equals("")) {
            obj.setValue(name.getId(), null, true);
        } else {
            obj.setValue(name.getId(), "\"" + name.getText() + "\"", true);
        }

        if (tooltip.getText().equals("")) {
            obj.setValue(tooltip.getId(), null, true);
        } else {
            obj.setValue(tooltip.getId(), "\"" + tooltip.getText() + "\"", true);
        }

        if (description.getText().equals("")) {
            obj.setValue(description.getId(), null, true);
        } else {
            obj.setValue(description.getId(), "\"" + description.getText() + "\"", true);
        }

        if (entity.getText().equals("")) {
            obj.setValue(entity.getId(), null, true);
        } else {
            obj.setValue(entity.getId(), "\"" + entity.getText() + "\"", true);
        }

        if (show_name.isSelected()) {
            obj.setValue(show_name.getId(), "yes", true);
        } else {
            obj.setValue(show_name.getId(), "no", true);
        }
        if (selectable.isSelected()) {
            obj.setValue(selectable.getId(), "yes", true);
        } else {
            obj.setValue(selectable.getId(), "no", true);
        }

        System.out.println(obj.export());
        itemView.refresh();
        return obj;
    }
}