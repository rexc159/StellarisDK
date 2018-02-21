package StellarisDK.FileClasses;

import StellarisDK.GUI.AbstractUI;

import java.util.HashMap;
import java.util.LinkedHashMap;

/*
    This class may prove most data classes worthless as a common parser works
    for all files within such format.
    More details later.
 */
public abstract class GenericData {
    protected HashMap<String, Object> data = new LinkedHashMap<>();

    public AbstractUI ui;

    public GenericData(){}

    public GenericData(String input){
        data = (HashMap)load(input);
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public void setValue(String key, Object value, boolean addIfAbsent) {
        if(addIfAbsent){
            data.put(key, value);
        }else{
            data.replace(key, value);
        }
    }

    public abstract Object load(String input);

    public abstract String export();
}
