package StellarisDK.GUI;

import StellarisDK.FileClasses.Event;
import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.VPair;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;

public class EventUI extends AbstractUI {

    @FXML
    protected TreeView treeView;

    public EventUI() {
        init("FXML/eventUI.fxml");
        window.setText("Event Editor");
    }


    public EventUI(Event obj) {
        init("FXML/eventUI.fxml");
        window.setText("Component Editor");
        load(obj);
    }

    @Override
    public void load(Object object) {
        if (object instanceof String) {
            obj.load((String) object);
        } else {
            obj = (Event) object;
        }
        treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView, TreeCell>() {
            @Override
            public TreeCell call(TreeView param) {
                return new TreeCell(){
                    private TextField textField;

                    public DataMap toDataMap(){
                        return null;
                    }

                    @Override
                    public void startEdit() {
                        super.startEdit();
                        if (textField == null) {
                            textField = new TextField(((VPair) ((ValueTriplet) getItem()).getValue()).getValue().toString());
                            textField.setOnKeyReleased(event -> {
                                    if (event.getCode() == KeyCode.ENTER) {
                                        commitEdit(textField.getText());
                                    } else if (event.getCode() == KeyCode.ESCAPE) {
                                        cancelEdit();
                                    }
                                });
                        }
                        setText(null);
                        setGraphic(textField);
                        textField.selectAll();
                        System.out.println(getItem().getClass());
                    }

                    @Override
                    public void commitEdit(Object newValue) {
                        ((VPair) ((ValueTriplet) getItem()).getValue()).setValue(newValue);
                        super.commitEdit(getItem());
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        setText(getItem().toString());
                        setGraphic(getTreeItem().getGraphic());
                    }

                    @Override
                    protected void updateItem(Object item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (isEditing()) {
                                if (textField != null) {
                                    textField.setText(getItem().toString());
                                }
                                setText(null);
                                setGraphic(textField);
                            } else {
                                setText(getItem().toString());
                                setGraphic(getTreeItem().getGraphic());
                            }
                        }
                    }
                };
            }
        });
        treeView.setRoot(((Event) obj).toTreeItem());
    }

    @Override
    public Object save() {
        return null;
    }
}