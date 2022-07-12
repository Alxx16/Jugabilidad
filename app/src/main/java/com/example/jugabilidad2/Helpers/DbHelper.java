package com.example.jugabilidad2.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private String pareo = "CREATE TABLE pareo (id INTEGER PRIMARY KEY AUTOINCREMENT,pregunta_id TEXT ,tematica_id TEXT, orden_pareo INTEGER, texto TEXT,audio TEXT)";

    public DbHelper(Context context, String dbName){
        super(context,dbName,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(pareo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
