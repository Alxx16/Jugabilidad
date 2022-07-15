package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugablidad_1.Controller.Jugabilidad;
import com.example.jugablidad_1.Controller.SharedPreferencesController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.jugablidad_1.Models.Responses.PreguntasResponse;
import com.nex3z.flowlayout.FlowLayout;

public class Modo3_Activity extends AppCompatActivity {

    RelativeLayout jugabilidad2_modo_3_mainLayout;
    TextView jugabilidad2_txtPregunta;
    Button jugabilidad2_modo_3_boton;
    ImageButton jugabilidad2_imbVoz;
    FlowLayout sentenceLine;


    private Modo3_PalabraPersonalizada palabraPersonalizada;
    private Modo3_VistaPersonalizada vistaPersonalizada;

    String audioP;

    List<String> respuestas;
    String pregunta = "";
    String opcCorrecta;
    String opcIncorrecta = "";
    String totalRespuestas;

    List<String> opcRespuesta = new ArrayList<>();

    SharedPreferencesController spp = new SharedPreferencesController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo3);

        Toolbar jugabilidad2_toolBar = findViewById(R.id.jugabilidad2_toolBar);
        setSupportActionBar(jugabilidad2_toolBar);

        inicializarControles();
        inicializarVistaPerzonalida();
        activacionBoton();
        obtenerInfoPregunta();
        revisarRespuesta();
    }

    private void inicializarControles() {
        jugabilidad2_modo_3_mainLayout = (RelativeLayout)findViewById(R.id.jugabilidad2_modo_3_main_layout);
        jugabilidad2_modo_3_boton = (Button)findViewById(R.id.jugabilidad2_modo_3_btn_confirmar);
        jugabilidad2_txtPregunta = (TextView) findViewById(R.id.jugabilidad2_txtPregunta);
        sentenceLine = (FlowLayout)findViewById(R.id.jugabilidad2_sentence_line);
        jugabilidad2_imbVoz = (ImageButton) findViewById(R.id.jugabilidad2_imbVoz);
        this.AudioPregunta();
    }

    private class TouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !vistaPersonalizada.empty()) {
                //Si la Vista de la palabra es presionada. Se va al metodo goToViewGroup
                //Que dependiendo si la palabra
                // este en el grupo de palabra abajo (se ira hacia arriba a la linea de oracion) y
                // este en la linea de oracion (se ira hacia abajo al grupo de palabras)
                palabraPersonalizada = (Modo3_PalabraPersonalizada) view;
                palabraPersonalizada.goToViewGroup(vistaPersonalizada, sentenceLine);

                //Metodo para validar si el boton puede ser activado o no segun Si la linea de oracion tenga elemento o no.
                activacionBoton();

                return true;
            }

            return false;
        }
    }

    private void obtenerInfoPregunta() {

        Jugabilidad jugabildad = new Jugabilidad(this);
        String ids = spp.leer(this, "preguntas_id");
        String[] aux = ids.split(",");
        int id = Integer.parseInt(aux[aux.length - 1]);
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

        totalRespuestas = opcCorrecta + " " + opcIncorrecta;

        respuestas = Arrays.asList(totalRespuestas.split(" "));

        Collections.shuffle(respuestas);

        jugabilidad2_txtPregunta.setText(pregunta);

        /////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////
        //Desabilitamos el boton de CONFIRMAR y le agregamos que este en blanco.
        jugabilidad2_modo_3_boton.setEnabled(false);
        jugabilidad2_modo_3_boton.setBackground(
                ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.shape_jugabilidad2_btn0));

        seteandoTexto(respuestas);

    }

    public void inicializarVistaPerzonalida(){
        //Instancio a mi clase VistaPersonalizada
        //A esta Vista la voy agregar en mi actividad
        vistaPersonalizada = new Modo3_VistaPersonalizada(getApplicationContext());

        //Seteo la vista en el centro de la actividad
        vistaPersonalizada.setGravity(Gravity.CENTER);

        //Le agrego parametros a la VistaPersonalizada
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //Parametro para que la vista quede creada por debajo de la oracion
        params.addRule(RelativeLayout.BELOW, R.id.jugabilidad2_fmlRespuestas);
        params.addRule(RelativeLayout.ABOVE, R.id.jugabilidad2_modo_3_btn_confirmar);
        params.topMargin = 5;

        //Finalmente agrego esa vista al activity_modo3.xml
        jugabilidad2_modo_3_mainLayout.addView(vistaPersonalizada, params);

    }

    private void activacionBoton(){
        //Hacemos validaciones para ver si el boton puede ser activado o no

        //Si la linea de oracion esta mayor a 0, osea que tiene elementos (por ejemplo una vista de palabra)
        if (sentenceLine.getChildCount() > 0) {
            //Entonces al boton se volverá de color y se podrá Tocar
            jugabilidad2_modo_3_boton.setBackground(
                    ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.shape_jugabilidad2_bntconfirmar));

            jugabilidad2_modo_3_boton.setEnabled(true);

        } else {
            //De lo contrario, el boton seguirá viendosé gris y no se podrá tocar.
            jugabilidad2_modo_3_boton.setBackground(
                    ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.shape_jugabilidad2_btn0));

            jugabilidad2_modo_3_boton.setEnabled(false);
            }
    }

    private void seteandoTexto(List<String> respuestas) {
        //Recorreremos cada palabra de la lista.

        for(String unaPalabra : respuestas){
            //A cada palabra la va enviar a PalabraPersonalizada
            Modo3_PalabraPersonalizada palabraPersonalizada = new Modo3_PalabraPersonalizada(getApplicationContext(),unaPalabra);
            //Se le implementa a esa vista de Palabra un metodo de Toque de Accion
            palabraPersonalizada.setOnTouchListener(new TouchListener());

            //Seteando al textView la fuente
            Typeface typeface = ResourcesCompat.getFont(getApplicationContext(), R.font.victorpro);
            palabraPersonalizada.setTypeface(typeface);

            //A la palabra le enviamos al metodo enviarPalabraVistaPersonalizada para meter ese vista de palabra a la Vista Personalizada
            vistaPersonalizada.enviarPalabraVistaPersonalizada(palabraPersonalizada);
        }

    }

    public void AudioPregunta() {
        jugabilidad2_imbVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(),R.raw.madres);
                mp.start();

            }
        });
    }


    private void revisarRespuesta() {
        //Agregar metodo onClick cuando el boton sea presionado
        jugabilidad2_modo_3_boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Variable StringBuilder para que vaya almacenando cada respuesta introducida en la Linea de Oracion
                StringBuilder respuestaIntroducida = new StringBuilder();

                //Recorrer la linea de oracion para traer cada elemento (osea la vista de palabras) que haya en la linea de oracion
                for (int i = 0; i < sentenceLine.getChildCount(); i++) {

                    //palabraPersonalizada tomará el TextView de que esta en la posicion (i) que tenga la linea de oracion
                    palabraPersonalizada = (Modo3_PalabraPersonalizada) sentenceLine.getChildAt(i);

                    //respuestaIntroducida irá almacenando cada texto de palabra que trae de palabraPersonalizada
                    //Ejemplo:  palabra1+" "+"palabra2"+" "+"palabra3"+" "
                    respuestaIntroducida.append(palabraPersonalizada.getText().toString() + " ");
                }

                //Si respuestaIntroducida contiene lo mismo que opcCorrecta ((palabra1 palabra2 palabra3)  == opcCorrera+" ")
                if(respuestaIntroducida.toString().equals(opcCorrecta + " ")){
                    //Mensaje Toast de Correcto
                    Toast.makeText(getApplicationContext(),"correcto CORRECTO PAI MUY BIEN",Toast.LENGTH_LONG).show();


                    ////Lo enviamos a la pantalla de Retroalimentacion
                    Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
                    startActivity(pantallaRetro);

                    //Prueba de impresion (ELIMINAR DESPUES)
                    System.out.println(opcRespuesta);
                    System.out.println("entro aqui");

                }else{
                    //Mensaje Toast de Incorrecto
                    Toast.makeText(getApplicationContext(),"incorrecto INCORRECTO MALO MALO MALO",Toast.LENGTH_LONG).show();

                    //Lo enviamos a la pantalla de Retroalimentacion
                    Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
                    startActivity(pantallaRetro);

                    //Prueba de impresion (ELIMINAR DESPUES)
                    System.out.println(opcRespuesta);
                    System.out.println(opcCorrecta);
                    System.out.println("chale ta mal lk");
                }
            }
        });
        {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jugabilidad2_modo3, menu);
        return true;
    }

}