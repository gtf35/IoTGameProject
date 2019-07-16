package top.gtf35.iotgameproject.classes_7.class_7_1;

import android.os.Handler;
import android.os.Message;

import com.newland.zigbeelibrary.ZigBeeAnalogServiceAPI;
import com.newland.zigbeelibrary.ZigBeeService;
import com.newland.zigbeelibrary.response.OnHumResponse;
import com.newland.zigbeelibrary.response.OnLightResponse;
import com.newland.zigbeelibrary.response.OnTemperatureResponse;

public class FourPort {

    private final int ISTEMP = 0;
    private final int ISHUMI = 0;
    private final int ISLIGHT = 0;
    private Handler mHandle = null;

    public FourPort(int com, int mode, int baudRate, Handler handler) {
        this.mHandle = handler;
        ZigBeeAnalogServiceAPI.openPort(com, mode, baudRate);
    }

    public void start(){
        ZigBeeService service = new ZigBeeService();
        service.start();
        ZigBeeAnalogServiceAPI.getTemperature("temp", new OnTemperatureResponse() {
            @Override
            public void onValue(double v) {

            }

            @Override
            public void onValue(String s) {
                sendMsg(ISLIGHT, s);
            }
        });
        ZigBeeAnalogServiceAPI.getLight("light", new OnLightResponse() {
            @Override
            public void onValue(double v) {

            }

            @Override
            public void onValue(String s) {
                sendMsg(ISLIGHT, s);
            }
        });
        ZigBeeAnalogServiceAPI.getHum("HUMI", new OnHumResponse() {
            @Override
            public void onValue(double v) {

            }

            @Override
            public void onValue(String s) {
                sendMsg(ISHUMI, s);
            }
        });
    }

    private void sendMsg(int tag, String msg){
        Message message = new Message();
        message.what = tag;
        message.obj = msg;
        mHandle.sendMessage(message);
    }

    public void closePort(){
        ZigBeeAnalogServiceAPI.closeUart();
    }
}
