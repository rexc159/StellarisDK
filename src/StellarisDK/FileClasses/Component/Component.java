package StellarisDK.FileClasses.Component;

import StellarisDK.FileClasses.DataPattern;
import StellarisDK.FileClasses.GenericData;
import StellarisDK.FileClasses.Locale;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

enum CompType {
    UTILITY,
    STRIKE_CRAFT,
    WEAPON
}

public class Component extends GenericData {
    public static int tab = 1;
    private CompType type;

    public Component(String path) {
        super(path);
    }

    @Override
    public Object load(String input) {
        LinkedHashMap<String, LinkedList<Object>> data = new LinkedHashMap<String, LinkedList<Object>>() {
            @Override
            public String toString() {
                String out = "";
                tab++;
                String tabs = "";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                for (String i : keySet()) {
                    out += get(i).toString().replaceAll("#key", "\n"+tabs+i);
                }
                tab--;
                return out;
            }
        };

        Matcher kv_match = DataPattern.kv.matcher(input);

        Matcher sC_match = DataPattern.sComplex.matcher(input);
        Matcher mC_match = DataPattern.mComplex.matcher(input);

        LinkedList<Object> temp;

        while (kv_match.find()) {
            temp = new LinkedList<Object>() {
                @Override
                public String toString() {
                    String out = "";
                    for (Object pair : this) {
                        if (this.size() == 1) {
                            return "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
                        }
                        out += "#key " + ((Pair) pair).getKey() + " " + ((Pair) pair).getValue();
                    }
                    return out;
                }
            };
            Pair<String, String> dat = new Pair<>(kv_match.group(2).trim(), kv_match.group(3).replaceAll("\"", "").trim());
            temp.add(dat);
            if (data.containsKey(kv_match.group(1).trim())) {
                data.get(kv_match.group(1).trim()).add(dat);
            } else {
                data.put(kv_match.group(1).trim(), temp);
            }
        }

        while (sC_match.find()) {
            temp = new LinkedList<Object>() {
                @Override
                public String toString() {
                    String out = "[";
                    for (Object sub : this) {
                        if (this.size() == 1) {
                            return sub.toString();
                        }
                        out += sub;
                    }
                    return out + "]";
                }
            };

            Matcher sCs_match = DataPattern.sComplex_sub.matcher(sC_match.group(2));
            if (sCs_match.find() && sCs_match.group(2) != null) {
                HashMap<String, Pair<String, String>> sCsMap = new HashMap<String, Pair<String, String>>() {
                    @Override
                    public String toString() {
                        String out = "#key = {";
                        for (String key : keySet()) {
                            out += " " + key + " " + this.get(key).getKey() + " " + this.get(key).getValue();
                        }
                        return out + " }";
                    }
                };
                if (sCs_match.group(3) != null) {
                    do {
                        try {
                            Pair<String, String> rec = new Pair<String, String>(sCs_match.group(2).trim(), sCs_match.group(3).trim()){
                                @Override
                                public String toString(){
                                    return getKey() + " " + getValue();
                                }
                            };
                            sCsMap.put(sCs_match.group(1).trim(), rec);
                        } catch (Exception e) {
                            System.out.println("Parsing Failed\n Cause: " + sC_match.group(2));
                            return null;
                        }
                    } while (sCs_match.find());
                    temp.add(sCsMap);
                    if (data.containsKey(sC_match.group(1).trim())) {
                        data.get(sC_match.group(1).trim()).add(sCsMap);
                    } else {
                        data.put(sC_match.group(1).trim(), temp);
                    }
                } else {
                    System.out.println(sC_match.group(2).replaceFirst("^\\s", "\t").replaceFirst("\\s$", "\n"));
                    Object dat = load(sC_match.group(2).replaceFirst("^\\s", "\t").replaceFirst("\\s$", "\n"));
                    temp.add(dat);
                    if (data.containsKey(sC_match.group(1).trim())) {
                        data.get(sC_match.group(1).trim()).add(dat);
                    } else {
                        data.put(sC_match.group(1).trim(), temp);
                    }
                }
            } else {
                LinkedList<Object> sCsList = new LinkedList<Object>() {
                    @Override
                    public String toString() {
                        String out = "{";
                        for (Object i : this) {
                            out += " \"" + i + "\"";
                        }
                        return out + "}";
                    }
                };
                do {
                    sCsList.add(sCs_match.group(1).trim());
                } while (sCs_match.find());
                temp.add(sCsList);
                if (data.containsKey(sC_match.group(1).trim())) {
                    data.get(sC_match.group(1).trim()).add(sCsList);
                } else {
                    data.put(sC_match.group(1).trim(), temp);
                }
            }
        }

        while (mC_match.find()) {
            temp = new LinkedList<Object>() {
                @Override
                public String toString() {
                    // Fix repeated here
                    String out = "#key = {";
                    for (Object i : this) {
                        out += i + "\n";
                        for (int k = 0; k < tab; k++) {
                            out += "\t";
                        }
                    }
                    return out + "}";
                }
            };
            Object dat = load(mC_match.group(3).replaceAll("(?m)^\t", ""));
            temp.add(dat);
            if (data.containsKey(mC_match.group(1).trim())) {
                data.get(mC_match.group(1).trim()).add(dat);
            } else {
                data.put(mC_match.group(1).trim(), temp);
            }
        }
        return data;
    }

    @Override
    public String export() {
        String out = " = {\n";
        for (String key : data.keySet()) {
            if (data.get(key) != null) {
                String tabs = "";
                for (int k = 0; k < tab; k++) {
                    tabs += "\t";
                }
                out += data.get(key).toString().replaceAll("#key", tabs+key) + "\n";
            }
        }
        return out + "}\n";
    }

    public String getGroup() {
        return data.get("component_set").toString();
    }

    @Override
    public String toString() {
        String temp = Locale.getLocale((String) data.get("key"));
        if (temp != null)
            return temp;
        else
            return data.get("key").toString();
    }
}