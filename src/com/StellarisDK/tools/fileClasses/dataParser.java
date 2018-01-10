package com.StellarisDK.tools.fileClasses;

import com.StellarisDK.tools.fileClasses.Component.Utility;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.StellarisDK.tools.fileClasses.recursiveParser.parse;

public class dataParser {

    private final static Pattern pattern = Pattern.compile("(?s)(?m)(^\\w+)\\s=\\s\\{(.+?)\\n\\}\\n?");
    private final static Pattern constants = Pattern.compile("(\\@\\w+) = (.+)");

    // ^\w+\s=\s\{.+?\n\}\n? - RegEx for matching files ** Remember DOTALL & MULTILINE
    // [^]+?^} - Also works, Need testing
    // ^\t\w+ = [^{^\n]+ - For inlines
    // ^\t\w+ = {[^}]+} - "Group" lines

    public static void parseData(String path) throws IOException {
        Scanner scan = new Scanner(new File(path));
        String temp = "";
        while(temp != null){
            temp = scan.findWithinHorizon(constants,0);
            System.out.println(temp);
        }
        while(scan.hasNext()){
            String test = scan.findWithinHorizon(pattern, 0);
            parse(test);
//            new Utility(test);
        }
    }

    public static LinkedList<Utility> parseCompUtil(String path) throws IOException {
        LinkedList<Utility> out = new LinkedList<>();
        Scanner scan = new Scanner(new File(path));
//        while(scan.hasNext()){
//            String test = scan.findWithinHorizon(pattern, 0);
//            out.add(new Utility(test));
//        }
        String temp = "";
        while(temp != null){
            temp = scan.findWithinHorizon(constants,0);
            System.out.println(temp);
        }
        String test = scan.findWithinHorizon(pattern, 0);
        out.add(new Utility(test));
        return out;
    }
}