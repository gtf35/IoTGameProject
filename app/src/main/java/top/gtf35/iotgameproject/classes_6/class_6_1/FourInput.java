package top.gtf35.iotgameproject.classes_6.class_6_1;

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
    private Handler mHandler = null;
    private static int mFourInput_fd = 0;

    public FourInput(int com, int mode, int bandRate, Handler handler) {
        this.mHandler = handler;
        ZigbeeAnalogHelper.com = ZigBeeAnalogServiceAPI.openPort(com, mode, bandRate);
        mFourInput_fd = ZigbeeAnalogHelper.com;
    }

    public void closePort(){
        ZigBeeAnalogServiceAPI.closeUart();
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
                Message msg = new Message();
                msg.obj = s;
                msg.what = ISTEMP;
                mHandler.sendMessage(msg);
            }
        });
        ZigBeeAnalogServiceAPI.getHum("humi", new OnHumResponse() {
            @Override
            public void onValue(double v) {
            }

            @Override
            public void onValue(String s) {
                Message msg = new Message();
                msg.obj = s;
                msg.what = ISHUMI;
                mHandler.sendMessage(msg);
            }
        });
        ZigBeeAnalogServiceAPI.getLight("light", new OnLightResponse() {
            @Override
            public void onValue(double v) {
            }

            @Override
            public void onValue(String s) {
                Message msg = new Message();
                msg.obj = s;
                msg.what = ISLIGHT;
                mHandler.sendMessage(msg);
            }
        });
    }
}
