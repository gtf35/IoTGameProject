package top.gtf35.iotgameproject.classes_5.class_5_1;

import android.util.Log;

import com.example.analoglib.Analog4150ServiceAPI;
import com.example.analoglib.OnFireResponse;
import com.example.analoglib.OnPersonResponse;
import com.example.analoglib.ReceiveThread;

public class ADAM4150 {

    private final char[] open1Fan =  {0x01, 0x05, 0x00, 0x10, 0xff, 0x00, 0x5d, 0xff};
    private final char[] close1Fan = {0x01, 0x05, 0x00, 0x10, 0x00, 0x00, 0xcc, 0x0f};
    private final char[] open2Fan =  {0x01, 0x05, 0x00, 0x11, 0xff, 0x00, 0xdc, 0x3f};
    private final char[] close2Fan = {0x01, 0x05, 0x00, 0x11, 0x00, 0x00, 0x9d, 0xcf};

    private static int mADAM4150_fd = 0;
    private boolean rePersion;
    private boolean reFire;

    public ADAM4150(int com, int mode, int baudRate) {
        mADAM4150_fd = Analog4150ServiceAPI.openPort(com, mode, baudRate);
        ReceiveThread receiveThread = new ReceiveThread();
        receiveThread.start();
        Analog4150ServiceAPI.getPerson("persion", new OnPersonResponse() {
            @Override
            public void onValue(boolean b) {
                rePersion = !b;
            }

            @Override
            public void onValue(String s) {
                Log.w(this.getClass().getSimpleName(), s);
            }
        });
        Analog4150ServiceAPI.getFire("fire", new OnFireResponse() {
            @Override
            public void onValue(boolean b) {
                reFire = b;
            }

            @Override
            public void onValue(String s) {
                Log.w(this.getClass().getSimpleName(), s);
            }
        });
    }

    public boolean isPersion() {
        return rePersion;
    }

    public boolean isFire() {
        return reFire;
    }

    public void open1Fan(){
        Analog4150ServiceAPI.sendRelayControl(open1Fan);
    }

    public void close1Fan(){
        Analog4150ServiceAPI.sendRelayControl(close1Fan);
    }

    public void open2Fan(){
        Analog4150ServiceAPI.sendRelayControl(open2Fan);
    }

    public void close2Fan(){
        Analog4150ServiceAPI.sendRelayControl(close2Fan);
    }

    public void send4150() {
        Analog4150ServiceAPI.send4150();
    }
}
