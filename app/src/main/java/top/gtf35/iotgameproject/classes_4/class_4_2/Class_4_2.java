package top.gtf35.iotgameproject.classes_4.class_4_2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

import top.gtf35.iotgameproject.R;

public class Class_4_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_4_2);
        Class_4_2_DBHelper dbHelper = new Class_4_2_DBHelper(this, "info.db", null, 1);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", "伊利畅轻牛奶" + getRandomString(4));
        values.put("info", "一盒" + getRandomString(4));
        values.put("price", 1);
        sqLiteDatabase.insert("info", null, values);
        Log.w(this.getClass().getSimpleName(), "Insert data success");
        Cursor cursor = sqLiteDatabase.query("Info", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do {
                String user = "名字:" + cursor.getString(cursor.getColumnIndex("name"));
                user = user + "\n说明:" + cursor.getString(cursor.getColumnIndex("info"));
                user = user + "\n价格:" + cursor.getInt(cursor.getColumnIndex("price"));
                Log.w(this.getClass().getSimpleName(), user);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
    }

    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
