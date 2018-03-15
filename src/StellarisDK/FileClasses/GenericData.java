package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.EntryArrayList;
import StellarisDK.FileClasses.Helper.StellarisColor;
import StellarisDK.GUI.AbstractUI;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * GenericData is the abstract base class for all other data type, as well as the data model of the
 * entire project. Each "Data" class contains an UI element and data stored with a LinkedHashMap.
 * @author      Rex
 * @version     %I%, %G%
 * @since       0.0.1
 */
public abstract class GenericData {

    public static int tab = 0;

    protected DataMap data;

    protected String name = "Empty";

    public AbstractUI ui;

    protected ArrayList<DataEntry> dataEntries;

    protected DataEntry type;

    public GenericData() {
        setDataEntries();
        name = "New Item (Click to Edit)";
        data = new DataMap();
    }

    public GenericData(String input) {
        setDataEntries();
        data = (DataMap) load(input);
    }

    public GenericData(String input, String type) {
        this(input);
        this.type = new DataEntry(type);
        setName();
    }

    private String setName() {
        if (this instanceof ModDescriptor) {
            if (data.containsKey("name"))
                name = data.get("name").toString();
        } else if (data.containsKey("key")) {
            name = ((EntryArrayList) data.get("key")).getFirstString();
        } else if (data.containsKey("name")) {
            name = ((EntryArrayList) data.get("name")).getFirstString();
        } else if (data.containsKey("id")) {
            name = ((EntryArrayList) data.get("id")).getFirstString();
        } else if (data.containsKey("event")) {
            name = ((EntryArrayList) data.get("event")).getFirstString();
        } else {
            name = type.toString();
        }
        return name;
    }

    public Object getFirstValue(String key) {
        if (getKey(key))
            return ((EntryArrayList) data.get(key)).get(0);
        else
            return null;
    }

    public String getFirstString(String key) {
        if (getKey(key))
            return ((EntryArrayList) data.get(key)).getFirstString();
        else
            return null;
    }

    public Object getValue(String key) {
        if (getKey(key))
            return data.get(key);
        else
            return null;
    }

    public void lockEntries() {
        for (DataEntry entry : dataEntries) {
            setEntryLock(entry.getKey(), entry.getBinary());
        }
    }

    private void setEntryLock(String key, int binary) {
        if (getKey(key)) {
            ((EntryArrayList<DataEntry>) data.get(key)).get(0).setBinary(binary);
        }
    }

    public boolean getKey(String key) {
        return data.containsKey(key);
    }

    public void setValue(String key, Object value, boolean addIfAbsent, int index) throws IndexOutOfBoundsException {
        if (addIfAbsent && !data.containsKey(key)) {
            EntryArrayList<DataEntry> temp = new EntryArrayList<>();
            if (value instanceof DataEntry) {
                temp.add((DataEntry) value);
            } else {
                temp.add(new DataEntry<>(key, value, data.size(), 1110));
            }
            data.put(key, temp);
        } else {
            ((DataEntry) ((EntryArrayList) data.get(key)).get(index)).setValue(value);
        }
    }

    public void removeValue(String key) {
        if (data.containsKey(key)) {
            data.remove(key);
        }
    }

    public void setData(DataMap data) {
        this.data = data;
    }

    public void setType(String type) {
        this.type = this.type == null ? new DataEntry(type) : new DataEntry(type, this.type.getBinary());
    }

    public void setType(DataEntry type) {
        this.type = type;
    }

    public int getSize() {
        return data.getFullSize();
    }

    public GenericData clone() {
        GenericData copy = createNew();
        copy.setType(type);
        copy.setData(data);
        return copy;
    }

    public void setDataEntries(){
        dataEntries = new ArrayList<>();
    }

    public TreeItem getRequiredTreeSet() {
        TreeItem root = new TreeItem<>(type);
        for (DataEntry entry : dataEntries) {
            TreeItem temp = new TreeItem<>(entry);
            if(!entry.isSingleEntry()){
                temp.getChildren().add(new TreeItem<>("click_to_edit"));
            }
            root.getChildren().add(temp);
        }
        return root;
    }

    public String export() {
        return "\r\n" + type + " = " + data;
    }

    public abstract GenericData createNew();

    public TreeItem toTreeItem() {
        return data.toTreeItem(type);
    }

