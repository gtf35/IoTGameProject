package top.gtf35.iotgameproject.classes_6.class_6_3;

import android.os.Handler;
import android.os.Message;

import com.newland.zigbeelibrary.ZigBeeAnalogServiceAPI;
import com.newland.zigbeelibrary.ZigBeeService;
import com.newland.zigbeelibrary.ZigbeeAnalogHelper;
import com.newland.zigbeelibrary.response.OnHumResponse;
import com.newland.zigbeelibrary.response.OnLightResponse;
import com.newland.zigbeelibrary.response.OnTemperatureResponse;

public class FourInput {
    private final int ISTEMP = 0;
    private final int ISHUMI = 1;
    private final int ISLIGHT = 2;
    private static int mFourInput_fd = 0;
    private String mTemp = "--", mHumi = "--" , mLight = "--" ;

    public FourInput(int com, int mode, int bandRate) {
        ZigbeeAnalogHelper.com = ZigBeeAnalogServiceAPI.openPort(com, mode, bandRate);
        mFourInput_fd = ZigbeeAnalogHelper.com;
    }

    public void closePort(){
        ZigBeeAnalogServiceAPI.closeUart();
    }

    public String getmTemp() {
        return mTemp;
    }

    public String getmHumi() {
        return mHumi;
    }

    public String getmLight() {
        return mLight;
    }

    public void start(){
        ZigBeeService zigBeeService = new ZigBeeService();
        zigBeeService.start();
        ZigBeeAnalogServiceAPI.getTemperature("temp", new OnTemperatureResponse() {
            @Override
            public void onValue(double v) {
            }

            @Override
            public void onValue(String s) {
                mTemp = s;
            }
        });
        ZigBeeAnalogServiceAPI.getHum("humi", new OnHumResponse() {
            @Override
            public void onValue(double v) {
            }

            @Override
            public void onValue(String s) {
                mHumi = s;
            }
        });
        ZigBeeAnalogServiceAPI.getLight("light", new OnLightResponse() {
            @Override
            public void onValue(double v) {
            }

            @Override
            public void onValue(String s) {
                mLight = s;
            }
        });


    }
}
