package com.moqod.android.chat.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 11/03/16
 * Time: 14:12
 */
public class NullHelper {

    public static String nonNull(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

}
