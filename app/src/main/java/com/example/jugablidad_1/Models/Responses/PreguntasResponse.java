package com.example.jugablidad_1.Models.Responses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.jugablidad_1.Helper.DbHelper;

import java.util.Collections;
import java.util.List;

public class PreguntasResponse {
    private int id;
    private int modo_id;
    private int tematica_id;
    private String pregunta;
    private String opcion_resp;
    private String retroalimentacion;
    private int respuesta;
    private String imagen_pregunta;
    private String audio_pregunta;
    private String imagen_respuesta;
    private String audio_respuesta;
    private String audio_retroalimentacion;

    public static final int CANTIDAD_PREGUNTAS = 10;


    public static void actualizarPreguntas(Context context) {
        DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tabla = "preguntas_respuestas";
        try {
            db.delete(tabla, null, null);
        } catch (Exception e) {}
    }

    public void guardarPreguntas(Context context, PreguntasResponse preguntasResponse) {
        try {
            DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int x = 0;
            if(db != null) {
                ContentValues content = new ContentValues();
                content.put("pregunta_id", preguntasResponse.getId());
                content.put("tematica_id", preguntasResponse.getTematica_id());
                content.put("modo", preguntasResponse.getModo_id());
                content.put("pregunta", preguntasResponse.getPregunta());
                content.put("respuesta", preguntasResponse.getOpcion_resp());
                content.put("retroalimentacion", preguntasResponse.getRetroalimentacion());
                content.put("opc", preguntasResponse.getRespuesta());
                content.put("imagen_pregunta", preguntasResponse.getImagen_pregunta());
                content.put("audio_pregunta", preguntasResponse.getAudio_pregunta());
                content.put("imagen_respuesta", preguntasResponse.getImagen_respuesta());
                content.put("audio_respuesta", preguntasResponse.getAudio_respuesta());
                content.put("audio_retroalimentacion", preguntasResponse.getAudio_retroalimentacion());

                db.insert("Preguntas_respuestas",null,content);
            }
        } catch (Exception e) {
            Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static List<PreguntasResponse> reordenarPreguntas(List<PreguntasResponse> preguntas) {
        Collections.shuffle(preguntas);
        return preguntas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModo_id() {
        return modo_id;
    }

    public void setModo_id(int modo_id) {
        this.modo_id = modo_id;
    }

    public int getTematica_id() {
        return tematica_id;
    }

    public void setTematica_id(int tematica_id) {
        this.tematica_id = tematica_id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion_resp() {
        return opcion_resp;
    }

    public void setOpcion_resp(String opcion_resp) {
        this.opcion_resp = opcion_resp;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public String getImagen_pregunta() {
        return imagen_pregunta;
    }

    public void setImagen_pregunta(String imagen_pregunta) {
        this.imagen_pregunta = imagen_pregunta;
    }

    public String getAudio_pregunta() {
        return audio_pregunta;
    }

    public void setAudio_pregunta(String audio_pregunta) {
        this.audio_pregunta = audio_pregunta;
    }

    public String getImagen_respuesta() {
        return imagen_respuesta;
    }

    public void setImagen_respuesta(String imagen_respuesta) {
        this.imagen_respuesta = imagen_respuesta;
    }

    public String getAudio_respuesta() {
        return audio_respuesta;
    }

    public void setAudio_respuesta(String audio_respuesta) {
        this.audio_respuesta = audio_respuesta;
    }

    public String getAudio_retroalimentacion() {
        return audio_retroalimentacion;
    }

    public void setAudio_retroalimentacion(String audio_retroalimentacion) {
        this.audio_retroalimentacion = audio_retroalimentacion;
    }
}
