//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

import android.util.Log;

import com.newland.zigbeelibrary.gtf.DataUtils;
import com.newland.zigbeelibrary.response.OnZigbeeRespone;
import java.util.HashMap;
import java.util.Map;

public class ZigbeeAnalogHelper {
    private Map<String, OnZigbeeRespone> mOnZigbeeResponseMap = new HashMap();
    private static ZigbeeAnalogHelper mHelper;
    public static int com = -1;
    private final String TAG = getClass().getSimpleName();

    public static ZigbeeAnalogHelper getInstance() {
        if (mHelper == null) {
            Class var0 = ZigbeeAnalogHelper.class;
            synchronized(ZigbeeAnalogHelper.class) {
                if (mHelper == null) {
                    mHelper = new ZigbeeAnalogHelper();
                }
            }
        }

        return mHelper;
    }

    private ZigbeeAnalogHelper() {
    }

    public Map<String, OnZigbeeRespone> getOnZigbeeResponses() {
        return this.mOnZigbeeResponseMap;
    }

    public void addOnZigbeeOnResponse(String tag, OnZigbeeRespone onRenspone) {
        this.mOnZigbeeResponseMap.put(tag, onRenspone);
    }

    public String convert(byte[] valueChar, ZigbeeAnalogHelper.SENSOR_TYPE type) {
        byte[] typeValue = this.getByteValue(valueChar, type);
        //高低位合并
        int sensorValue = (typeValue[1] & 255) << 8 | typeValue[0] & 255;
        System.out.println("sensorValue =" + sensorValue);
        Log.w(TAG, "传感器数值：" + sensorValue + "  低位：" + typeValue[0] + " 高位：" + typeValue[1]);
        String result = "";
        if (ZigbeeAnalogHelper.SENSOR_TYPE.AIRQUALITY == type) {
            //这个无需修正
            Log.w(TAG, "当前解析：空气质量  修正值：" + sensorValue);
            //直接除100输出
            result = Tool.m2((double)sensorValue / 100.0D);
            Log.w(TAG, "当前解析：空气质量  解析值：" + result);
        } else {
            double values = (double)(sensorValue * 3300 / 1023) / 150.0D;
            //修正算法：(原始值 * 3300 / 1023) / 150.0
            Log.w(TAG, "当前解析：" + type.channel + "  修正值：" + result);
            //小于4，设置为4
            if (values <= 4.0D) {
                values = 4.0D;
            }
            //计算权重：（修正值-4）/ 16
            double retValue = (values - 4.0D) / 16.0D * (double)type.maxRange + (double)type.minRange;
            //如果是10进制
            //其实逻辑就是判断小数位数
            if (type.decimalFlag) {
                //一位小数
                result = Tool.m1(retValue);
            } else {
                //2位1小数
                result = Tool.m2(retValue);
            }
            Log.w(TAG, "当前解析：" + type.channel + "  解析值：" + result);
            System.out.println("result =" + result);
        }

        return result;
    }

    private byte[] getByteValue(byte[] value, ZigbeeAnalogHelper.SENSOR_TYPE type) {
        //type.channel是渠道号，也就是功能，标志是温度还是湿度还是空气质量，等等
        //渠道号+7和渠道号+7是当前功能的高低位，+7是低位，+8是高位
        int channel = type.channel;
        byte[] temp = new byte[]{value[channel + 7], value[channel + 8]};
        Log.w(TAG, "解析当前模式高低位，当前的渠道号：" + channel + "  低位：" + DataUtils.hex2String(temp[0]) + "  高位：" + DataUtils.hex2String(temp[1]));
        ZigBeeService.printHexString("getByteValue = ", temp);
        return temp;
    }

    public static enum SENSOR_TYPE {

        /*
        *     光照：渠道号 1
        *     温度：渠道号 3
        *     风速：渠道号 4 未实现
        *     湿度：渠道号 5
        *     气压：渠道号 5 未实现
        * 二氧化碳：渠道号 7 未实现
        * 空气质量：渠道号 8
        * */

        TEMPERATURE(3, 50, 0, true),
        LIGHT(1, 20000, 0, true),
        HUMIDITY(5, 100, 0, true),
        WIND(4, 70, 0, true),
        AIRPRESSURE(5, 110, 0, false),
        CO2(7, 5000, 0, true),
        AIRQUALITY(8, -1, -1, false);

        int channel;//渠道号
        int maxRange;//最大范围
        int minRange;//最小范围
        boolean decimalFlag;//小数位数

        private SENSOR_TYPE(int channel, int maxRange, int minRange, boolean decimalFlag) {
            this.channel = channel;
            this.minRange = minRange;
            this.maxRange = maxRange;
            this.decimalFlag = decimalFlag;
        }
    }
}
