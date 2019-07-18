package com.newland.zigbeelibrary.gtf;

import android.util.Log;

public class ArrayUtils {

    public static String arrayToString(byte[] data){
        return arrayToString(data, -1);
    }

    public static String arrayToString(byte[] data, int arrayLength){
        int length = data.length;
        if (arrayLength > 0) length = arrayLength;
        String result = "";
        try {
            result = result + " ============================数组信息=====================================" + "\n";
            result = result + "│      数组类型：" + data.getClass().getComponentType().getSimpleName() + "\n";
            result = result + "│      数组长度：" + data.length + "\n";
            result = result + "│---------------------------index和内容-----------------------------------" + "\n│";
            /*打印index和内容*/
            for (int i = 0; i < length; i++){
                result = result + " [" + i + "]=" + data[i] + " ";
            }
            result = result + "\n";
            result = result + "│------------------------------内容---------------------------------------" + "\n│";
            /*只打印内容*/
            for (int i = 0; i < length; i++){
                result = result + "   " + data[i];
            }
            result = result + "\n";
            result = result + "│-----------------------index和内容[16进制]--------------------------------" + "\n│";
            /*转义为16进制，打印index和内容*/
            for (int i = 0; i < length; i++){
                String temp = Integer.toHexString(data[i]);
                String theOneByte = temp;
                if (temp.length() > 2) {
                    theOneByte = temp.substring(temp.length() - 2, temp.length());
                }
                result = result + " [" + i + "]=" + theOneByte.toUpperCase() + " ";
            }
            result = result + "\n";
            result = result + "│----------------------------内容[16进制]----------------------------------" + "\n│";
            /*转义为16进制，打印index和内容*/
            for (int i = 0; i < length; i++){
                String temp = Integer.toHexString(data[i]);
                String theOneByte = temp;
                if (temp.length() > 2) {
                    theOneByte = temp.substring(temp.length() - 2, temp.length());
                }
                result = result + "   " + theOneByte.toUpperCase();
            }
            result = result + "\n";
            result = result + " =======================================================================" + "\n";
        } catch (Exception e){
            result = result + "\n解析数组发生错误：" + e.toString();
            e.printStackTrace();
        }
        return result;
    }

    public static void logArray(String tag, byte[] array){
        Log.w(tag, arrayToString(array));
    }
}
