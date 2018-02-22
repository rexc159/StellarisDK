package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.PairLinkedList;
import StellarisDK.FileClasses.Helper.cPair;
import StellarisDK.GUI.AbstractUI;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;

/*
    This class may prove most data classes worthless as a common parser works
    for all files within such format.
    More details later.
 */
public abstract class GenericData {

    private static int tab = 1;

    protected HashMap<String, Object> data = new LinkedHashMap<>();

    public AbstractUI ui;

    public GenericData() {
    }

    public GenericData(String input) {
        data = (HashMap) load(input);
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

    public Object load(String input) {
        LinkedHashMap<String, LinkedList<Object>> data = new LinkedHashMap<String, LinkedList<Object>>() {
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

        Matcher single_match = DataPattern.C_MATCH.matcher(input);

        LinkedList<Object> temp;

        while (single_match.find()) {
            if (single_match.group(1) != null) {
                temp = new PairLinkedList();

                cPair dat = new cPair(single_match.group(2).trim(), single_match.group(3).trim());
                temp.add(dat);
                if (data.containsKey(single_match.group(1).trim())) {
                    data.get(single_match.group(1).trim()).add(dat);
                } else {
                    data.put(single_match.group(1).trim(), temp);
                }

            } else if (single_match.group(5) != null) {
                temp = new LinkedList<Object>() {
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
                if (sCs_match.find() && sCs_match.group(2) != null) {
                    LinkedHashMap<String, cPair> sCsMap = new LinkedHashMap<String, cPair>() {
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
                                cPair rec = new cPair(sCs_match.group(2).trim(), sCs_match.group(3).trim()) {
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
                    LinkedList<Object> sCsList = new LinkedList<Object>() {
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

            } else if (single_match.group(7) != null) {
                temp = new LinkedList<Object>() {
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
                Object dat = load(single_match.group(9).replaceAll("(?m)^\t", ""));
                temp.add(dat);
                if (data.containsKey(single_match.group(7).trim())) {
                    data.get(single_match.group(7).trim()).add(dat);
                } else {
                    data.put(single_match.group(7).trim(), temp);
                }
            }
        }
        return data;
    }

    public abstract String export();
}