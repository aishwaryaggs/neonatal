package com.neonatal.monitoring.system;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHalper extends SQLiteOpenHelper {


    public MyDbHalper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        

        db.execSQL("CREATE TABLE police(id integer primary key, name text, phone text," +
                " latitude long, longitude long)");
        
        db.execSQL("CREATE TABLE hospital(id integer primary key, name text, phone text," +
                " latitude long, longitude long)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "police");

        onCreate(db);
    }
}
