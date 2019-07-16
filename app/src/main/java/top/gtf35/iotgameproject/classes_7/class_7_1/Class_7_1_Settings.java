package top.gtf35.iotgameproject.classes_7.class_7_1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import top.gtf35.iotgameproject.R;

public class Class_7_1_Settings extends AppCompatActivity {

    private EditText mTempETStart, mTempETEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_7_1__settings);
        mTempETStart = findViewById(R.id.et_temp_start);
        mTempETEnd = findViewById(R.id.et_temp_end);
    }

    public void save(View view){
        String input = mTempETStart.getText().toString();
        if (TextUtils.isEmpty(input)) return;
        Intent intent = new Intent();
        intent.putExtra("temp", Double.parseDouble(input));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
