package com.example.jugabilidad2.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.jugabilidad2.Helpers.DbHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Pareo() {

    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
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

    public boolean pareoInsert(Context context){
        try{
            DbHelper dbHelper = new DbHelper(context,"frograming");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if(db!= null){
                ContentValues contentValues = new ContentValues();
                contentValues.put("pregunta_id",this.getPregunta_id());
                contentValues.put("tematica_id",this.getTematica_id());
                contentValues.put("orden_pareo",this.getOrden_pareo());
                contentValues.put("texto",this.getTexto());
                contentValues.put("audio",this.getAudio());
                db.insert("pareo",null,contentValues);
                return true;
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en insercion =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return false;
    }

    /*#Método utilizado a través de la utilización de la tematica por shared preferences
    public int obtenerIdPreguntas(Context context){
        List<Integer> idPreguntas = new ArrayList<>();
        int idAleatorio;
        try{//Validacion de que campos en nullo o vacio
            DbHelper dbHelper = new DbHelper(context,"frograming");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!= null){
                String[] campos = new String[]{"pregunta_id"};
                //TRAER TEMATICA A TRAVES DE SHAREDPREFERENCES
                Cursor cursor = db.query("pareo",campos,"tematica_id = " + 1,null,"pregunta_id",null,null);
                if(cursor.moveToFirst()){
                    do {
                        idPreguntas.add(cursor.getInt(0));
                    }while (cursor.moveToNext());
                }
            }
        }catch (Exception e){
            Toast.makeText(context,"Errocito en select de ID PREGUNTAS =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        //RANDOMIZEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEER
        Random rand = new Random();
        idAleatorio=idPreguntas.get(rand.nextInt(idPreguntas.size()));
        return idAleatorio;
    }*/

    //#Método encargado de obtener todos las filas del pareo, según el id de pregunta que este asignada al parametro
    public List<Pareo> obtenerPareo(int pregunta,Context context){
        List<Pareo> pareoE = new ArrayList<>();
        try{
            DbHelper dbHelper = new DbHelper(context,"frograming");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                //Consulta que obtiene el texto y audio en la tabla pareo, según el id de pregunta del parametro
                String[] campos = new String[]{"texto","audio"};
                Cursor cursor = db.query("pareo",campos,"pregunta_id= "+pregunta ,null,null,null,null,"10");
                if(cursor.moveToFirst()){
                    do {
                        //#Asignar texto y audio al constructor de la clase Pareo(Entidad)
                        Pareo pareo = new Pareo(
                                cursor.getString(0),
                                cursor.getString(1)
                        );
                        //#agregar datos cargados en el constructor de Pareo(Entidad) a la lista de tipo pareo
                        pareoE.add(pareo);
                    }while (cursor.moveToNext());
                }
            }
        }catch (Exception e){
            Toast.makeText(context,"Error en obtener datos de pareo =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        //#Retornar lista cargada a la clase PareoActivity
        return pareoE;
    }
    //#Método encargado de obtener el id de la opción seleccionada por el texto en PareoActivity
    public int obtenerIdPareo(String texto, Context context){
        int id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context,"frograming");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                String[] campos = new String[]{"orden_pareo"};
                Cursor cursor = db.query("pareo",campos,"texto="+"'"+texto+"'",null,null,null,null,"1");
                if(cursor.moveToFirst()){
                    do {
                        id = cursor.getInt(0);
                    }while (cursor.moveToNext());
                }
            }
            return id;
        } catch (SQLiteException e){
            Toast.makeText(context,"Error en obtener datos de pareo SQLiteException =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(context,"Error en obtener datos de pareo =>"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return 0;
    }
}
