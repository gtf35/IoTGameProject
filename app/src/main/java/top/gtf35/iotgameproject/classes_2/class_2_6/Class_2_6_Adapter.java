package top.gtf35.iotgameproject.classes_2.class_2_6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import top.gtf35.iotgameproject.R;

public class Class_2_6_Adapter extends ArrayAdapter {

    private int mLayoutID;

    public Class_2_6_Adapter(Context context, int layoutID, List<Class_2_6_ItemBean> items) {
        super(context, layoutID, items);
        mLayoutID = layoutID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Class_2_6_ItemBean item = (Class_2_6_ItemBean)getItem(position);
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(mLayoutID, parent, false);
        } else {
            view = convertView;
        }
        TextView tv;
        tv = view.findViewById(R.id.tv_item_ddh);
        tv.setText(item.getDdh());
        tv = view.findViewById(R.id.tv_item_je);
        tv.setText(item.getJe());
        tv = view.findViewById(R.id.tv_item_zt);
        tv.setText(item.getZt());
        tv = view.findViewById(R.id.tv_item_tjsj);
        tv.setText(item.getTjsj());
        tv = view.findViewById(R.id.tv_item_cz);
        tv.setText(item.getCz());
        return view;
    }
}
