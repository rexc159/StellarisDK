package StellarisDK.FileClasses;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/*
    This class may prove most data classes worthless as a common parser works
    for all files within such format.
    More details later.
 */
public abstract class GenericData {
    protected HashMap<String, Object> data = new LinkedHashMap<>();

    public GenericData(){}

    public GenericData(String path){
        try{
            data = (HashMap)load(path);
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
