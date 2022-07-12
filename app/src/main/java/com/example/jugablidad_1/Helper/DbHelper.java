package com.example.jugablidad_1.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private Context context;

    private String Preguntas_respuestas = "CREATE TABLE preguntas_respuestas (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "pregunta_id INTEGER," +
            "tematica_id INTEGER," +
            "modo INTEGER," +
            "pregunta TEXT," +
            "respuesta TEXT," +
            "retroalimentacion TEXT," +
            "opc INTEGER," +
            "imagen_pregunta TEXT," +
            "audio_pregunta TEXT," +
            "imagen_respuesta TEXT," +
            "audio_respuesta TEXT," +
            "audio_retroalimentacion TEXT" +
            ")";

    private String Usuarios = "CREATE TABLE usuarios (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre TEXT,apellido TEXT, correo TEXT, cedula TEXT, contrasena TEXT,facultad TEXT,carrera TEXT,grupo TEXT)";

    private String pareo = "CREATE TABLE pareo (id INTEGER PRIMARY KEY AUTOINCREMENT,pregunta_id TEXT ,tematica_id TEXT, orden_pareo INTEGER, texto TEXT, audio TEXT)";


    public DbHelper(Context context, String dbName){
        super(context,
                dbName,
                null,
                1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Usuarios);
        sqLiteDatabase.execSQL(Preguntas_respuestas);
        sqLiteDatabase.execSQL(pareo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
