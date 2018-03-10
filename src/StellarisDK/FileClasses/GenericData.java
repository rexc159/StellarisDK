package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.*;
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

    private static int tab = 0;

    protected DataMap data;

    protected String name = "Empty";

    public AbstractUI ui;

    /*
        Should be enum within extended classes
        but since it needs to be referenced later on anyway,
        it maybe better to just store it with a String object instead
     */
    protected String type;

    public GenericData() {
        name = "New Item (Click to Edit)";
        data = new DataMap<String, ArrayList<Object>>();
    }

    public GenericData(String input) {
        data = (DataMap) load(input);
        setName();
    }

    public GenericData(String input, String type) {
        this(input);
        this.type = type;
        setName();
    }

    protected String setName() {
        if (this instanceof ModDescriptor) {
            if (data.containsKey("name"))
                name = data.get("name").toString();
        } else if (data.containsKey("key")) {
            name = ((PairArrayList) data.get("key")).getFirstString();
        } else if (data.containsKey("name")) {
            name = ((PairArrayList) data.get("name")).getFirstString();
        } else if (data.containsKey("id")) {
            name = ((PairArrayList) data.get("id")).getFirstString();
        } else if (data.containsKey("event")) {
            name = ((PairArrayList) data.get("event")).getFirstString();
        } else {
            name = type;
        }
        return name;
    }

    public String getFirstValue(String key) {
        if (getKey(key))
            return ((PairArrayList) data.get(key)).getFirstString();
        else
            return null;
    }

    public Object getValue(String key) {
        if (getKey(key))
            return data.get(key);
        else
            return null;
    }

    public boolean getKey(String key) {
        return data.containsKey(key);
    }

    public void setValue(String key, Object value, boolean addIfAbsent) {
        if (addIfAbsent && !data.containsKey(key)) {
            PairArrayList temp = new PairArrayList();
            if (value instanceof ValueTriplet) {
                temp.add(value);
            } else {
                temp.add(new ValueTriplet<>("=", value, data.size()));
            }
            data.put(key, temp);
        } else {
            ((ValueTriplet) ((PairArrayList) data.get(key)).get(0)).setValue(value);
        }
    }

    public void setData(DataMap data) {
        this.data = data;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getSize(){
        return data.getFullSize();
    }

    public abstract TreeItem getRequiredTreeSet();

    public static int getTab() {
        return tab;
    }

    public static void changeTab(boolean inc) {
        if (inc) {
            tab++;
        } else {
            tab--;
        }
    }

    public String export() {
        return "\r\n" + type + " = " + data;
    }

    private PairArrayList sLrecursion(String input) {
        PairArrayList data = new PairArrayList() {
            @Override
            public String toString() {
                if (size() != 0 && this.get(0) instanceof VPair) {
                    GenericData.changeTab(true);
                    String tabs = "\r\n";
                    for (int k = 0; k < GenericData.getTab(); k++) {
                        tabs += "\t";
                    }
                    String out = "{";
                    for (Object item : this) {
                        out += tabs + item;
                    }
                    GenericData.changeTab(false);
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
                        data.add(new VPair<>(matcher.group(1).trim(), new VPair<>(matcher.group(2).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim(), color.group(5).trim()))));
                    } catch (NullPointerException e) {
                        data.add(new VPair<>(matcher.group(1).trim(), new VPair<>(matcher.group(2).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim()))));
                    }
                }
            } else if (matcher.group(4) != null) {
                data.add(new VPair<>(matcher.group(4).trim(), new VPair<>(matcher.group(5).trim(), sLrecursion(matcher.group(6)))));
            } else if (matcher.group(7) != null) {
                data.add(new VPair<>(matcher.group(7).trim(), new VPair<>(matcher.group(8).trim(), matcher.group(9).trim())));
            } else if (matcher.group(10) != null) {
                data.add(new VPair<>(matcher.group(10).trim(), new VPair<>(matcher.group(11).trim(), matcher.group(12).trim())));
            } else if (matcher.group(13) != null) {
                data.add(matcher.group(13).trim());
            }
        }
        return data;
    }

    public abstract GenericData createNew();

    public TreeItem toTreeItem() {
        return data.toTreeItem(type);
    }

    public Object load(String input) {
        int size = 0;
        DataMap<String, ArrayList<Object>> data = new DataMap<>();
        Matcher matcher;

        matcher = DataPattern.newCombine.matcher(input);

        while (matcher.find()) {
            ArrayList temp;
            if (matcher.group(5) != null) {
                if (matcher.group(7).contains("{")) {
                    ValueTriplet dat;
                    Matcher color = DataPattern.color.matcher(matcher.group(7));
                    if (color.find()) {
                        try {
                            dat = new ValueTriplet<>(matcher.group(6).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim(), color.group(5).trim()), size++);
                        } catch (NullPointerException e) {
                            dat = new ValueTriplet<>(matcher.group(6).trim(), new StellarisColor(color.group(1).trim(), color.group(2).trim(), color.group(3).trim(), color.group(4).trim()), size++);
                        }
                    } else {
                        dat = new ValueTriplet<>(matcher.group(6).trim(), sLrecursion(matcher.group(7).trim()), size++);
                    }
                    if (data.containsKey(matcher.group(5))) {
                        data.get(matcher.group(5).trim()).add(dat);
                    } else {
                        temp = new PairArrayList();
                        temp.add(dat);
                        data.put(matcher.group(5).trim(), temp);
                    }
                } else {
                    ValueTriplet dat = new ValueTriplet<>(matcher.group(6).trim(), matcher.group(7).trim(), size++);
                    if (data.containsKey(matcher.group(5).trim())) {
                        data.get(matcher.group(5).trim()).add(dat);
                    } else {
                        temp = new PairArrayList();
                        temp.add(dat);
                        data.put(matcher.group(5).trim(), temp);
                    }
                }
            } else if (matcher.group(1) != null) {
                temp = new PairArrayList();
                int order = size++;
                DataMap secMap;
                ValueTriplet dat;
                if (!matcher.group(4).contains("=")) {
                    dat = new ValueTriplet<>(matcher.group(2).trim(), sLrecursion(matcher.group(4).trim().replaceAll("[\\t\\n\\r]", " ")), order);
                } else {
                    secMap = (DataMap) load(matcher.group(4).replaceAll("(?m)^\t", ""));
                    dat = new ValueTriplet<>(matcher.group(2).trim(), secMap, order);
                }
                temp.add(dat);
                if (data.containsKey(matcher.group(1).trim())) {
                    data.get(matcher.group(1).trim()).add(dat);
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