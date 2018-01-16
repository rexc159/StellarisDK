package com.StellarisDK.Tools.FileClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Dummy reference class
public class recursiveParser {

//    private final static Pattern object = Pattern.compile("(?s)(?m)(^\\w+) = \\{(.+?)\\n\\}\\n?");
//    private final static Pattern constant = Pattern.compile("(\\@\\w+) = (.+)");
    private static final Pattern type1 = Pattern.compile("(?m)^\\t(\\w+) = \\{\\s*(.+?)\\}");
    private static final Pattern type1_sub = Pattern.compile("(?m)(\\w+) = ([^ \\n]+)|(\\w+)");
    private static final Pattern type2 = Pattern.compile("(?s)(?m)^\\t(\\w+) = \\{[\\r\\n](.+?)^\t\\}");
    private static final Pattern kv = Pattern.compile("(?m)^\\t?(\\w+) . ([^\\{#\\n]+)(#.+)*");

    public static void parse(String input){

        Matcher kv_match = kv.matcher(input);

        Matcher t1_match = type1.matcher(input);
        Matcher t2_match = type2.matcher(input);

        while(kv_match.find()){
            System.out.println(kv_match.group(1).trim()+":!"+kv_match.group(2).trim());
        }

        while(t1_match.find()){
            System.out.println(t1_match.group(1).trim()+":{");
            parse(t1_match.group(2).replaceAll("(?m)^\t",""));
        }
        while(t2_match.find()){
            System.out.println("\t"+t2_match.group(1).trim()+":");
            parse(t2_match.group(2).replaceAll("(?m)^\t",""));
        }

    }
}
