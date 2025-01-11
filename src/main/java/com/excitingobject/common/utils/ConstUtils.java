package com.excitingobject.common.utils;

public class ConstUtils {
    public static String joinText(String... args) {
        return joinText('.', args);
    }
    public static String joinText(Character sep, String... args) {
        String val = "";
        if(args != null) {
            for (String text: args) {
                if(!"".equals(text)) {
                    if(val.equals("")) {
                        val = text;
                    } else {
                        val += sep + text;
                    }
                }
            }
        }
        return val;
    }
}
