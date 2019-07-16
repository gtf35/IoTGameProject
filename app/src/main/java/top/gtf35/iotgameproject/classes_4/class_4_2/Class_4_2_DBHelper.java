package top.gtf35.iotgameproject.classes_4.class_4_2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Class_4_2_DBHelper extends SQLiteOpenHelper {

    private final String CREATE_SQL = "create table Info ( " +
            "id integer primary key autoincrement ," +
            "name text ," +
            "info text ," +
            "price integer" +
            ")";

    public Class_4_2_DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
        Log.w(this.getClass().getSimpleName(), "Create table finish");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
