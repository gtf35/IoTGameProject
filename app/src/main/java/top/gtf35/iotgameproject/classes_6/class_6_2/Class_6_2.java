package top.gtf35.iotgameproject.classes_6.class_6_2;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.gtf35.iotgameproject.R;

public class Class_6_2 extends AppCompatActivity {

    private ImageView mFanIV, mDoorIV;
    private AnimationDrawable mFanAnimaDrawable, mDoorOpenAnimaDrawable, mDoorCloseAnimaDrawable;
    private boolean isFunRunning, isDoorOpen;
    private FourInput mFourInput;
    private TextView mTempTV, mHumiTV, mLightTV, mStatusTV;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_6_2);
        initView();
        initAnima();
        startFan();
        closeDoor();
        initListener();
        mFourInput = new FourInput(1, 0, 5, mHandler);
        mFourInput.start();
    }

    private void initView(){
        mFanIV = findViewById(R.id.iv_fan);
        mDoorIV = findViewById(R.id.iv_door);
        mTempTV = findViewById(R.id.tv_temp);
        mHumiTV = findViewById(R.id.tv_humi);
        mLightTV = findViewById(R.id.tv_light);
        mStatusTV = findViewById(R.id.tv_statues_title);
    }

    private void initAnima(){
        mFanAnimaDrawable = new AnimationDrawable();
        for (int i = 1; i <= 8; i++){
            int fanAnimaPicID = getResources().getIdentifier("class_5_3_f" + i , "drawable", getPackageName());
            Drawable fanAnimaPicDrawable = getResources().getDrawable(fanAnimaPicID);
            mFanAnimaDrawable.addFrame(fanAnimaPicDrawable, 40);
        }
        mFanAnimaDrawable.setOneShot(false);


        mDoorOpenAnimaDrawable = new AnimationDrawable();
        for (int i = 1; i <= 11; i++){
            int fanAnimaPicID = getResources().getIdentifier("class_5_3_d" + i , "drawable", getPackageName());
            Drawable fanAnimaPicDrawable = getResources().getDrawable(fanAnimaPicID);
            mDoorOpenAnimaDrawable.addFrame(fanAnimaPicDrawable, 40);
        }
        mDoorOpenAnimaDrawable.setOneShot(true);

        mDoorCloseAnimaDrawable = new AnimationDrawable();
        for (int i = 11; i >= 1; i--){
            int fanAnimaPicID = getResources().getIdentifier("class_5_3_d" + i , "drawable", getPackageName());
            Drawable fanAnimaPicDrawable = getResources().getDrawable(fanAnimaPicID);
            mDoorCloseAnimaDrawable.addFrame(fanAnimaPicDrawable, 40);
        }
        mDoorCloseAnimaDrawable.setOneShot(true);
    }

    private void startFan(){
        isFunRunning = true;
        mFanIV.setImageDrawable(mFanAnimaDrawable);
        mFanAnimaDrawable.stop();
        mFanAnimaDrawable.start();
    }

    private void stopFan(){
        isFunRunning = false;
        mFanIV.setImageDrawable(mFanAnimaDrawable);
        mFanAnimaDrawable.stop();
    }

    private void openDoor(){
        isDoorOpen = true;
        mDoorIV.setImageDrawable(mDoorOpenAnimaDrawable);
        mDoorOpenAnimaDrawable.stop();
        mDoorOpenAnimaDrawable.start();
    }

    private void closeDoor(){
        isDoorOpen = false;
        mDoorIV.setImageDrawable(mDoorCloseAnimaDrawable);
        mDoorCloseAnimaDrawable.stop();
        mDoorCloseAnimaDrawable.start();
    }

    private void initListener(){
        mDoorIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoorOpen){
                    closeDoor();
                } else {
                    openDoor();
                }
            }
        });
        mFanIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFunRunning){
                    stopFan();
                } else {
                    startFan();
                }
            }
        });
        mStatusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state){
                    if (thread.getState() == Thread.State.TERMINATED){
                        thread.run();
                    } else {
                        if (!thread.isAlive()){
                            thread.start();
                        }
                    }
                    state = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStatusTV.setText("逻辑状态：开");
                        }
                    });
                } else {
                    thread.interrupt();
                    state = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mStatusTV.setText("逻辑状态：关");
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFourInput.closePort();
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String data = (String)msg.obj;
            switch (msg.what){
                case 0:
                    mTempTV.setText("温度感应:" + data);
                    break;
                case 1:
                    mHumiTV.setText("湿度感应:" + data);
                    break;
                case 2:
                    mLightTV.setText("光照感应:" + data);
                    break;
                case 0x11:
                    mTempTV.setText("温度感应:" + (Math.random() * 40));
            }
        }
    };

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                while (!thread.currentThread().isInterrupted()){
                    Message message = Message.obtain();
                    message.what = 0x11;
                    mHandler.sendMessage(message);
                    Thread.sleep(1500);
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.e(this.getClass().getSimpleName(), e.toString());
            }
        }
    });


}

