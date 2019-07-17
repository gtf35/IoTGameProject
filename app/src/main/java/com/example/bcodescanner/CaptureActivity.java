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

public class CaptureActivity extends Activity implements SurfaceHolder.Callback {

    private ViewfinderView mViewfinderView;
    private InactivityTimer mInactivityTimer;
    private Vector<BarcodeFormat> mBarcodeFormats;
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
        CameraManager.init(getApplicationContext());
        mInactivityTimer = new InactivityTimer(this);
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

    private void handleDecode(Result result, Bitmap barcode){
        mInactivityTimer.onActivity();
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
