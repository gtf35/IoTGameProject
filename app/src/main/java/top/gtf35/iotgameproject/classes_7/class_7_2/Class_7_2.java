package top.gtf35.iotgameproject.classes_7.class_7_2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;

import java.util.Vector;

import top.gtf35.iotgameproject.R;

public class Class_7_2 extends Activity implements SurfaceHolder.Callback {

    private ViewfinderView mViewfinderView;
    private InactivityTimer mInactivityTimer;
    private Vector<BarcodeFormat> mBarcodeFormats;
    private CaptureActivityHandler mCaptureActivityHandler;
    private String mCharacterSet;
    private boolean mHasSurface = false;

    private void initView(){
        mViewfinderView = findViewById(R.id.viewfindview);
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
        if (mHasSurface){

        }
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

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
