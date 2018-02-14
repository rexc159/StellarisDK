package com.StellarisDK.Tools.FileClasses;

import java.io.IOException;
import java.util.HashMap;

/*
    This class may prove most data classes worthless as a common parser works
    for all files within such format.
    More details later.
 */
public abstract class GenericData {
    protected HashMap<String, Object> data = new HashMap<>();

    public GenericData(String keys[]) {
        for (String key : keys) {
            data.put(key, null);
        }
    }

    public GenericData(String keys[], String path){
        this(keys);
        try{
            load(path);
        } catch(IOException e){
            System.out.println("File not Found.");
        }
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public void setValue(String key, Object value) {
        data.replace(key, value);
    }

    public abstract Object load(String path) throws IOException;

    public abstract String export();
}
