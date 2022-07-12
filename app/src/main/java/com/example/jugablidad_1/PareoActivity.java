/*
* Pantalla pareo
* Autor: Camaño Rafael
* UTP PANAMÁ
 */
package com.example.jugablidad_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jugablidad_1.Adapter.PareoListviewAdapter;
import com.example.jugablidad_1.Controller.SharedPreferencesController;
import com.example.jugablidad_1.Entidades.Pareo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PareoActivity extends AppCompatActivity {

    SharedPreferencesController spp = new SharedPreferencesController();

    private Button btn_siguiente;
    private ListView lstPareo;
    private ListView lstPareo2;
    private Button btnpareo;
    private TextView lblPareo1, lblPareo2;
    private ProgressBar prgbar;
    private int idPareo1=0,idPareo2=0,avance=0,idPreguntaPareo=0;
    private String opcPareo1,opcPareo2;
    private List<Integer> numerosPareo = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pareo);

        this.inicializarControles();
        //this.obtenerInfoPregunta();


        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaRetro = new Intent(getApplicationContext(), RetroalimentacionActivity.class);
                startActivity(pantallaRetro);
            }
        });

    }

    private void inicializarControles() {
        //data = (TextView) findViewById(R.id.jugabilidad1_modo_4);
        btn_siguiente = (Button) findViewById(R.id.jugabilidad2_modo_4_btn_confirmar);
        lstPareo = (ListView)findViewById(R.id.lstPareo1);
        lstPareo2 = (ListView)findViewById(R.id.lstPareo2);
        prgbar = (ProgressBar) findViewById(R.id.pgrPareo);
        //Jugabildad jugabildad = new Jugabildad(this);
        String ids = spp.leer(this,"preguntas_id");
        String [] aux = ids.split(",");
        idPreguntaPareo = Integer.parseInt(aux[aux.length-1]);
        System.out.println(idPreguntaPareo);
        CargarListView();
        CargarListView2();
        AttachEvent();
    }

    private void CargarListView() {
        //#Insertar la variable idPreguntaPareo en el parametro para obtener los datos de esa pregunta
        List<Pareo> pareo = new Pareo().obtenerPareo(idPreguntaPareo,getApplicationContext());
        List<Pareo> pareosDivision = new ArrayList<>();
        //#Desordenar las posiciones opciones del listview
        for (int i= 0; i < 5; i++){
            pareosDivision.add(pareo.get(i));
        }
        Collections.shuffle(pareosDivision);
        PareoListviewAdapter adapter = new PareoListviewAdapter(getApplicationContext(),pareosDivision);
        lstPareo.setAdapter(adapter);
    }

    private void CargarListView2() {
        //#Insertar la variable idPreguntaPareo en el parametro para obtener los datos de esa pregunta
        List<Pareo> pareo = new Pareo().obtenerPareo(idPreguntaPareo,getApplicationContext());
        List<Pareo> pareosDivision = new ArrayList<>();
        //#Desordenar las posiciones, opciones del listview
        for (int i= 5; i < 10; i++){
            //#Generar las posiciones aleatoriamente con el método generarNumero y asignarlo a pareo que contiene todos los
            //#elementos del listview, luego serán asignados a la lista pareosDivision que es la que se adaptará a la pantalla
            pareosDivision.add(pareo.get(i));
        }
        Collections.shuffle(pareosDivision);
        PareoListviewAdapter adapter = new PareoListviewAdapter(getApplicationContext(),pareosDivision);
        //#Cargar adaptador al listview para mostrarlo en pantalla
        lstPareo2.setAdapter(adapter);
    }

    //#Método utilizado para adjuntar todos los eventos que se utilizarán en el Activity
    private void AttachEvent(){
        Pareo pareo = new Pareo();
        //Métodos utilizados para los eventos en los listviews, para los dos listviews son idénticos
        lstPareo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //#Condicional que va a ser usada en el momento en se haya seleccionado por segunda vez un elemento en la misma columna
                //#Para cambiar el color del actual elemento seleccionado y quitar el color al anteriormente seleccionado
                if(lblPareo1!=null){
                    lblPareo1.setBackgroundResource(R.drawable.shape_jugabilidad2_general);
                }
                //#Se asignará el shape color amarillo al actual elemento seleccionado
                view.findViewById(R.id.lblPareoTemplate).setBackgroundResource(R.drawable.shape_jugabilidad2_seleccionaropcion);
                //#Se le asignara el valor del del textview del texto mostrado para su posterior uso en cambios de vista
                lblPareo1=view.findViewById(R.id.lblPareoTemplate);
                //#Tomar el enlace del audio del textview
                TextView a = view.findViewById(R.id.lblPareoAudio);
                //#Parsear el textview a un string para poder usarlo
                String audio = a.getText().toString();
                //#parsear la variable audio aun uri para poder buscarlá en la
                MediaPlayer mediaPlayer =new MediaPlayer();
                try {
                    mediaPlayer.stop();
                    mediaPlayer.setDataSource(PareoActivity.this,Uri.parse(String.valueOf(Uri.parse("http://192.168.0.4:8000/"+audio))));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                //#Se le asignara el valor del del textview a variable textview para tomar el valor
                TextView txt = (TextView) view.findViewById(R.id.lblPareoTemplate);
                //#Parsear el textview de la variable txt a un string para poder usarlo
                opcPareo1 = txt.getText().toString();
                //#Método obtenerIdPareo es utilizado para obtener el id del elemento seleccionado del listview
                idPareo1= pareo.obtenerIdPareo(opcPareo1,getApplicationContext());
                //#Método compararPreguntas es utilizado para comparar los dos elementos seleccionados de cada listview
                //if(opcPareo1!=null && opcPareo2!=null){
                    compararPreguntas(idPareo1,idPareo2);
                //}
            }
        });

        lstPareo2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(lblPareo2 !=null){
                    lblPareo2.setBackgroundResource(R.drawable.shape_jugabilidad2_general);
                }
                view.findViewById(R.id.lblPareoTemplate).setBackgroundResource(R.drawable.shape_jugabilidad2_seleccionaropcion);
                lblPareo2 =view.findViewById(R.id.lblPareoTemplate);
                TextView a = view.findViewById(R.id.lblPareoAudio);
                String audio = a.getText().toString();
                MediaPlayer mediaPlayer =new MediaPlayer();
                try {
                    mediaPlayer.stop();
                    mediaPlayer.setDataSource(PareoActivity.this,Uri.parse(String.valueOf(Uri.parse("http://192.168.0.4:8000/"+audio))));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                TextView txt = (TextView) view.findViewById(R.id.lblPareoTemplate);
                opcPareo2 = txt.getText().toString();
                idPareo2= pareo.obtenerIdPareo(opcPareo2,getApplicationContext());
                //if(opcPareo1!=null && opcPareo2!=null){
                    compararPreguntas(idPareo1,idPareo2);
                //}
            }
        });
    }
    //#Método utilizado para comparar los elementos seleccionados de cada listview
    private void compararPreguntas(int p1, int p2){
        //#Condicional donde solo entra si las dos elemenentos de los listviews están seleccionados
        if(opcPareo1!=null && opcPareo2!=null){
            //#Condicional donde solo entra al tener los elementos con el mismo id, que significa que es correcto
            if(p1==p2){
                //#Al momento de entrar a la condicional se le asignara un shape verde que mostrará en pantalla como una señal de
                //#Correcto
                lblPareo1.setBackgroundResource(R.drawable.shape_jugabilidad2_correcto);
                lblPareo2.setBackgroundResource(R.drawable.shape_jugabilidad2_correcto);
                //#En las variables de los textviews guardada anteriormente al seleccionarlos, se le asigna un texto "vacío" a la vista
                lblPareo1.setText("");
                lblPareo2.setText("");
                //#En las variables de las opciones seleccionadas que guardan los id's se le asigna null
                opcPareo1=null;
                opcPareo2=null;
                //#En la variable avance se le suma 1
                avance = avance + 1;
                //#Condicional dondo entrará solo al avance a ver respondido los 5 pares correctamente
                if(avance == 5){ ;
                    //prgbar.setProgress(6);
                    //#Se avtivará el botón para seguir a la próxima pregunta
                    btn_siguiente.setEnabled(true);
                    //#Se revelara el botón para seguir a la próxima pregunta
                    btnpareo.setVisibility(View.VISIBLE);
                }
                //#Se le asigno un momento para que se activará estas instrucciones
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //#En las variables de los textviews guardada anteriormente al seleccionarlos, se le asigna que sean invisibles a la vista
                        lblPareo1.setVisibility(View.INVISIBLE);
                        lblPareo2.setVisibility(View.INVISIBLE);
                        //#En las variables de los textviews guardada anteriormente al seleccionarlos, se le asigna null para que no haya problemas de vista
                        lblPareo1= null;
                        lblPareo2= null;
                    }
                };
                Timer time = new Timer();
                time.schedule(timerTask, 250);
            }else{
                //#Al momento de fallar la condicional se le asignara un shape rojo que mostrará en pantalla como una señal de
                //#Incorrecto
                lblPareo2.setBackgroundResource(R.drawable.shape_jugabilidad2_incorrecto);
                lblPareo1.setBackgroundResource(R.drawable.shape_jugabilidad2_incorrecto);
                //#En las variables de las opciones seleccionadas que guardan los id's se le asigna null
                opcPareo1=null;
                opcPareo2=null;
                //#Se le asigno un momento para que se activará estas instrucciones
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //#Al momento de fallar la condicional se le asignara un shape blanco que mostrará en pantalla como una señal de no tener nada seleccionado
                        lblPareo1.setBackgroundResource(R.drawable.shape_jugabilidad2_general);
                        lblPareo2.setBackgroundResource(R.drawable.shape_jugabilidad2_general);
                        //#En las variables de los textviews guardada anteriormente al seleccionarlos, se le asigna null para que no haya problemas de vista
                        lblPareo1= null;
                        lblPareo2= null;
                    }
                };
                Timer time = new Timer();
                time.schedule(timerTask, 250);
            }
        }
    }
}