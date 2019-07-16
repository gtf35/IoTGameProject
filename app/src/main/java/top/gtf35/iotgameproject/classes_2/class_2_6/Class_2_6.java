package top.gtf35.iotgameproject.classes_2.class_2_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import top.gtf35.iotgameproject.R;

public class Class_2_6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_2_6);

        ListView listView = findViewById(R.id.ll);
        List<Class_2_6_ItemBean> list = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            Class_2_6_ItemBean itemBean = new Class_2_6_ItemBean();
            itemBean.setDdh("20150828104" + i);
            itemBean.setJe("1" + i);
            itemBean.setZt("已发货");
            itemBean.setTjsj("2015-08-28 10:0" + i);
            itemBean.setCz(" ");
            list.add(itemBean);
        }
        Class_2_6_Adapter adapter = new Class_2_6_Adapter(this, R.layout.item_activity_class_2_6, list);
        listView.setAdapter(adapter);
    }
}
