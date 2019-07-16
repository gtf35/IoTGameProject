package top.gtf35.iotgameproject.classes_3.class_3_2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import top.gtf35.iotgameproject.R;

public class Class_3_2 extends Activity {

    private TextView outputTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_3_2);
        outputTV = findViewById(R.id.tv1);
        output("==>onCreate");
    }

    private void output(String text){
        outputTV.setText(outputTV.getText().toString() + "\n" + text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        output("==>onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        output("==>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        output("==>onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        output("==>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        output("==>onDestroy");
    }
}
