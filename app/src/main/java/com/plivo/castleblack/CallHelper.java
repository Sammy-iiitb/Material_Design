package com.plivo.castleblack;
/**
 * Created by lijo on 28/5/15.
 */
public class CallHelper {
    public static String helpCall(String number) {
        String num;
        if (number.charAt(0) == '+')
            num = number.substring(1, number.length());
        else
            num = "";
        return num;
    }
}
