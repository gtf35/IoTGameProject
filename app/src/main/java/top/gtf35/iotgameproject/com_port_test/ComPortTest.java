package top.gtf35.iotgameproject.com_port_test;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import top.gtf35.iotgameproject.R;

public class ComPortTest extends AppCompatActivity {

    FourInput mFourInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_com_port_test);
        Log.w(getClass().getSimpleName(), "onCreate called");
        mFourInput = new FourInput(7, 0, 5, mHandler);
        mFourInput.start();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String data = (String)msg.obj;
            switch (msg.what){
                case 0:
                    Log.w(getClass().getSimpleName(), "温度感应:" + data);
                    break;
                case 1:
                    Log.w(getClass().getSimpleName(), "湿度感应:" + data);
                    break;
                case 2:
                    Log.w(getClass().getSimpleName(), "光照感应:" + data);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFourInput.closePort();
    }
}
