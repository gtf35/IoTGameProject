//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

import com.newland.jni.Linuxc;
import com.newland.zigbeelibrary.response.OnHumResponse;
import com.newland.zigbeelibrary.response.OnLightResponse;
import com.newland.zigbeelibrary.response.OnTemperatureResponse;
import com.newland.zigbeelibrary.response.OnZigbeeRespone;

public class ZigBeeAnalogServiceAPI {
    public ZigBeeAnalogServiceAPI() {
    }

    public static void getTemperature(String tag, final OnTemperatureResponse valueResponse) {
        ZigbeeAnalogHelper.getInstance().addOnZigbeeOnResponse(tag, new OnZigbeeRespone() {
            public void getZigbeeValue(ZigbeeEntity entity) {
                valueResponse.onValue(entity.temperature);
                valueResponse.onValue(entity.temperature);
            }
        });
    }

    public static void getHum(String tag, final OnHumResponse valueResponse) {
        ZigbeeAnalogHelper.getInstance().addOnZigbeeOnResponse(tag, new OnZigbeeRespone() {
            public void getZigbeeValue(ZigbeeEntity entity) {
                valueResponse.onValue(entity.hum);
                valueResponse.onValue(entity.hum + "%");
            }
        });
    }

    public static void getLight(String tag, final OnLightResponse valueResponse) {
        ZigbeeAnalogHelper.getInstance().addOnZigbeeOnResponse(tag, new OnZigbeeRespone() {
            public void getZigbeeValue(ZigbeeEntity entity) {
                valueResponse.onValue(entity.light);
                valueResponse.onValue(entity.light);
            }
        });
    }

    public static int openPort(int com, int mode, int baudRate) {
        boolean var3 = true;

        int com_fd;
        try {
            mode = getMode(com);
            com_fd = Linuxc.openUart(com, mode);
            if (com_fd > 0) {
                ZigbeeAnalogHelper.com = com_fd;
                int status = Linuxc.setUart(com_fd, (char)baudRate);
                if (mode == 3) {
                    status = 1;
                }

                if (status > 0) {
                    return com_fd;
                }
            }
        } catch (Exception var5) {
            com_fd = -1;
        }

        return com_fd;
    }

    private static int getMode(int com) {
        return com <= 3 ? 0 : 1;
    }

    public static void closeUart() {
        if (ZigbeeAnalogHelper.com > 0) {
            Linuxc.closeUart(ZigbeeAnalogHelper.com);
            ZigbeeAnalogHelper.com = -1;
        }

    }
}
