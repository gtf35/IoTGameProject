package com.newland.zigbeelibrary.gtf;

public class DataUtils {
    /*
     * gtf add
     * */

    public static String hex2String(byte b) {
        String hex = Integer.toHexString(b & 255);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }
}
