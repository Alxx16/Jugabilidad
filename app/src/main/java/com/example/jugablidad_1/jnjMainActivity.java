package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jugablidad_1.Controller.Jugabilidad;

import java.util.Timer;
import java.util.TimerTask;

public class jnjMainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jnj_main);

        this.inicializarControles();
        int id = 1;


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jugabilidad jug = new Jugabilidad(getApplicationContext());
                jug.borrarDatosPreferences();
                jug.obtenerDatosJugabilidad(id);
                jug.obtenerDatosPareo(id);

                TimerTask timertask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(jug.cambiarModo());
                        finish();
                    }
                };

                Timer time = new Timer();
                time.schedule(timertask, 1000);
            }
        });
    }

    private void inicializarControles() {
        btn = (Button) findViewById(R.id.obtenerDatos);
    }
}