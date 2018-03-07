package StellarisDK.GUI;

import StellarisDK.FileClasses.Event;
import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.VPair;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
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
                TreeCell<Object> cell = new TreeCell<Object>() {
                    private TextField textField;

                    public DataMap toDataMap() {
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

                cell.setOnDragDetected(event -> {
                    TreeItem item = cell.getTreeItem();
                    Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent clipboard = new ClipboardContent();
                    clipboard.putString(Integer.toString(item.getParent().getChildren().indexOf(item)));
                    db.setContent(clipboard);
                    event.consume();
                });

                cell.setOnDragOver(event -> {
                    if (event.getGestureSource() != cell) {
                        TreeItem source;
                        TreeItem item = cell.getTreeItem();
                        if (event.getGestureSource() instanceof LabeledText) {
                            source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
                        } else {
                            source = ((TreeCell) event.getGestureSource()).getTreeItem();
                        }
                        if(source.getParent().equals(item.getParent())){
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    }
                    event.consume();
                });

                cell.setOnDragEntered(event -> {
                    if (event.getGestureSource() != cell) {
                        cell.updateSelected(true);
                    }
                    event.consume();
                });

                cell.setOnDragExited(event -> {
                    cell.updateSelected(false);
                    event.consume();
                });

                cell.setOnDragDropped(event -> {
                    System.out.println("FROM: " + event.getGestureSource());
                    System.out.println("TO: " + cell);
                    TreeItem target = cell.getTreeItem();
                    TreeItem source;
                    if (event.getGestureSource() instanceof LabeledText) {
                        source = ((TreeCell) ((Node) event.getGestureSource()).getParent()).getTreeItem();
                    } else {
                        source = ((TreeCell) event.getGestureSource()).getTreeItem();
                    }
                    int index = target.getParent().getChildren().indexOf(target);
                    source.getParent().getChildren().remove(source);
                    target.getParent().getChildren().add(index, source);
                    event.setDropCompleted(true);
                    event.consume();
                });

                cell.setOnDragDone(event -> {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        System.out.println("Done");
                    }
                    event.consume();
                });

                return cell;
            }
        });
        treeView.setRoot(((Event) obj).toTreeItem());
    }

    @Override
    public Object save() {
        return null;
    }
}