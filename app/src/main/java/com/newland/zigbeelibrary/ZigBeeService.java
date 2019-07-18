//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

import android.annotation.SuppressLint;
import android.util.Log;

import com.newland.jni.Linuxc;
import com.newland.zigbeelibrary.ZigbeeAnalogHelper.SENSOR_TYPE;
import com.newland.zigbeelibrary.gtf.ArrayUtils;
import com.newland.zigbeelibrary.response.OnZigbeeRespone;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@SuppressLint({"DefaultLocale"})
public class ZigBeeService extends Thread {
    private byte[] data = new byte[1024];
    private int datalen = 0;
    private Calendar c = null;
    private String time;
    private boolean isFlag = false;
    private int tag_len = 0;
    private int lengthcount = 21;
    private final byte HEAD = -2;
    private final byte CMD0 = 70;
    private final byte CMD1 = -121;
    private final byte DTYPEL = 2;
    private final byte DTYPEH = 0;
    private final String TAG = this.getClass().getSimpleName();
    private ZigbeeEntity zigBeeEntity;

    public ZigBeeService() {
    }

    public void run() {
        Log.w(getClass().getSimpleName(), "FourInput run call");
        ZigbeeEntity zigBeeEntity = new ZigbeeEntity();
        while (ZigbeeAnalogHelper.com > 0) {
            try {
                Thread.currentThread();
                Thread.sleep(250);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] temp = new byte[1024];
            int lentemp = Linuxc.receiveMsgUartHex(ZigbeeAnalogHelper.com, temp.length, temp);
            if (lentemp > 0) {
                if (ZigbeeAnalogHelper.com > 0) {
                    System.arraycopy(temp, 0, this.data, 0, lentemp);
                    while (true) {
                        if (lentemp < 27) {
                            Log.w(getClass().getSimpleName(), "lentemp < 27 break  lentemp = " + lentemp);
                            break;
                        }
                        if (!checkData(this.data)) {
                            Log.w(getClass().getSimpleName(), "数据头校验不通过    !checkData(this.data) break ");
                            break;
                        }
                        //报文第8位定义了有效数据长度，把表示数据长度的byte转换成String，便于转换成int来初始化最终的数组
                        String dataLengthString = Byte.toString(data[8]);
                        // 16进制转10进制
                        int datalength = Integer.valueOf(dataLengthString, 16);
                        Log.w(TAG, "报文显示有效数据长度为" + datalength + ",原始byte为" + data[8]);
                        //根据报文汇报的有效数据长度创建新的byte数组
                        byte[] databyte = new byte[datalength];
                        try {
                            //从第10位开始是有效数据，把有效数据复制到新数组中
                            for (int i = 0; i < datalength; i++) {
                                databyte[i] = this.data[i + 10];
                            }
                            //有效数据中的第7位为功能定义位，10进制48，16进制30，匹配的话进行温湿度等特性提取
                            if (databyte[7] == 48) {
                                String lightValue = ZigbeeAnalogHelper.getInstance().convert(databyte, SENSOR_TYPE.LIGHT);
                                String temperatureValue = ZigbeeAnalogHelper.getInstance().convert(databyte, SENSOR_TYPE.TEMPERATURE);
                                String humidityValue = ZigbeeAnalogHelper.getInstance().convert(databyte, SENSOR_TYPE.HUMIDITY);
                                zigBeeEntity.light = lightValue;
                                zigBeeEntity.temperature = temperatureValue;
                                zigBeeEntity.hum = humidityValue;
                                Map<String, OnZigbeeRespone> responses = ZigbeeAnalogHelper.getInstance().getOnZigbeeResponses();
                                if (responses != null) {
                                    for (Entry<String, OnZigbeeRespone> entry : responses.entrySet()) {
                                        ((OnZigbeeRespone) entry.getValue()).getZigbeeValue(zigBeeEntity);
                                    }
                                }
                            }
                        } catch (Exception e){
                            Log.e(TAG, "切割数据位部分报错：" + e.toString());
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.w(getClass().getSimpleName(), "因为 ZigbeeAnalogHelper.com > 0 不满足，return");
                    return;
                }
            } else {
                Log.w(getClass().getSimpleName(), "因为 lentemp > 0 不满足，return");
            }
        }
    }


    private boolean checkData(byte[] data) {
        boolean result = data[0] == HEAD && data[2] == CMD0 && data[3] == CMD1 && data[6] == DTYPEL && data[7] == DTYPEH;
        System.out.println("sss result = " + result);
        Log.w(TAG, "收到数据：\n" + ArrayUtils.arrayToString(data, 23));
        Log.w(TAG, "校验数据有效性结果：" + result);
        return result;
    }

    public int convert(int data1, int data2) {
        String tempString = Integer.toHexString(data2);
        if (tempString.length() == 1) {
            tempString = "0" + tempString;
        }

        String strLight = Integer.toHexString(data1) + tempString;
        int lightValue = Integer.parseInt(strLight, 16);
        return lightValue;
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

    @SuppressLint({"DefaultLocale"})
    private char[] ToChars(byte[] b) {
        char[] temp = new char[1024];

        for(int n = 0; n < b.length; ++n) {
            temp[n] = (char)(b[n] & 255);
        }

        return temp;
    }

    @SuppressLint({"DefaultLocale"})
    private int findCharInReceiveData(int start, char ch) {
        for(int i = start; i < this.datalen; ++i) {
            if (this.data[i] == ch) {
                return i;
            }
        }

        return -1;
    }

    public String intToString(int[] in) {
        String str_out = "";

        for(int i = in.length - 1; i >= 0; --i) {
            str_out = this.change(Integer.toHexString(in[i])) + str_out;
        }

        return str_out;
    }

    private String change(String hexString) {
        String str = "";
        if (hexString.length() >= 2) {
            str = hexString;
        } else {
            str = "0" + hexString;
        }

        return str;
    }

    public String change(int c) {
        return c >= 10 ? String.valueOf(c) : "0" + String.valueOf(c);
    }

    public static void printHexString(String hint, byte[] b) {
        System.out.print(hint);

        for(int i = 0; i < b.length; ++i) {
            String hex = Integer.toHexString(b[i] & 255);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }

            System.out.print(hex.toUpperCase() + " ");
        }

        System.out.println(" ");
    }

    private char[] ToChars(byte[] b, int length) {
        char[] temp = new char[length];

        for(int n = 0; n < b.length; ++n) {
            temp[n] = (char)(b[n] & 255);
        }

        return temp;
    }


}
