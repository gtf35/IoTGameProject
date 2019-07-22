package top.gtf35.iotgameproject.bitmap_learn;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import top.gtf35.iotgameproject.R;

public class BitmapTest extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_test);
        mImageView = findViewById(R.id.iv_bitmaplearn);
        //mImageView.setImageResource(R.drawable.large_img_2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.large_img_2)
    }
}
