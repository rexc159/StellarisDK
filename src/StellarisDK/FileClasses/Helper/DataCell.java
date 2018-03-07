package StellarisDK.FileClasses.Helper;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;

public class DataCell<T> extends TreeCell<T> {

    private TextField textField;

    public DataCell() {
        super();
        this.setOnDragDetected(event -> {
            TreeItem item = this.getTreeItem();
            Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboard = new ClipboardContent();
            clipboard.putString(Integer.toString(item.getParent().getChildren().indexOf(item)));
            db.setContent(clipboard);
            event.consume();
        });

        this.setOnDragOver(event -> {
            if (event.getGestureSource() != this) {
                TreeItem source;
                TreeItem item = this.getTreeItem();
                if (event.getGestureSource() instanceof LabeledText) {
                    source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
                } else {
                    source = ((TreeCell) event.getGestureSource()).getTreeItem();
                }
                if (source.getParent().equals(item.getParent())) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }
            event.consume();
        });

        this.setOnDragEntered(event -> {
            if (event.getGestureSource() != this) {
                this.updateSelected(true);
            }
            event.consume();
        });

        this.setOnDragExited(event -> {
            this.updateSelected(false);
            event.consume();
        });

        this.setOnDragDropped(event -> {
            System.out.println("FROM: " + event.getGestureSource());
            System.out.println("TO: " + this);
            TreeItem target = this.getTreeItem();
            TreeItem source;
            if (event.getGestureSource() instanceof LabeledText) {
                source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
            } else {
                source = ((TreeCell) event.getGestureSource()).getTreeItem();
            }
            int index = target.getParent().getChildren().indexOf(target);
            source.getParent().getChildren().remove(source);
            if (index == target.getParent().getChildren().size()) {
                target.getParent().getChildren().add(source);
            } else {
                target.getParent().getChildren().add(index, source);
            }
            event.setDropCompleted(true);
            event.consume();
        });

        this.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                System.out.println("Done");
            }
            event.consume();
        });
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
        setText(getItem().toString().replaceAll("#tabs", ""));
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getItem().toString().replaceAll("#tabs", ""));
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getItem().toString().replaceAll("#tabs", ""));
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }
}
