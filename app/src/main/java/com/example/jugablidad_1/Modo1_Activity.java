package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jugablidad_1.Controller.Jugabilidad;
import com.example.jugablidad_1.Controller.SharedPreferencesController;
import com.example.jugablidad_1.Models.Responses.PreguntasResponse;

import java.util.List;

public class Modo1_Activity extends AppCompatActivity {

    SharedPreferencesController sp = new SharedPreferencesController();

    TextView data;
    Button btn_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo1);

        this.inicializarControles();
        this.obtenerInfoPregunta();

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
                startActivity(pantallaRetro);
            }
        });
    }

    private void inicializarControles() {
        data = (TextView) findViewById(R.id.jugabilidad1_modo_1);
        btn_confirmar = (Button) findViewById(R.id.jugabilidad1_modo_1_btn_confirmar);
    }

    private void obtenerInfoPregunta() {
        Jugabilidad jugabildad = new Jugabilidad(this);
        String ids = sp.leer(this,"preguntas_id");
        String [] aux = ids.split(",");
        int id = Integer.parseInt(aux[aux.length-1]);


        List<PreguntasResponse> preguntas = jugabildad.getPregunta(id);

        String dataaaa = "";

        for(PreguntasResponse preg : preguntas) {
            dataaaa += preg.getPregunta()+"\n";
            dataaaa += preg.getRespuesta()+"\n";
            dataaaa += preg.getOpcion_resp()+"\n";
            dataaaa += preg.getRetroalimentacion()+"\n";
            dataaaa += "\n\n";
        }
        data.setText(dataaaa);
    }
}