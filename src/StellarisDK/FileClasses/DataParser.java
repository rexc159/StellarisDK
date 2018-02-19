package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataParser {

    private final static Pattern pattern = Pattern.compile("(?s)(?m)(^\\w+)\\s=\\s\\{(.+?)\\n\\}\\n?");
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

    public static LinkedList<TreeItem> parseSet(File file) throws IOException {
        LinkedList<TreeItem> out = new LinkedList<>();
        Scanner scan = new Scanner(file);
        String temp = "";
        while (temp != null) {
            temp = scan.findWithinHorizon(constants, 0);
//            System.out.println(temp);
        }
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            out.add(new TreeItem<>(new CompSet(objectDat)));
        }
        return out;
    }

    public static LinkedList<TreeItem> parseCompUtil(File file) throws IOException {
        LinkedList<TreeItem> out = new LinkedList<>();
        Scanner scan = new Scanner(file);
        String temp = "";
        while (temp != null) {
            temp = scan.findWithinHorizon(constants, 0);
//            System.out.println(temp);
        }
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            out.add(new TreeItem<>(new Component(objectDat)));
        }
        return out;
    }
}