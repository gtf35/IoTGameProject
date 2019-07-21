package top.gtf35.iotgameproject.fire;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import top.gtf35.iotgameproject.R;

public class FireActivity extends AppCompatActivity {

    private AnimationDrawable mFireAnimaDrawable, mSmokeAnimaDrawable;
    private ImageView mFireImageView, mSmokeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);
        mFireImageView = findViewById(R.id.iv_fire);
        mFireAnimaDrawable = getAnimaDrawableFromOnePic(R.drawable.file, 3, 4, 100, false);
        mFireImageView.setImageDrawable(mFireAnimaDrawable);
        mFireAnimaDrawable.start();

        mSmokeImageView = findViewById(R.id.iv_smoke);
        mSmokeAnimaDrawable = getAnimaDrawableFromOnePic(R.drawable.smoke, 3, 4, 100, false);
        mSmokeImageView.setImageDrawable(mSmokeAnimaDrawable);
        mSmokeAnimaDrawable.start();
    }

    /**
     * 通过一整张图片返回AnimaDrawable
     * @author gtf35
     * @version 1.0
     * @param picID 一整张图片的资源id
     * @param xNum 在 X 轴上有几帧
     * @param yNum 在 Y 轴上有几帧
     * @param ms 每一帧的停留的毫秒数
     * @param setOneShot 是否只播放一次
     * @return AnimaDrawable
     */
    private AnimationDrawable getAnimaDrawableFromOnePic(int picID, int xNum, int yNum, int ms, boolean setOneShot){
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), picID);//原始图片bitmap对象
        Drawable[]  eachList = new Drawable[ xNum * yNum ];//准备数组存放切割后的bmp
        int eachWidth =  originalBitmap.getWidth() / xNum;//计算一帧的宽度
        int eachHeight = originalBitmap.getHeight() / yNum ;//计算一帧的高度
        //遍历切割
        //y轴
        for (int y = 0; y < yNum; y++){
            //x轴
            for (int x = 0; x < xNum; x++){
                int bmpX = x * eachWidth;//计算切割起点x坐标
                int bmpY = y * eachHeight;//计算切割起点y坐标
                //切割图片                                原始素材     起始x 起始y  切割宽度         切割高度
                Bitmap thisBmp = Bitmap.createBitmap(originalBitmap, bmpX, bmpY, eachWidth, eachHeight);
                Drawable thisDrawable = new BitmapDrawable(thisBmp);//把bitmap转换成Drawable便于做动画
                int listIndex = ((xNum * yNum) - xNum + 1 ) - (y * 3) + x -1;//计算按播放顺序应该是第几帧，画一个坐标图就能算出来
                Log.w("listIndex", "i=" + listIndex + " x=" + x + " y=" + y);
                eachList[listIndex] = thisDrawable;//放入数组，最后数组是有序的
            }
        }
        AnimationDrawable finishAnimaDrawable = new AnimationDrawable();
        for (int i = 0; i < eachList.length; i++){
            Log.w("index", "i=" + i);
            finishAnimaDrawable.addFrame(eachList[i], ms);//正放
        }
        for (int i = eachList.length - 1 ; i >=0; i--){
            Log.w("index", "i=" + i);
            finishAnimaDrawable.addFrame(eachList[i], ms);//倒放
        }
        finishAnimaDrawable.setOneShot(setOneShot);
        return finishAnimaDrawable;
    }
}
