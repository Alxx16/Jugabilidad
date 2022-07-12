package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.jugablidad_1.Controller.Jugabilidad;

import java.util.Timer;
import java.util.TimerTask;

public class LoaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);

        cambiarPregunta();
    }

    private void cambiarPregunta() {
        Jugabilidad jug = new Jugabilidad(getApplicationContext());

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
}