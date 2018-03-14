package StellarisDK.FileClasses.Helper;

import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;

public class DataCell<T> extends TreeCell<T> {

    private TextField textField;
    private static TreeItem cellContent;

    public DataCell() {
        super();

        this.setOnDragDetected(event -> {
            TreeItem item = this.getTreeItem();
            if (item != null && item.getParent() != null) {
                Dragboard db = this.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboard = new ClipboardContent();
                clipboard.putString(Integer.toString(item.getParent().getChildren().indexOf(item)));
                db.setContent(clipboard);
                event.consume();
            }
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

        setCM();
    }

    @Override
    public void startEdit() {
        if ((getItem() instanceof DataEntry)) {
            if (((DataEntry) getItem()).isEditable()) {
                startEditor();
            }
        } else {
            startEditor();
        }
    }

    private void startEditor() {
        super.startEdit();
        if (getItem() instanceof DataEntry) {
            if (((DataEntry) getItem()).isRequired()) {
                textField = new TextField(((DataEntry) getItem()).getValue().toString());
            } else {
                textField = new TextField(getItem().toString());
            }
        } else {
            textField = new TextField(getItem().toString());
        }
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commitEdit((T) textField.getText());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        if (getItem() instanceof DataEntry && ((DataEntry) getItem()).isRequired()) {
            setText(((DataEntry) getItem()).getKey() + ": ");
            setContentDisplay(ContentDisplay.RIGHT);
        } else {
            setText(null);
        }
        setGraphic(textField);
        textField.selectAll();
    }

    @Override
    public void commitEdit(T newValue) {
        System.out.println(getItem().getClass());
        if (getItem() instanceof DataEntry) {
            if (((DataEntry) getItem()).isRequired()) {
                ((DataEntry) getItem()).setValue(newValue);
                super.commitEdit(getTreeItem().getValue());
            } else {
                super.commitEdit(newValue);
            }
        } else {
            super.commitEdit(newValue);
        }
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
                if (getItem() == null) {
                    setText("New Item");
                } else {
                    setText(getItem().toString().replaceAll("#tabs", ""));
                }
                setGraphic(getTreeItem().getGraphic());
            }
        }
    }

    public static TreeItem clone(TreeItem items) {
        TreeItem copy;
        if (items.getValue() instanceof DataEntry) {
            copy = new TreeItem<>(((DataEntry) items.getValue()).copy());
        } else {
            copy = new TreeItem<>(items.getValue());
        }
        for (Object item : items.getChildren()) {
            copy.getChildren().add(clone((TreeItem) item));
        }
        return copy;
    }

    private void setCM() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem createNew = new MenuItem("New..");
        createNew.setOnAction(event -> {
            if (!((DataEntry) getItem()).isSingleEntry()) {
                getTreeItem().getChildren().add(new TreeItem("Click_to_Edit"));
            }else{
                getTreeItem().getParent().getChildren().add(new TreeItem("Click_to_Edit"));
            }
        });
        MenuItem edit = new MenuItem("Rename");
        edit.setOnAction(event -> {
            if (((DataEntry) getItem()).isEditable()) {
                startEdit();
            }
        });

        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(event -> {
            if (getTreeItem().getParent() != null || !((DataEntry) getItem()).isRequired())
                getTreeItem().getParent().getChildren().remove(getTreeItem());
        });


        MenuItem cut = new MenuItem("Cut");
        cut.setOnAction(event -> {
            if(!((DataEntry) getItem()).isRequired()){
                cellContent = getTreeItem();
                getTreeItem().getParent().getChildren().remove(getTreeItem());
            }
        });

        MenuItem copy = new MenuItem("Copy");
        copy.setOnAction(event -> {
            cellContent = clone(getTreeItem());
        });

        MenuItem paste = new MenuItem("Paste");
        paste.setOnAction(event -> {
            if (getTreeItem().getParent() != null || ((DataEntry) getItem()).isSingleEntry())
                getTreeItem().getParent().getChildren().add(clone(cellContent));
            else
                getTreeItem().getChildren().add(clone(cellContent));
        });

        contextMenu.getItems().addAll(createNew, edit, cut, copy, paste, delete);
        this.setContextMenu(contextMenu);

        this.setOnContextMenuRequested(event -> {
            contextMenu.getItems().clear();
            contextMenu.getItems().addAll(createNew, edit, cut, copy, paste, delete);
            if (this.getTreeItem().getParent() == null) {
                contextMenu.getItems().removeAll(cut, copy, delete);
            }
            if ((getItem() instanceof DataEntry)) {
                if (((DataEntry) getItem()).isRequired()) {
                    contextMenu.getItems().removeAll(cut, delete);
                }
                if (!((DataEntry) getItem()).isEditable()) {
                    contextMenu.getItems().removeAll(edit);
                }
                if (((DataEntry) getItem()).isSingleEntry()) {
                    contextMenu.getItems().remove(createNew);
                }
            }
            if (cellContent == null) {
                paste.setDisable(true);
            } else {
                paste.setDisable(false);
            }
        });
    }
}