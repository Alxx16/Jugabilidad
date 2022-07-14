package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugablidad_1.Adaptadores.GridViewAdapter;
import com.example.jugablidad_1.Adaptadores.GridViewAdapterRespuesta;
import com.example.jugablidad_1.Controller.Jugabilidad;
import com.example.jugablidad_1.Controller.SharedPreferencesController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.jugablidad_1.Models.Responses.PreguntasResponse;
import com.nex3z.flowlayout.FlowLayout;

public class Modo3_Activity extends AppCompatActivity {

    ProgressBar jugabilidad2_pgrBa;
    TextView jugabilidad2_txtPregunta;
    GridView jugabilidad2_grdRespuestas;
    GridView jugabilidad2_grdPalabras;

    FlowLayout sentenceLine;

    List<String> respuestas;
    ImageButton jugabilidad2_imbVoz;
    String audioP;
    String opcCorrecta;
    String opcIncorrecta = "";
    int currentProgress = 0;

    List<String> opcRespuesta = new ArrayList<>();

    SharedPreferencesController spp = new SharedPreferencesController();

    /*currentProgress = currentProgress + 10;
                    jugabilidad2_pgrBa.setProgress(currentProgress);
                    jugabilidad2_pgrBa.setMax(100);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo3);
        this.InicializarControles();

    }

    private void InicializarControles() {
        jugabilidad2_pgrBa = (ProgressBar) findViewById(R.id.jugabilidad2_pgrBar);
        jugabilidad2_txtPregunta = (TextView) findViewById(R.id.jugabilidad2_txtPregunta);
        /*sentenceLine = (FlowLayout)findViewById(R.id.jugabilidad2_sentence_line);*/
        jugabilidad2_grdRespuestas = (GridView) findViewById(R.id.jugabilidad2_grdRespuestas);
        jugabilidad2_grdPalabras = (GridView) findViewById(R.id.jugabilidad2_grdPalabras);
        jugabilidad2_imbVoz = (ImageButton) findViewById(R.id.jugabilidad2_imbVoz);
        this.obtenerInfoPregunta();
        this.SetearGrid();
        this.AudioPregunta();
    }

    private void obtenerInfoPregunta() {


        Jugabilidad jugabildad = new Jugabilidad(this);
        String ids = spp.leer(this, "preguntas_id");
        String[] aux = ids.split(",");
        int id = Integer.parseInt(aux[aux.length - 1]);
        String pregunta = "";
       /* String opcCorrecta = "";*/
        String opcIncorrecta= "";
        //ARRAYLIST CON LOS DATOS DE LA PREGUNTA
        List<PreguntasResponse> preguntas = jugabildad.getPregunta(id);

        pregunta = preguntas.get(0).getPregunta();
        audioP = preguntas.get(0).getAudio_pregunta();

        if (preguntas.get(0).getRespuesta() == 1) {
            opcCorrecta = preguntas.get(0).getOpcion_resp();
            opcIncorrecta = preguntas.get(1).getOpcion_resp();

        } else {
            opcCorrecta = preguntas.get(1).getOpcion_resp();
            opcIncorrecta = preguntas.get(0).getOpcion_resp();
        }

        String totalRespuestas = opcCorrecta + " " + opcIncorrecta;

        respuestas = Arrays.asList(totalRespuestas.split(" "));

        Collections.shuffle(respuestas);

        jugabilidad2_txtPregunta.setText(pregunta);
    }

    public void SetearGrid() {
        GridViewAdapter adapter = new GridViewAdapter(this, respuestas, jugabilidad2_grdRespuestas);
        jugabilidad2_grdPalabras.setAdapter(adapter);
    }

    public void AudioPregunta() {
        jugabilidad2_imbVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), Uri.parse("http://192.168.0.17:8080/madres.mp3"));
                mp.start();

            }
        });
    }

    public void ComprobarResp (View view){

        /*String str="";*/
        GridViewAdapter adapter = new GridViewAdapter(this, respuestas, jugabilidad2_grdRespuestas);
        opcRespuesta = adapter.respuestas_probar();

        /*for (String resp : opcRespuesta) {
            str+=resp+" ";
        }*/
       /* StringBuilder str = new StringBuilder();
        for (String resp : opcRespuesta) {
            str.append(resp);
            str.append(" ");
        }*/

        if (opcRespuesta.equals(opcCorrecta)){

            Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
            startActivity(pantallaRetro);

            System.out.println(opcRespuesta);
            System.out.println("entro aqui");

        }else{
            Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
            startActivity(pantallaRetro);
            System.out.println(opcRespuesta);
            System.out.println(opcCorrecta);
            System.out.println("chale ta mal lk");
        }




    }


}