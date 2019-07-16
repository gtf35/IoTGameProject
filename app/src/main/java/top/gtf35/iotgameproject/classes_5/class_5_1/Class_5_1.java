package top.gtf35.iotgameproject.classes_5.class_5_1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import top.gtf35.iotgameproject.R;

public class Class_5_1 extends AppCompatActivity {

    private TextView mFireTV, mSmokeTV, mPresionTV;
    private ImageView mFireImg;
    private ADAM4150 mADAM4150 = new ADAM4150(1, 0, 3);
    private int ms = 300;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(mRunnable, ms);
            mPresionTV.setText("非法入侵：   " + (mADAM4150.isFire() ? "有人": "无人"));
            mFireTV.setText("火焰：   " + (mADAM4150.isFire() ? "有火": "无火"));
            if (mADAM4150.isFire()){
                mFireImg.setVisibility(View.VISIBLE);
            } else {
                mFireImg.setVisibility(View.GONE);
            }
            mADAM4150.send4150();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_5_1);
        mFireTV = findViewById(R.id.tv_fire);
        mPresionTV = findViewById(R.id.tv_persion);
        mSmokeTV = findViewById(R.id.tv_smoke);
        mFireImg = findViewById(R.id.img_fire);
        mHandler.postDelayed(mRunnable, ms);
    }
}
