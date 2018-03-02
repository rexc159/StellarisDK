package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.VPair;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import StellarisDK.GUI.AbstractUI;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * GenericData is the abstract base class for all other data type, as well as the data model of the
 * entire project. Each "Data" class contains an UI element and data stored with a LinkedHashMap.
 * Currently, this is not memory efficient as values within the HashMap are stored as ArrayLists and
 * recursively defined HashMaps regardless of data size, this combined with Java's HashMap implementation
 * led to huge memory overhead.
 * Although such overhead maybe resolved with JSONObjects, such changes will result in delay of release
 * therefore, performance related issues are pushed to after full Alpha release.
 * Alternatively, since all objects are reasonably sized Strings, storing the data as String objects,
 * and parsing it each time maybe an even better solution.
 * @author      Rex
 * @version     %I%, %G%
 * @since       0.0.1
 */
public abstract class GenericData {

    private static int tab = 0;

    protected DataMap data = new DataMap<String, ArrayList<Object>>() {
        @Override
        public String toString() {
            tab++;
            String tabs = "\r\n";
            for (int k = 0; k < tab; k++) {
                tabs += "\t";
            }
            String out = "{";
            Object[] temp = compressToPairArray();
            for (int i = 0; i < getFullSize(); i++)
                if (temp[i] != null)
                    out += temp[i].toString().replaceAll("#tabs", tabs);
            tab--;
            return out + tabs.replaceFirst("\t", "") + "}";
        }
    };

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
    }

    public GenericData(String input) {
//        raw = input;
        data = (DataMap) load(input);
        setName();
//        data.clear();
    }

    public GenericData(String input, String type) {
        this(input);
        this.type = type;
        setName();
    }

    String setName() {
        if (data.containsKey("key")) {
            name = ((PairArrayList) data.get("key")).getFirstString();
        } else if (data.containsKey("name")) {
            if (this instanceof ModDescriptor)
                name = data.get("name").toString();
            else
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

    public Object getValue(String key) {
        if(getKey(key))
            return data.get(key);
        else
            return null;
    }

    public boolean getKey(String key){
        return data.containsKey(key);
    }

    public void setValue(String key, Object value, boolean addIfAbsent) {
        if (addIfAbsent) {
            data.put(key, value);
        } else {
            data.replace(key, value);
        }
    }

    public void setData(DataMap data) {
        this.data = data;
    }

    public String export() {
        return "\r\n" + type + " = " + data;
    }

    private ArrayList sLrecursion(String input) {
        ArrayList data = new ArrayList() {
            @Override
            public String toString() {
                String out = "{ ";
                for (Object item : this) {
                    out += item + " ";
                }
                return out + "}";
            }
        };
        Matcher matcher = DataPattern.sLre.matcher(input);
        while (matcher.find()) {
            if (matcher.group(1) != null) {
                data.add(new VPair(matcher.group(1).trim(), new VPair(matcher.group(2).trim(), matcher.group(3).trim())));
            } else if (matcher.group(4) != null) {
                data.add(new VPair(matcher.group(4).trim(), new VPair(matcher.group(5).trim(), sLrecursion(matcher.group(6)))));
            } else if (matcher.group(7) != null) {
                data.add(new VPair(matcher.group(7).trim(), new VPair(matcher.group(8).trim(), matcher.group(9).trim())));
            } else if (matcher.group(10) != null) {
                data.add(new VPair(matcher.group(10).trim(), new VPair(matcher.group(11).trim(), matcher.group(12).trim())));
            } else if (matcher.group(13) != null) {
                data.add(matcher.group(13).trim());
            }
        }
        return data;
    }

    public abstract GenericData createNew();

    public Object load(String input) {
        int size = 0;
        DataMap<String, ArrayList<Object>> data = new DataMap<String, ArrayList<Object>>() {
            @Override
            public String toString() {
                tab++;
                String tabs = "\r\n";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                String out = "{";
                Object[] temp = compressToPairArray();
                for (int i = 0; i < getFullSize(); i++)
                    if (temp[i] != null)
                        out += temp[i].toString().replaceAll("#tabs", tabs);
                tab--;
                return out + tabs.replaceFirst("\t", "") + "}";
            }
        };
        Matcher matcher;

        matcher = DataPattern.newCombine.matcher(input);

        while (matcher.find()) {
            ArrayList temp;
            if (matcher.group(5) != null) {
                if (matcher.group(7).contains("{")) {
                    ValueTriplet dat = new ValueTriplet<>(matcher.group(6).trim(), sLrecursion(matcher.group(7)), size++);
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
                DataMap secMap = (DataMap) load(matcher.group(4).replaceAll("(?m)^\t", ""));
                ValueTriplet dat = new ValueTriplet<>(matcher.group(2).trim(), secMap, order);
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