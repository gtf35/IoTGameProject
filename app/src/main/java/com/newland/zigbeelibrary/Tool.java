//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

import java.text.DecimalFormat;

public class Tool {
    public Tool() {
    }

    public static String m1(double f) {
        DecimalFormat df = new DecimalFormat("##.0");
        String result = df.format(f);
        if (result.indexOf(".") == 0) {
            result = "0" + result;
        }

        return result;
    }

    public static String m2(double f) {
        DecimalFormat df = new DecimalFormat("##.00");
        String result = df.format(f);
        if (result.indexOf(".") == 0) {
            result = "0" + result;
        }

        return result;
    }

    public static String m4(double f) {
        DecimalFormat df = new DecimalFormat("##.0000");
        String result = df.format(f);
        if (result.indexOf(".") == 0) {
            result = "0" + result;
        }

        return result;
    }
}
