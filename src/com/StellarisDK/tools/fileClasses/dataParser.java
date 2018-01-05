package com.StellarisDK.tools.fileClasses;

import com.StellarisDK.tools.fileClasses.ship.CompUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class dataParser {

    private final static Pattern pattern = Pattern.compile("(?s)(?m)^\\w+\\s=\\s\\{.+?\\n\\}\\n?");
    private final static Pattern constant = Pattern.compile("(\\@\\w+) = (.+)");

    // ^\w+\s=\s\{.+?\n\}\n? - RegEx for matching files ** Remember DOTALL & MULTILINE
    // [^]+?^} - Also works, Need testing
    // ^\t\w+ = [^{^\n]+ - For inlines
    // ^\t\w+ = {[^}]+} - "Group" lines

    public static void parseData(String path) throws IOException {
        Scanner scan = new Scanner(new File(path));
        String temp = "";
        while(temp != null){
            temp = scan.findWithinHorizon(constant,0);
            System.out.println(temp);
        }
        String test = scan.findWithinHorizon(pattern, 0);
        new CompUtil(test);
    }

    public static LinkedList<CompUtil> parseCompUtil(String path) throws IOException {
        LinkedList<CompUtil> out = new LinkedList<>();
        Scanner scan = new Scanner(new File(path));
//        while(scan.hasNext()){
//            String test = scan.findWithinHorizon(pattern, 0);
//            out.add(new CompUtil(test));
//        }
        String temp = "";
        while(temp != null){
            temp = scan.findWithinHorizon(constant,0);
            System.out.println(temp);
        }
        String test = scan.findWithinHorizon(pattern, 0);
        out.add(new CompUtil(test));
        return out;
    }
}