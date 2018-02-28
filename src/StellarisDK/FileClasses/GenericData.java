package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataMap;
import StellarisDK.FileClasses.Helper.PairArrayList;
import StellarisDK.FileClasses.Helper.VPair;
import StellarisDK.FileClasses.Helper.ValueTriplet;
import StellarisDK.GUI.AbstractUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private static int tab = 1;

    private int size = 0;

    protected HashMap<String, Object> data = new HashMap<>();

    protected DataMap testData = new DataMap();

    protected String name = "Empty";

    public AbstractUI ui;

    /*
        Should be enum within extended classes
        but since it needs to be referenced later on anyway,
        it maybe better to just store it with a String object instead
     */
    protected String type;

    public GenericData() {
    }

    public GenericData(String input) {
//        raw = input;
        data = (LinkedHashMap) load(input);
        setName();
//        data.clear();
    }

    public GenericData(String input, String type) {
        this(input);
        this.type = type;
        setName();
    }

    private void setName() {
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
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public void setValue(String key, Object value, boolean addIfAbsent) {
        if (addIfAbsent) {
            data.put(key, value);
        } else {
            data.replace(key, value);
        }
    }

    public void setData(HashMap data){
        this.data = data;
    }

    public void setTestData(DataMap data){
        this.testData = data;
    }

    public String exportVer(){
        Object[] temp = testData.compressToPairArray(size);
        for(int i=0; i< size; i++)
            if(temp[i] != null)
                System.out.println(temp[i]);
        return "";
    }

    private Object sLrecursion(String input){
        Object data = null;
        Matcher sLrecursion = DataPattern.sLre.matcher(input);
        while(sLrecursion.find()){
            if(sLrecursion.group(1) != null){
                data = null;
            }else if (sLrecursion.group(4) != null){
                data = null;
            }else if (sLrecursion.group(7) != null){
                data = null;
            }else if (sLrecursion.group(10) != null){
                data = null;
            }else if(sLrecursion.group(13) != null){
                data = null;
            }else{
                data = null;
            }
        }
        return data;
    }

    public DataMap verLoad(String input) {
        DataMap data = new DataMap() {
            @Override
            public String toString() {
                tab++;
                String tabs = "\r\n";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                String out = "";
                Object[] temp = compressToPairArray(size);
                for(int i=0;i<size;i++)
                    if(temp[i] != null)
                        out += temp[i].toString().replaceAll("#test", tabs);
//                tab++;
//                String tabs = "\r\n";
//                for (int k = 0; k < tab; k++) {
//                    tabs += "\t";
//                }
//                for (String i : keySet()) {
//                    out += get(i).toString().replaceAll("#key", tabs + i);
//                }
//                tab--;
                tab--;
                return out;
            }
        };
        Matcher matcher;

        matcher = DataPattern.newCombine.matcher(input);

        while (matcher.find()) {
            ArrayList temp;
            if (matcher.group(5) != null) {
                if (matcher.group(7).contains("{")) {
                    temp = new ArrayList<Object>() {
                        @Override
                        public String toString() {
                            String out = "#key = {";
                            for (Object sub : this) {
                                out += sub.toString().replaceAll("(?m)^[\r\n\\t]+", " ");
                            }
                            return out + " }";
                        }
                    };

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
                temp = new ArrayList<Object>() {
                    @Override
                    public String toString() {
                        // Fix repeated here
                        String out = "";
                        for (Object i : this) {
                            out += "#key = {";
                            out += i + "\r\n";
                            for (int k = 0; k < tab; k++) {
                                out += "\t";
                            }
                            out += "}";
                        }
                        return out;
                    }
                };
                int order = size++;
                DataMap secMap = verLoad(matcher.group(4).replaceAll("(?m)^\t", ""));
                ValueTriplet dat = new ValueTriplet<>(matcher.group(2), secMap, order);
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

    // Deprecated entries are currently not removed
    public Object load(String input) {
        LinkedHashMap<String, ArrayList<Object>> data = new LinkedHashMap<String, ArrayList<Object>>() {
            @Override
            public String toString() {
                String out = "";
                tab++;
                String tabs = "\r\n";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                for (String i : keySet()) {
                    out += get(i).toString().replaceAll("#key", tabs + i);
                }
                tab--;
                return out;
            }
        };
        Matcher single_match;
        try {
            single_match = DataPattern.C_MATCH.matcher(input);
        } catch (NullPointerException e) {
            System.out.println("Cause: " + input + "\nEND INPUT");
            return null;
        }

        ArrayList<Object> temp;

        while (single_match.find()) {
            if (single_match.group(1) != null) {

                temp = new PairArrayList();

                VPair dat = new VPair(single_match.group(2).trim(), single_match.group(3).trim());
                temp.add(dat);
                if (data.containsKey(single_match.group(1).trim())) {
                    data.get(single_match.group(1).trim()).add(dat);
                } else {
                    data.put(single_match.group(1).trim(), temp);
                }
            } else if (single_match.group(5) != null) {

                temp = new ArrayList<Object>() {
                    @Override
                    public String toString() {
                        String out = "#key = {";
                        for (Object sub : this) {
                            out += sub.toString().replaceAll("(?m)^[\r\n\\t]+", " ");
                        }
                        return out + " }";
                    }
                };

                Matcher sCs_match = DataPattern.sComplex_sub.matcher(single_match.group(6));
                if (sCs_match.find()) {
                    if (sCs_match.group(2) != null) {
                        LinkedHashMap<String, VPair> sCsMap = new LinkedHashMap<String, VPair>() {
                            @Override
                            public String toString() {
                                String out = "";
                                for (String key : keySet()) {
                                    out += " " + key + " " + this.get(key).getKey() + " " + this.get(key).getValue();
                                }
                                return out + "";
                            }
                        };

                        if (sCs_match.group(3) != null) {
                            do {
                                try {
                                    VPair rec = new VPair(sCs_match.group(2).trim(), sCs_match.group(3).trim()) {
                                        @Override
                                        public String toString() {
                                            return getKey() + " " + getValue();
                                        }
                                    };
                                    sCsMap.put(sCs_match.group(1).trim(), rec);
                                } catch (Exception e) {
                                    System.out.println("Parsing Failed\n Cause: " + single_match.group(6));
                                    return null;
                                }
                            } while (sCs_match.find());
                            temp.add(sCsMap);
                            if (data.containsKey(single_match.group(5).trim())) {
                                data.get(single_match.group(5).trim()).add(sCsMap);
                            } else {
                                data.put(single_match.group(5).trim(), temp);
                            }
                        } else {
                            Object dat = load(single_match.group(6).replaceFirst("^\\s", "\t").replaceFirst("\\s$", "\n"));
                            temp.add(dat);
                            if (data.containsKey(single_match.group(5).trim())) {
                                data.get(single_match.group(5).trim()).add(dat);
                            } else {
                                data.put(single_match.group(5).trim(), temp);
                            }
                        }

                    } else {
                        ArrayList<Object> sCsList = new ArrayList<Object>() {
                            @Override
                            public String toString() {
                                String out = "";
                                for (Object i : this) {
                                    out += " " + i + "";
                                }
                                return out;
                            }
                        };

                        do {
                            sCsList.add(sCs_match.group(1).trim());
                        } while (sCs_match.find());
                        temp.add(sCsList);
                        if (data.containsKey(single_match.group(5).trim())) {
                            data.get(single_match.group(5).trim()).add(sCsList);
                        } else {
                            data.put(single_match.group(5).trim(), temp);
                        }
                    }
                }
            } else if (single_match.group(8) != null) {

                temp = new ArrayList<Object>() {
                    @Override
                    public String toString() {
                        // Fix repeated here
                        String out = "";
                        for (Object i : this) {
                            out += "#key = {";
                            out += i + "\r\n";
                            for (int k = 0; k < tab; k++) {
                                out += "\t";
                            }
                            out += "}";
                        }
                        return out;
                    }
                };
                Object dat = load(single_match.group(10).replaceAll("(?m)^\t", ""));
                temp.add(dat);
                if (data.containsKey(single_match.group(8).trim())) {
                    data.get(single_match.group(8).trim()).add(dat);
                } else {
                    data.put(single_match.group(8).trim(), temp);
                }
            }
        }
        return data;
    }

    public abstract String export();

    @Override
    public String toString() {
        return name;
    }
}