package com.StellarisDK.tools.fileClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recursiveParser {

//    private final static Pattern object = Pattern.compile("(?s)(?m)(^\\w+) = \\{(.+?)\\n\\}\\n?");
//    private final static Pattern constant = Pattern.compile("(\\@\\w+) = (.+)");
    private final static Pattern type1 = Pattern.compile("(?m)^\\t(\\w+) = \\{\\s*(.+?)\\}");
    private final static Pattern type2 = Pattern.compile("(?s)(?m)^\\t(\\w+) = \\{[\\r\\n](.+?)^\t\\}");
    private final static Pattern kv = Pattern.compile("(?m)^\\t?(\\w+) . ([^\\{#\\n]+)(#.+)*");

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
