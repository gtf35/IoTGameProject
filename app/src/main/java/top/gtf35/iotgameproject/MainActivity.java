package top.gtf35.iotgameproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpActivity(View v){
        try {
            String tag = v.getTag().toString();
            Class<?> targetActivity =Class.forName(tag);
            startActivity(new Intent(MainActivity.this, targetActivity));
        } catch (Exception e){
            Toast.makeText(MainActivity.this, "出错了：\n" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
