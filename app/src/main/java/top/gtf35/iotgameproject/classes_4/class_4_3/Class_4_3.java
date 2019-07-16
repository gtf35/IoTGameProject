package top.gtf35.iotgameproject.classes_4.class_4_3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import top.gtf35.iotgameproject.R;

public class Class_4_3 extends AppCompatActivity {

    private WebView webView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            WebView.enableSlowWholeDocumentDraw();//为了截图，关闭优化
        }
        setContentView(R.layout.activity_class_4_3);
        webView = findViewById(R.id.webview);
        button = findViewById(R.id.btn_take_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Class_4_3.this, "begin", Toast.LENGTH_SHORT).show();
                Class_4_3_FileService service = new Class_4_3_FileService(Class_4_3.this);
                service.saveBitmapToSDCard("WebViewCapture.png", captureWebView(webView));
                Toast.makeText(Class_4_3.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://cn.bing.com/");

    }

    private Bitmap captureWebView(WebView webView){
        try {
            Picture picture = webView.capturePicture();
            int width = picture.getWidth();
            int height = picture.getHeight();
            if (height <= 0 || width <= 0) return null;
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            picture.draw(canvas);
            return bitmap;
        } catch (Exception e){
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), e.toString());
            return null;
        }
    }
}
