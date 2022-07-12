package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jugablidad_1.Controller.Jugabilidad;
import com.example.jugablidad_1.Controller.SharedPreferencesController;
import com.example.jugablidad_1.Models.Pareo;

import java.util.List;

public class Modo4_Activity extends AppCompatActivity {

    SharedPreferencesController spp = new SharedPreferencesController();

    TextView data;
    Button btn_confirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo4);

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
        data = (TextView) findViewById(R.id.jugabilidad1_modo_4);
        btn_confirmar = (Button) findViewById(R.id.jugabilidad1_modo_4_btn_confirmar);

    }

    private void obtenerInfoPregunta() {
        Jugabilidad jugabildad = new Jugabilidad(this);
        String ids = spp.leer(this,"preguntas_id");
        String [] aux = ids.split(",");
        int id = Integer.parseInt(aux[aux.length-1]);

        List<Pareo> pareo = new Pareo().obtenerPareo(id,getApplicationContext());
        List<Pareo> pareo1 = new Pareo().obtenerPareo(id,getApplicationContext());

        String dataaaa = "";

        for(Pareo preg : pareo) {
            dataaaa += preg.getOrden_pareo()+"\n";
            dataaaa += preg.getTexto()+"\n";
            dataaaa += "\n\n";
        }

        for(Pareo preg : pareo1) {
            dataaaa += preg.getOrden_pareo()+"\n";
            dataaaa += preg.getTexto()+"\n";
            dataaaa += "\n\n";
        }

        data.setText(dataaaa);
    }



}