package StellarisDK.FileClasses.Helper;

import javafx.scene.control.TreeItem;

public class DataEntry {

    private boolean singleEntry = true;
    private boolean editable = true;
    private boolean required = false;
    private ValueTriplet entry;

    public DataEntry(String entry) {
        this.entry = new ValueTriplet<>("", entry, 0);
    }

    public DataEntry(ValueTriplet entry) {
        this.entry = entry;
    }

    public DataEntry(String entry, boolean editable) {
        this.editable = editable;
        this.entry = new ValueTriplet<>("", entry, 0);
    }

    public DataEntry(ValueTriplet entry, boolean editable) {
        this.editable = editable;
        this.entry = entry;
    }

    public DataEntry(String entry, boolean editable, boolean singleEntry) {
        this.singleEntry = singleEntry;
        this.editable = editable;
        this.entry = new ValueTriplet<>("", entry, 0);
    }

    public DataEntry(ValueTriplet entry, boolean editable, boolean singleEntry) {
        this.singleEntry = singleEntry;
        this.editable = editable;
        this.entry = entry;
    }

    public DataEntry(String entry, boolean editable, boolean singleEntry, boolean required) {
        this.singleEntry = singleEntry;
        this.editable = editable;
        this.required = required;
        this.entry = new ValueTriplet<>("", entry, 0);
    }

    public DataEntry(ValueTriplet entry, boolean editable, boolean singleEntry, boolean required) {
        this.singleEntry = singleEntry;
        this.editable = editable;
        this.required = required;
        this.entry = entry;
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

    public TreeItem getTreeEntry() {
        return new TreeItem<>(entry);
    }

    public ValueTriplet getEntry() {
        return entry;
    }

    public void setEntry(ValueTriplet entry) {
        this.entry = entry;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getBinary(){
        return (singleEntry ? 100 : 0) + (editable ? 10 : 0) + (required ? 1 : 0);
    }

    public void setBinary(int binary) {
        char[] group = Integer.toString(binary).toCharArray();
        this.singleEntry = group[0] == '1';
        this.editable = group[1] == '1';
        this.required = group[2] == '1';
    }

    public TreeItem toNestedTree() {
        TreeItem temp = new TreeItem<>(this);
        if (!singleEntry)
            temp.getChildren().add(new TreeItem<>("click_to_edit"));
        return temp;
    }


    @Override
    public String toString() {
        return entry.toString();
    }
}