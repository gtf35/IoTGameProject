package top.gtf35.iotgameproject.classes_2.class_2_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import top.gtf35.iotgameproject.R;

public class Class_2_2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_2_2);
        Spinner spinner = findViewById(R.id.sp1);
        spinner.setPrompt("请选择支付方式");
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this ,R.array.mode, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
    }
}
