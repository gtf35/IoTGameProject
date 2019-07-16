package top.gtf35.iotgameproject.classes_4.class_4_1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import top.gtf35.iotgameproject.R;

public class Class_4_1 extends AppCompatActivity implements View.OnClickListener {

    private EditText mTempET1, mTempET2, mHumiET, mLightET, mCOET;
    private Button mSaveBtn, mClearBtn, mBtnRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_4_1);
        mTempET1 = findViewById(R.id.et_temp1);
        mTempET2 = findViewById(R.id.et_temp2);
        mCOET = findViewById(R.id.et_co);
        mHumiET = findViewById(R.id.et_humi);
        mLightET = findViewById(R.id.et_light);
        mSaveBtn = findViewById(R.id.btn_save);
        mSaveBtn.setOnClickListener(this);
        mClearBtn = findViewById(R.id.btn_reset);
        mClearBtn.setOnClickListener(this);
        mBtnRead = findViewById(R.id.btn_read);
        mBtnRead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_read:
                actionRead();
                break;
            case R.id.btn_save:
                actionSave();
                break;
            case R.id.btn_reset:
                actionClear();
                break;
            default:
                break;
        }
    }

    private void saveSP(String key, String str){
        SharedPreferences sp = getSharedPreferences("zhcs", 0);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(key, str);
        spe.apply();
    }

    private String getSP(String key, String defaultStr){
        SharedPreferences sp = getSharedPreferences("zhcs", 0);
        return sp.getString(key, defaultStr);
    }

    private void actionRead(){
        mTempET1.setText(getSP(Integer.toString(mTempET1.getId()), ""));
        mTempET2.setText(getSP(Integer.toString(mTempET2.getId()), ""));
        mHumiET.setText(getSP(Integer.toString(mHumiET.getId()), ""));
        mLightET.setText(getSP(Integer.toString(mLightET.getId()), ""));
        mCOET.setText(getSP(Integer.toString(mCOET.getId()), ""));
    }

    private void actionSave(){
        saveSP(Integer.toString(mTempET1.getId()), mTempET1.getText().toString());
        saveSP(Integer.toString(mTempET2.getId()), mTempET2.getText().toString());
        saveSP(Integer.toString(mHumiET.getId()), mHumiET.getText().toString());
        saveSP(Integer.toString(mLightET.getId()), mLightET.getText().toString());
        saveSP(Integer.toString(mCOET.getId()), mCOET.getText().toString());
        Toast.makeText(this, "读取成功!", Toast.LENGTH_SHORT).show();
    }

    private void actionClear(){
        mTempET1.setText("");
        mTempET2.setText("");
        mHumiET.setText("");
        mLightET.setText("");
        mCOET.setText("");
    }
}
