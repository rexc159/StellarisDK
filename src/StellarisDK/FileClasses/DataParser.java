package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
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

    public static GenericData parseData(String path) throws IOException {
        Scanner scan = new Scanner(new File(path));
        Component data = null;
        String temp = "";
        while (temp != null) {
            temp = scan.findWithinHorizon(constants, 0);
            System.out.println(temp);
        }
        while (scan.hasNext()) {
            String test = scan.findWithinHorizon(pattern, 0);
//            System.out.println("Original: "+test);
//            new Component(test).export();
            System.out.println("New: " + new Component(test).export() + "\n\n");
            data = new Component(test);
        }
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
            if(temp !=null)
                consts.getChildren().add(new TreeItem<>(temp));
        } while (temp != null);
        if(!consts.getChildren().isEmpty())
            out.add(consts);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                GenericData gData;
                switch (obj.group(1)) {
                    case "component_set":
                        gData = new CompSet(objectDat);
                        break;
                    case "ambient_object":
                    case "anomaly":
                    default:
                        gData = new Component(objectDat);
                }

                out.add(new TreeItem<>(gData));
            } else {
                break;
            }
        }
        return out;
    }
}