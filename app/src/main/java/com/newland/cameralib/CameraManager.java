//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.cameralib;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import com.newland.cameralib.HttpUtils.CallBack;
import com.newland.cameralib.HttpUtils.ImageCallBack;

@SuppressLint({"NewApi"})
public class CameraManager {
    private static CameraManager instance;
    private static final String CONTROL_OLD_CAMERAURL = "http://%s/decoder_control.cgi?command=%s&user=admin&pwd=";
    private static final String CONTROL_NEW_CAMERAURL = "http://%s/decoder_control.cgi?command=%s&loginuse=admin&loginpas=";
    private static final String CONNECT_CAMERAURL = "http://%s/snapshot.cgi?user=admin&pwd=&strm=0&resolution=32";
    private static final String HTTP_RESULT_OK = "ok";
    private static final String HTTP_RESULT_ERROR_CAMERA = "401";
    private String address;
    private WebView webview;
    private String url;
    private boolean isOld = false;
    private boolean isRun = false;
    private HttpUtils httpUtils = new HttpUtils();
    private int ms = 500;
    private Handler mHandler = null;
    private Runnable update = new Runnable() {
        public void run() {
            if (CameraManager.this.isRun) {
                CameraManager.this.mHandler.postDelayed(CameraManager.this.update, (long)CameraManager.this.ms);
                CameraManager.this.updateWebView();
            }

        }
    };

    public CameraManager() {
    }

    public static CameraManager getInstance() {
        if (instance == null) {
            Class var0 = CameraManager.class;
            synchronized(CameraManager.class) {
                if (instance == null) {
                    instance = new CameraManager();
                }
            }
        }

        return instance;
    }

    public void startCamera(String address, WebView webview) {
        this.webview = webview;
        this.address = address;
        this.url = String.format("http://%s/snapshot.cgi?user=admin&pwd=&strm=0&resolution=32", address);
        this.updateWebView();
        this.mHandler = new Handler();
        this.mHandler.post(this.update);
        this.isRun = true;
    }

    public void startCamera(String address) {
        this.address = address;
        this.url = String.format("http://%s/snapshot.cgi?user=admin&pwd=&strm=0&resolution=32", address);
        this.isRun = true;
    }

    private void updateWebView() {
        if (this.webview != null && !TextUtils.isEmpty(this.url)) {
            this.webview.loadUrl(this.url);
        }

    }

    public void stopCamera() {
        this.isRun = false;
    }

    public void cameraUp() {
        this.SendControl(0);
    }

    public void cameraStopUp() {
        this.SendControl(1);
    }

    public void cameraDown() {
        this.SendControl(2);
    }

    public void cameraStopDown() {
        this.SendControl(3);
    }

    public void cameraLeft() {
        this.SendControl(4);
    }

    public void cameraStopLeft() {
        this.SendControl(5);
    }

    public void cameraRight() {
        this.SendControl(6);
    }

    public void cameraStopRight() {
        this.SendControl(7);
    }

    public void screenshotCamera(ImageCallBack imageCallBack) {
        HttpUtils.doGetImageAsyn(this.url, imageCallBack);
    }

    public void getCurrentBitmap(ImageCallBack callBack) {
        if (this.url != null) {
            HttpUtils.doGetImageAsyn(this.url, callBack);
        }
    }

    public boolean getRunstatus() {
        return this.isRun;
    }

    public Bitmap getCurrentBitmap() {
        return this.url == null ? null : HttpUtils.getBitmap(this.url);
    }

    private void SendControl(final int control) {
        if (this.address.length() != 0) {
            String controlUrl = !this.isOld ? String.format("http://%s/decoder_control.cgi?command=%s&loginuse=admin&loginpas=", this.address, control) : String.format("http://%s/decoder_control.cgi?command=%s&user=admin&pwd=", this.address, control);
            HttpUtils.doGetAsyn(controlUrl, new CallBack() {
                public void onRequestComplete(String result) {
                    Log.d("result", result);
                    if (result == "401") {
                        CameraManager.this.isOld = true;
                        CameraManager.this.SendControl(control);
                    }

                }
            });
        }
    }
}
