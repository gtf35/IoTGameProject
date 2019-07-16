package top.gtf35.iotgameproject.classes_3.class_3_3;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import top.gtf35.iotgameproject.R;

public class Class_3_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_3_3);
        findViewById(R.id.btn_opencamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
            }
        });
    }
}
