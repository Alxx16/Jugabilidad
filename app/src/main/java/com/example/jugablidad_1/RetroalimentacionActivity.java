package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RetroalimentacionActivity extends AppCompatActivity {

    Button btn_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retroalimentacion);

        inicializarControles();

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaCarga = new Intent(getApplicationContext(), LoaderActivity.class);
                startActivity(pantallaCarga);
            }
        });

    }

    private void inicializarControles() {
        btn_continuar = (Button) findViewById(R.id.jugabilidad_retroalimentacion);
    }
}