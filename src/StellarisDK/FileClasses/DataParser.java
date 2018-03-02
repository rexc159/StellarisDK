package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.DataMap;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParser {

    private final static Pattern pattern = Pattern.compile("(?s)(?m)(^\\w+)\\s=\\s\\{(.+?)[\\r\\n]\\}[\\r\\n]?");
    private final static Pattern constants = Pattern.compile("(\\@\\w+) = (.+)");

    public static void parseToConsole(File file) throws IOException{
        Scanner scan = new Scanner(file);
        Component data;
        String temp;
        do {
            temp = scan.findWithinHorizon(constants, 0);
            if (temp != null)
                System.out.println(temp);
        } while (temp != null);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
//            System.out.println("Original: "+test);
//            new Component(test).export();
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                GenericData gData;
                data = new Component();
                data.setData((DataMap)data.load(obj.group(2)));
                System.out.println(data.export());
            } else {
                break;
            }
        }
    }

    public static GenericData parseData(String path) throws IOException {
        Scanner scan = new Scanner(new File(path));
        Component data = null;
        String temp;
        do {
            temp = scan.findWithinHorizon(constants, 0);
            if (temp != null)
                System.out.println(temp);
        } while (temp != null);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
//            System.out.println("Original: "+test);
//            new Component(test).export();
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                GenericData gData;
                data = new Component(objectDat, obj.group(1));
                System.out.println(data.export());
            } else {
                break;
            }
        }
        scan.close();
        return data;
    }

    /*
        Either follow the extremely long stack of elifs, or just use the folder name as reference
        which is way easier anyway.

        Different types of the same objects are identified within the data class itself
     */
    public static ArrayList<TreeItem> parseAll(File file) throws IOException {
        /*
            Special Handler for attitudes, bombardment, required
        */
        ArrayList<TreeItem> out = new ArrayList<>();
        Scanner scan = new Scanner(file);
        String temp;
        TreeItem<String> consts = new TreeItem<>("Constants");
        do {
            temp = scan.findWithinHorizon(constants, 0);
            if (temp != null)
                consts.getChildren().add(new TreeItem<>(temp));
        } while (temp != null);
        if (!consts.getChildren().isEmpty())
            out.add(consts);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                GenericData gData;
                switch (obj.group(1)) {
                    case "ambient_object":
                        gData = new AmbientObject(obj.group(2));
                        break;
                    case "component_set":
                        gData = new CompSet(obj.group(2));
                        break;
                    case "component":
                    case "anomaly":
                    default:
                        gData = new Component(obj.group(2), obj.group(1));
                }
                out.add(new TreeItem<>(gData));
            } else {
                break;
            }
        }
        scan.close();
        return out;
    }
}