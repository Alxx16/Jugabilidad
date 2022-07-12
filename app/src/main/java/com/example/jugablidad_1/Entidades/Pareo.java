/*
 * Entidad de pareo
 * Autor: Camaño Rafael
 * UTP PANAMÁ
 */
package com.example.jugablidad_1.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

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
                    db.insert("pareo",null,contentValues);
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en insercion =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    //#Método encargado de obtener todos las filas del pareo, según el id de pregunta que este asignada al parametro
    public List<Pareo> obtenerPareo(int pregunta, Context context){
        List<Pareo> pareo = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context,"proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                //Consulta que obtiene el texto y audio en la tabla pareo, según el id de pregunta del parametro
                String[] campos = new String[]{"texto","audio"};
                Cursor cursor = db.query("pareo",campos,"pregunta_id= "+pregunta,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        //#Asignar texto y audio al constructor de la clase Pareo(Entidad)
                        Pareo pareoo = new Pareo(
                                cursor.getString(0),
                                cursor.getString(1)
                        );
                        //#agregar datos cargados en el constructor de Pareo(Entidad) a la lista de tipo pareo
                        pareo.add(pareoo);
                    }while (cursor.moveToNext());
                }
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en obtener datos de pareo =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        //#Retornar lista cargada a la clase PareoActivity
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
    //#Método encargado de obtener el id de la opción seleccionada por el texto en PareoActivity
    public int obtenerIdPareo(String texto, Context context){
        int id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context,"proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                String[] campos = new String[]{"orden_pareo"};
                Cursor cursor = db.query("pareo",campos,"texto = " +"'"+texto+"'",null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        id = cursor.getInt(0);
                    }while (cursor.moveToNext());
                }
            }
            //#Retorno del id del elemento seleccionado
            return id;
        } catch (SQLiteException e){
            Toast.makeText(context,"Error en obtener datos de pareo SQLiteException =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context,"Error en obtener datos de pareo =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return 0;
    }
}
