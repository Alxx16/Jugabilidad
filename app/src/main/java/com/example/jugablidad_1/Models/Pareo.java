package com.example.jugablidad_1.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.jugablidad_1.Helper.DbHelper;
import com.example.jugablidad_1.Models.Responses.PareoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pareo {

    private int pregunta_id;
    private int tematica_id;
    private int orden_pareo;
    private String texto;
    private String audio;


    public Pareo(int pregunta_id, int tematica_id, int orden_pareo, String texto, String audio) {
        this.pregunta_id = pregunta_id;
        this.tematica_id = tematica_id;
        this.orden_pareo = orden_pareo;
        this.texto = texto;
        this.audio = audio;
    }

    public Pareo(String texto, String audio) {
        this.texto = texto;
        this.audio = audio;
    }

    public Pareo() {}

    public void pareoInsert(Context context){
        try{
            DbHelper dbHelper = new DbHelper(context,"proyecto_ds6");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if(db!= null){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("pregunta_id",this.getPregunta_id());
                    contentValues.put("tematica_id",this.getTematica_id());
                    contentValues.put("orden_pareo",this.getOrden_pareo());
                    contentValues.put("texto",this.getTexto());
                    //System.out.println("ENTROOOOOOOOOOOOOOOOOOOOOOO");
                    db.insert("pareo",null,contentValues);
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en insercion =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public List<Pareo> obtenerPareo(int pregunta, Context context){
        List<Pareo> pareo = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context,"proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                String[] campos = new String[]{"texto","audio"};
                Cursor cursor = db.query("pareo",campos,"pregunta_id= "+pregunta,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        Pareo pareoo = new Pareo(
                                cursor.getString(0),
                                cursor.getString(1)
                        );
                        pareo.add(pareoo);
                    }while (cursor.moveToNext());
                }
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en obtener datos de pareo =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return pareo;
    }

    public static List<PareoResponse> reordenarPareo(List<PareoResponse> pareo) {
        Collections.shuffle(pareo);
        return pareo;
    }

    public static void actualizarPareo(Context context) {
        DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tabla = "pareo";
        try {
            db.delete(tabla, null, null);
        } catch (Exception e) {}
    }


    public int getPregunta_id() {
        return pregunta_id;
    }

    public void setPregunta_id(int pregunta_id) {
        this.pregunta_id = pregunta_id;
    }

    public int getTematica_id() {
        return tematica_id;
    }

    public void setTematica_id(int tematica_id) {
        this.tematica_id = tematica_id;
    }

    public int getOrden_pareo() {
        return orden_pareo;
    }

    public void setOrden_pareo(int orden_pareo) {
        this.orden_pareo = orden_pareo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }



}