    private EntryArrayList sLrecursion(String input) {
        EntryArrayList data = new EntryArrayList() {
            @Override
            public String toString() {
                if (size() != 0 && this.get(0) instanceof DataEntry) {
                    tab++;
                    String tabs = "\r\n";
                    for (int k = 0; k < tab; k++) {
                        tabs += "\t";
                    }
                    String out = "{";
                    for (Object item : this) {
                        out += tabs + item;
                    }
                    tab--;
                    return out + tabs.replaceFirst("\t", "") + "}";
                } else {
                    String out = "{ ";
                    for (Object item : this) {
                        out += item + " ";
                    }
                    return out + "}";
                }
            }
        };
        Matcher matcher = DataPattern.sLre.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                Matcher color = DataPattern.color.matcher(matcher.group(3));
                if (color.find()) {
                    try {
                        data.add(new DataEntry<>(matcher.group(1).trim(), matcher.group(2).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim(), color.group(5).trim()), 1110));
                    } catch (NullPointerException e) {
                        data.add(new DataEntry<>(matcher.group(1).trim(), matcher.group(2).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim()), 1110));
                    }
                }
            } else if (matcher.group(4) != null) {
                data.add(new DataEntry<>(matcher.group(4).trim(), matcher.group(5).trim(), sLrecursion(matcher.group(6)), 1010));
            } else if (matcher.group(7) != null) {
                data.add(new DataEntry<>(matcher.group(7).trim(), matcher.group(8).trim(), matcher.group(9).trim(), 1110));
            } else if (matcher.group(10) != null) {
                data.add(new DataEntry<>(matcher.group(10).trim(), matcher.group(11).trim(), matcher.group(12).trim(), 1110));
            } else if (matcher.group(13) != null) {
                data.add(matcher.group(13).trim());
            }
        }
        return data;
    }

    public Object load(String input) {
        int size = 0;
        DataMap<EntryArrayList<DataEntry>> data = new DataMap<>();
        Matcher matcher;

        matcher = DataPattern.newCombine.matcher(input);

        while (matcher.find()) {
            EntryArrayList<DataEntry> temp;
            if (matcher.group(5) != null) {
                if (matcher.group(7).contains("{")) {
                    DataEntry entry;
                    Matcher color = DataPattern.color.matcher(matcher.group(7));
                    if (color.find()) {
                        try {
                            entry = new DataEntry<>(matcher.group(5).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim(), color.group(5).trim()), size++ , 1110);
                        } catch (NullPointerException e) {
                            entry = new DataEntry<>(matcher.group(5).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim()), size++ , 1110);
                        }
                    } else {
                        entry = new DataEntry<>(matcher.group(5).trim(), sLrecursion(matcher.group(7).trim()), size++, 1010);
                    }
                    if (data.containsKey(matcher.group(5))) {
                        data.get(matcher.group(5).trim()).add(entry);
                    } else {
                        temp = new EntryArrayList<>();
                        temp.add(entry);
                        data.put(matcher.group(5).trim(), temp);
                    }
                } else {
                    DataEntry entry = new DataEntry<>(matcher.group(5).trim(), matcher.group(6).trim(), matcher.group(7).trim(), size++, 1110);
                    if (data.containsKey(matcher.group(5).trim())) {
                        data.get(matcher.group(5).trim()).add(entry);
                    } else {
                        temp = new EntryArrayList<>();
                        temp.add(entry);
                        data.put(matcher.group(5).trim(), temp);
                    }
                }
            } else if (matcher.group(1) != null) {
                temp = new EntryArrayList<>();
                int order = size++;
                DataMap secMap;
                DataEntry entry;
                if (!matcher.group(4).contains("=")) {
                    entry = new DataEntry<>(matcher.group(1).trim(), sLrecursion(matcher.group(4).trim().replaceAll("[\\t\\n\\r]", " ")), order, 1010);
                } else {
                    secMap = (DataMap) load(matcher.group(4).replaceAll("(?m)^\t", ""));
                    entry = new DataEntry<>(matcher.group(1).trim(), secMap, order, 1010);
                }
                temp.add(entry);
                if (data.containsKey(matcher.group(1).trim())) {
                    data.get(matcher.group(1).trim()).add(entry);
                } else {
                    data.put(matcher.group(1).trim(), temp);
                }
            }
        }
        return data;
    }

    @Override
    public String toString() {
        return setName();
    }
}