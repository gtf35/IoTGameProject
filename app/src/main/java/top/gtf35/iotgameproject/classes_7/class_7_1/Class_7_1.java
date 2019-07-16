package top.gtf35.iotgameproject.classes_7.class_7_1;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import top.gtf35.iotgameproject.MainActivity;
import top.gtf35.iotgameproject.R;

public class Class_7_1 extends AppCompatActivity {

    private TextView mTempTV, mHumiTV, mLightTV, mStatusTV;
    private FourPort mFourPort;
    private PortHandler mHandle;
    private double mTempInSetting;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_7_1);
        mFourPort = new FourPort(1, 0, 5, mHandle);
        mFourPort.start();
        initView();
    }

    private void initView(){
        mTempTV = findViewById(R.id.tv_temp);
        mHumiTV = findViewById(R.id.tv_humi);
        mLightTV = findViewById(R.id.tv_light);
        mStatusTV = findViewById(R.id.tv_statues_title);
    }

    private class PortHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mTempTV.setText("温度感应：" + (String)msg.obj + "°C");
                    if (Double.parseDouble((String)msg.obj) > mTempInSetting){
                        playMusic();
                    } else {
                        stopMusic();
                    }
                    break;
                case 1:
                    mHumiTV.setText("湿度感应：" + (String)msg.obj);
                    break;
                case 2:
                    mLightTV.setText("光照感应：" + (String)msg.obj);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFourPort.closePort();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                mTempInSetting = data.getDoubleExtra("temp", 1.0);
        }
    }

    private void playMusic(){
        if (mPlayer != null) if (mPlayer.isPlaying()) return;
        AssetManager assetManager = this.getAssets();
        AssetFileDescriptor fileDescriptor;
        mPlayer = new MediaPlayer();
        try{
            fileDescriptor = assetManager.openFd("TempIsH.mp3");
            mPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mPlayer.prepare();
            mPlayer.start();
        } catch (Exception e){

        }
    }

    private void stopMusic(){
        if (mPlayer != null) mPlayer.stop();
    }
}
