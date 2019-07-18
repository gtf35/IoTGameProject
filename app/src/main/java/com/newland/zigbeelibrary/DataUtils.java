//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

import android.content.Context;

public class DataUtils {
    public DataUtils() {
    }

    public static float convert(int data1, int data2) {
        char[] c = new char[]{(char)data1, (char)data2};
        String tempString = Integer.toHexString(data2);
        if (tempString.length() == 1) {
            tempString = "0" + tempString;
        }

        String strLight = Integer.toHexString(data1) + tempString;
        int lightValue = Integer.parseInt(strLight, 16);
        return (float)lightValue;
    }

    public static int toint(int high, int low) {
        int show;
        if (low < 16) {
            show = Integer.parseInt(Integer.toHexString(high) + Integer.toHexString(0) + Integer.toHexString(low), 16);
            show = toComplement(show);
            return show;
        } else {
            show = Integer.parseInt(Integer.toHexString(high) + Integer.toHexString(low), 16);
            show = toComplement(show);
            return show;
        }
    }

    private static int toComplement(int data) {
        new Integer(data);
        String binary = Integer.toString(data, 2);
        if (binary.toCharArray().length == 16) {
            data = ~(data - (int)Math.pow(2.0D, (double)binary.length())) + 1;
            data = 0 - data;
        }

        return data;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    public static String charArrayToHexString(char[] b) {
        String hs = "";
        String tmp = "";

        for(int n = 0; n < b.length; ++n) {
            tmp = Integer.toHexString(b[n]);
            if (tmp.length() == 1) {
                hs = hs + "0" + tmp;
            } else {
                hs = hs + tmp;
            }
        }

        tmp = null;
        return hs.toUpperCase();
    }
}
