package com.StellarisDK.tools.fileClasses.ship;

import java.util.regex.Matcher;

public class CompUtil extends Component_Template {

    public CompUtil(){
    }

    public CompUtil(String input){

        Matcher kv_match = kv.matcher(input);
        Matcher cv_match = cv.matcher(input);
        Matcher int_match;

        while(kv_match.find()){
            System.out.println(kv_match.group(1)+":"+kv_match.group(2).replaceAll("\"",""));
        }

        while(cv_match.find()){
            System.out.println(cv_match.group(1)+":");
            int_match = internal.matcher(cv_match.group(2));
            while(int_match.find()){
                System.out.println("\t"+int_match.group(1)+":"+int_match.group(3));
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return data.get("key").toString();
    }
}