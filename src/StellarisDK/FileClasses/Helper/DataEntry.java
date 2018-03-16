package StellarisDK.FileClasses.Helper;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TreeItem;

public class DataEntry<V> {

    private boolean singleEntry = true;
    private boolean editable = true;
    private boolean required = false;

    private String key;
    private String operator = "";
    private V value;
    private int order = 0;

    public Control editor;

    public void setEditor(Control editor){
        this.editor = editor;
    }

    public Node getEditor(){
        return editor;
    }

    public DataEntry(String key) {
        this.key = key;
    }

    public DataEntry(String key, int binary) {
        setBinary(binary);
        this.key = key;
    }

    public DataEntry(String key, V value, int binary) {
        setBinary(binary);
        this.key = key;
        this.operator = "=";
        this.value = value;
    }

    public DataEntry(String key, V value, int order, int binary) {
        setBinary(binary);
        this.key = key;
        this.operator = "=";
        this.value = value;
        this.order = order;
    }

    public DataEntry(String key, String operator, V value, int binary) {
        setBinary(binary);
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public DataEntry(String key, String operator, V value, int order, int binary) {
        setBinary(binary);
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isSingleEntry() {
        return singleEntry;
    }

    public void setSingleEntry(boolean singleEntry) {
        this.singleEntry = singleEntry;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getBinary() {
        return 1000 + (singleEntry ? 100 : 0) + (editable ? 10 : 0) + (required ? 1 : 0);
    }

    public void setBinary(int binary) {
        char[] group = Integer.toString(binary).toCharArray();
        this.singleEntry = group[1] == '1';
        this.editable = group[2] == '1';
        this.required = group[3] == '1';
    }

    private int getBinaryNotRequired() {
        return 1000 + (singleEntry ? 100 : 0) + (editable ? 10 : 0);
    }

    public DataEntry copy() {
        return new DataEntry<>(key, operator, value, order, getBinaryNotRequired());
    }

    public TreeItem toNestedTree() {
        TreeItem temp = new TreeItem<>(this);
        if (!singleEntry)
            temp.getChildren().add(new TreeItem<>("click_to_edit"));
        return temp;
    }

    @Override
    public String toString() {
        return (key + " " + operator + " " + (value != null ? value : "")).trim();
    }
}