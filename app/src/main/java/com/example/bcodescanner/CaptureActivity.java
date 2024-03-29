package com.example.bcodescanner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.util.Vector;

import top.gtf35.iotgameproject.R;

/*
* 此活动打开相机并在后台线程上进行实际扫描。
* 它绘制取景器以帮助用户正确放置条形码，
* 在图像处理过程中显示反馈，
* 然后在扫描成功时覆盖结果。
* */

public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    /*预览界面*/
    /*此视图覆盖在相机预览的顶部，就是扫描的预览还有扫描动画*/
    //https://github.com/zxing/zxing/blob/master/android/src/com/google/zxing/client/android/ViewfinderView.java
    private ViewfinderView mViewfinderView;
    /*闲置倒计时，用于电池供电时在闲置时节能*/
    //https://github.com/zxing/zxing/blob/master/android/src/com/google/zxing/client/android/InactivityTimer.java
    private InactivityTimer mInactivityTimer;
    /*BarcodeFormat是个枚举类，用于描述已知的条码类型*/
    //https://github.com/zxing/zxing/blob/master/core/src/main/java/com/google/zxing/BarcodeFormat.java
    private Vector<BarcodeFormat> mBarcodeFormats;
    /*此类处理包含要捕获的状态机的所有消息传递*/
    //https://github.com/zxing/zxing/blob/master/android/src/com/google/zxing/client/android/CaptureActivityHandler.java
    private CaptureActivityHandler mCaptureActivityHandler;
    private String mCharacterSet;
    private boolean mPlayBeep, mVibrate;
    private MediaPlayer mMediaPlayer;
    private boolean mHasSurface = false;
    private final static long VIBRATE_DURATION = 200L;
    private Button mExitButton;

    private void initView() {
        mViewfinderView = findViewById(R.id.viewfindview);
        mExitButton = findViewById(R.id.exit_btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_7_2);
        initView();
        /*
        * CameraManager：此对象包装Camera服务对象，并期望成为唯一与之对话的对象。 该实现封装了拍摄预览大小图像所需的步骤，这些图像用于预览和解码。
        * */
        CameraManager.init(getApplicationContext());
        mInactivityTimer = new InactivityTimer(this);//闲置倒计时
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.surfaceview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (mHasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        mBarcodeFormats = null;
        mCharacterSet = null;
        mPlayBeep = true;
        AudioManager audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL){
            mPlayBeep = false;
        }
        initBeepSound();
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVibrate = true;

    }

    @Override
    protected void onDestroy() {
        mInactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mCaptureActivityHandler != null){
            mCaptureActivityHandler.quitSynchronously();
            mCaptureActivityHandler = null;
        }
        CameraManager.get().closeDriver();
    }

    /*
    * 找到了有效的条形码，因此请说明成功并显示结果
    * */
    private void handleDecode(Result result, Bitmap barcode){
        mInactivityTimer.onActivity();
        /*beep或震动，并且我们有一张图片去绘制*/
        mPlayBeepSoundAndVibrate();
        String resultString = result.getText();
        if (TextUtils.isEmpty(resultString)){
            Toast.makeText(this, "扫描失败", Toast.LENGTH_SHORT).show();
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("result", resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
        }
        this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder){
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (Exception e){
            e.printStackTrace();
            Log.w(getClass().getSimpleName(), e.toString());
            return;
        }
        if (mCaptureActivityHandler == null)
            mCaptureActivityHandler = new CaptureActivityHandler(this, mBarcodeFormats, mCharacterSet);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHasSurface = true;
        initCamera(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return mViewfinderView;
    }

    public void drawViewFinder(){
        mViewfinderView.drawViewfinder();
    }

    private void initBeepSound(){

        if (mPlayBeep && mMediaPlayer == null){
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnCompletionListener(mBeepListener);
            AssetFileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.beep);
            try {
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                fileDescriptor.close();
                mMediaPlayer.setVolume(1,1);
                mMediaPlayer.prepare();
            } catch (Exception e){
                mMediaPlayer = null;
                e.printStackTrace();
                Log.w(getClass().getSimpleName(), e.toString());
            }
        }
    }

    private void mPlayBeepSoundAndVibrate(){
        if (mPlayBeep && mMediaPlayer != null){
            mMediaPlayer.start();
        }
        if (mVibrate){
            Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private final MediaPlayer.OnCompletionListener mBeepListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {
            mMediaPlayer.seekTo(0);
        }
    };
}
