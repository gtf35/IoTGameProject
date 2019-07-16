package top.gtf35.iotgameproject.classes_5.class_5_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.newland.cameralib.CameraManager;

import top.gtf35.iotgameproject.R;

public class Class_5_2 extends AppCompatActivity {

    CameraManager mCameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_5_2);
        WebView mWebView = findViewById(R.id.webView1);
        mCameraManager = CameraManager.getInstance();
        mCameraManager.startCamera("1892.168.x.x", mWebView);
    }

    public void myClick(View view){
        switch (view.getId()){
            case R.id.imgUp:
                mCameraManager.cameraUp();
                stopAll();
                break;
            case R.id.imgDown:
                mCameraManager.cameraDown();
                stopAll();
                break;
            case R.id.imgLeft:
                mCameraManager.cameraLeft();
                stopAll();
                break;
            case R.id.imgRight:
                mCameraManager.cameraRight();
                stopAll();
                break;
        }
    }

    private void stopAll(){
        mCameraManager.cameraStopUp();
        mCameraManager.cameraStopDown();
        mCameraManager.cameraStopLeft();
        mCameraManager.cameraStopRight();
    }
}
