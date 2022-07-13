package com.example.jugablidad_1.Controller;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.jugablidad_1.Helper.DbHelper;
import com.example.jugablidad_1.Models.Pareo;
import com.example.jugablidad_1.Models.Responses.PareoResponse;
import com.example.jugablidad_1.Models.Responses.PreguntasResponse;
import com.example.jugablidad_1.Modo1_Activity;
import com.example.jugablidad_1.Modo2_Activity;
import com.example.jugablidad_1.Modo3_Activity;
import com.example.jugablidad_1.Modo4_Activity;
import com.example.jugablidad_1.PuntosTotalesActivity;
import com.example.jugablidad_1.Service.ApiInterface;
import com.example.jugablidad_1.Service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Jugabilidad {

    private ApiInterface api;
    private Context context;
    SharedPreferencesController sp = new SharedPreferencesController();


    public Jugabilidad(Context context) {
        this.context = context;
    }

    public void obtenerDatosJugabilidad(int id) {
        api = ApiService.getApiService();

        Call<List<PreguntasResponse>> call = api.getPreguntas(id);
        call.enqueue(new Callback<List<PreguntasResponse>>() {

            @Override
            public void onResponse(Call<List<PreguntasResponse>> call, Response<List<PreguntasResponse>> response) {
                if(response.isSuccessful()) {
                    PreguntasResponse.actualizarPreguntas(context);
                    List<PreguntasResponse> preguntas = PreguntasResponse.reordenarPreguntas(response.body());

                    for(PreguntasResponse preg : preguntas) {
                        preg.guardarPreguntas(context, preg);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<PreguntasResponse>> call, Throwable t) {
                int x = 1;
            }
        });
    }

    public void obtenerDatosPareo(int id) {
        api = ApiService.getApiService();
        Call<List<PareoResponse>> llamadoPareo = api.obtenerListadoPareo(id);
        llamadoPareo.enqueue(new Callback<List<PareoResponse>>() {
            @Override
            public void onResponse(Call<List<PareoResponse>> call, Response<List<PareoResponse>> response) {
                List<PareoResponse> listaPareo = response.body();
                Pareo.actualizarPareo(context);
                listaPareo = Pareo.reordenarPareo(listaPareo);
                for(PareoResponse PareoB : listaPareo){
                    Pareo pareo = new Pareo(
                            PareoB.getPregunta_id(),
                            PareoB.getTematica_id(),
                            PareoB.getOrden_pareo(),
                            PareoB.getTexto(),
                            PareoB.getAudio()
                    );
                    pareo.pareoInsert(context);
                }
            }
            @Override
            public void onFailure(Call<List<PareoResponse>> call, Throwable t) {
                int x = 1;
            }
        });
    }

    public void borrarDatosPreferences() {
        sp.guardar(context, "cant_modo_1", "");
        sp.guardar(context, "cant_modo_2", "");
        sp.guardar(context, "cant_modo_3", "");
        sp.guardar(context, "cant_modo_4", "");
        sp.guardar(context, "preguntas_id", "");
        sp.guardar(context, "preguntas_repetidas_modo", "");
    }

    public Intent cambiarModo() {
        Intent modo = null;
        int id_modo = 0;
        int id = obtenerSiguientePreguntaId();
        String m4 = sp.leer(context,"cant_modo_4");

        if(!m4.equals("")) {
            sp.guardar(context, "cant_modo_4", ""); // esto es para que solamente se repita una vez
            id_modo = 4;
        } else {
            id_modo = obtenerIdModo(id);
        }

        switch (id_modo) {
            case 0:
                modo = new Intent(context, PuntosTotalesActivity.class);
                break;
            case 1:
                modo = new Intent(context, Modo1_Activity.class);
                break;
            case 2:
                modo = new Intent(context, Modo2_Activity.class);
                break;
            case 3:
                modo = new Intent(context, Modo3_Activity.class);
                break;
            case 4:
                modo = new Intent(context, Modo4_Activity.class);
                break;
        }

        return modo;
    }

    public int validarCantidadModos(int id) {
        int cantidad = 0;

        String m1 = sp.leer(context,"cant_modo_1");
        String m2 = sp.leer(context,"cant_modo_2");
        String m3 = sp.leer(context,"cant_modo_3");

        int modo = obtenerIdModo(id);

        switch (modo) {
            case 1:
                    if(!m1.equals("")) {
                        cantidad = Integer.parseInt(m1)+1;
                        if(cantidad <= 3) {
                            sp.guardar(context, "cant_modo_1", cantidad+"");
                        } else {
                            return 0;
                        }
                    } else {
                        sp.guardar(context, "cant_modo_1", "1");
                    }
                break;
            case 2:
                    if(!m2.equals("")) {
                        cantidad = Integer.parseInt(m2)+1;
                        if(cantidad <= 3) {
                            sp.guardar(context, "cant_modo_2", cantidad+"");
                        } else {
                            return 0;
                        }
                    } else {
                        sp.guardar(context, "cant_modo_2", "1");
                    }
                break;
            case 3:
                    if(!m3.equals("")) {
                        cantidad = Integer.parseInt(m3)+1;
                        if(cantidad <= 3) {
                            sp.guardar(context, "cant_modo_3", cantidad+"");
                        } else {
                            return 0;
                        }
                    } else {
                        sp.guardar(context, "cant_modo_3", "1");
                    }
                break;
        }

        return id;
    }

    public int obtenerSiguientePreguntaId() {
        int id_preg = -1;
        int cantidad_preguntas = 0;

        String ids = sp.leer(context,"preguntas_id");

        if(!ids.equals("")) {
            String []aux = ids.split(",");
            cantidad_preguntas = aux.length;

            if(cantidad_preguntas == PreguntasResponse.CANTIDAD_PREGUNTAS ) {
                sp.guardar(context, "preguntas_id", "");
                return 0;
            }

            if(PreguntasResponse.CANTIDAD_PREGUNTAS-9 == cantidad_preguntas ) {
                id_preg = obtenerPareoId();
                sp.guardar(context, "preguntas_id", sp.leer(context, "preguntas_id") + id_preg + ",");
                sp.guardar(context, "cant_modo_4", "1");
                return id_preg;
            }

            if(cantidad_preguntas > 0 ) {
                do {
                    id_preg = obtenerPreguntaId(true);
                } while(id_preg == 0);
            }
        } else {
            id_preg = obtenerPreguntaId(false);
        }

        return id_preg;
    }

    public List<PreguntasResponse> getPregunta(int id_preg) {
        List<PreguntasResponse> preguntas = new ArrayList<>();
        String query = "SELECT pregunta_id, tematica_id, modo, pregunta, respuesta, retroalimentacion, opc, imagen_pregunta, audio_pregunta, imagen_respuesta, audio_respuesta, audio_retroalimentacion FROM preguntas_respuestas WHERE pregunta_id = "+id_preg;
        try {
            DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            if(db != null) {

                Cursor c = db.rawQuery(query, null);
                if(c.moveToFirst()) {
                    do {
                        PreguntasResponse preg = new PreguntasResponse();
                        preg.setId(c.getInt(0));
                        preg.setTematica_id(c.getInt(1));
                        preg.setModo_id(c.getInt(2));
                        preg.setPregunta(c.getString(3));
                        preg.setOpcion_resp(c.getString(4));
                        preg.setRetroalimentacion(c.getString(5));
                        preg.setRespuesta(c.getInt(6));
                        preg.setImagen_pregunta(c.getString(7));
                        preg.setAudio_pregunta(c.getString(8));
                        preg.setImagen_respuesta(c.getString(9));
                        preg.setAudio_respuesta(c.getString(10));
                        preg.setAudio_retroalimentacion(c.getString(11));
                        preguntas.add(preg);
                    } while (c.moveToNext());
                }
            }
        } catch (Exception e) {
        }

        return preguntas;
    }

    public int obtenerIdModo(int id_preg) {
        int modo = 0;
        String query = "";
        if(id_preg == 0) return 0;

        try {
            query = "SELECT modo FROM preguntas_respuestas WHERE pregunta_id = "+id_preg;
            DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            if(db != null) {

                Cursor c = db.rawQuery(query, null);
                if(c.moveToFirst()) {
                    modo = c.getInt(0);
                }
            }
        } catch (Exception e) {
        }

        return modo;
    }


    public int obtenerPreguntaId(boolean cant_preg) {
        int id = 0;
        String query = "";

        if(cant_preg){
            String ids = sp.leer(context,"preguntas_id"); //d
            String repetidas = sp.leer(context,"preguntas_repetidas_modo");

            ids = ids.substring(0, ids.length()-1);

            query = "SELECT pregunta_id FROM preguntas_respuestas WHERE pregunta_id NOT IN ("+ids+repetidas+") LIMIT 1";
        } else {
            query = "SELECT pregunta_id FROM preguntas_respuestas LIMIT 1";
        }

        try {
            DbHelper dbHelper = new DbHelper(context, "proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            if(db != null) {

                Cursor c = db.rawQuery(query, null);

                if(c.moveToFirst()) {
                    id = c.getInt(0);
                }
            }
        } catch (Exception e) {
            int x = 0;
        }

        if(validarCantidadModos(id) == id) {
            if(cant_preg){
                String ids = sp.leer(context,"preguntas_id");
                String nuevoIds = ids+id;
                sp.guardar(context, "preguntas_id", nuevoIds+",");
            } else {
                sp.guardar(context, "preguntas_id", id+",");
            }
        } else {
            String repetidas = sp.leer(context,"preguntas_repetidas_modo");
            sp.guardar(context, "preguntas_repetidas_modo", ","+id+repetidas);
            return 0;
        }

        return id;
    }

    public int obtenerPareoId() {
        int id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context,"proyecto_ds6");
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(db!=null){
                String[] campos = new String[]{"pregunta_id"};
                Cursor cursor = db.query("pareo",campos,null,null,null,null,null,"1");
                if(cursor.moveToFirst()){
                    id = cursor.getInt(0);
                }
            }
        }catch (Exception e){
            int x = 0;
        }

        return id;
    }




}
