package com.example.jugabilidad2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jugabilidad2.Adapter.PareoListviewAdapter;
import com.example.jugabilidad2.Entidades.Pareo;
import com.example.jugabilidad2.Entidades.Responses.PareoResponse;
import com.example.jugabilidad2.Services.ApiService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PareoActivity extends AppCompatActivity {

    private ListView lstPareo;
    private ListView lstPareo2;
    private Button btnpareo;
    private TextView lblPareo1, lblPareo2;
    private ProgressBar prgbar;
    private int idPareo1,idPareo2,avance=0,idPreguntaPareo;
    private String opcPareo1,opcPareo2;
    private List<Integer> numerosPareo = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pareo);
        //#TU TE ENCARGASTE DE LA CREACIÓN DE LA BASE DE DATOS
        //CargarDbTablaPareo();
        InicializarControles();
    }

    //#CLASE DONDE SE CREABA LA BASE DE DATOS PAREO TU TE ENCARGASTE DE ESTO
    /*
    private void CargarDbTablaPareo(){
        //LLAMADA DE END POINT PARA OBTENER DATOS
        //DECLARACIÓN DE LIST IGUAL AL DE RESPONSE PARA GUARDADO DE DATOS
        Call<List<PareoResponse>> llamadoPareo = ApiService.getApiService().obtenerListadoPareo();
        llamadoPareo.enqueue(new Callback<List<PareoResponse>>() {
            @Override
            public void onResponse(Call<List<PareoResponse>> call, Response<List<PareoResponse>> response) {
                //PASAR DATOS DEL CUERPO HACIA UNA LISTA SIN EL CALL
                List<PareoResponse> listaProductos = response.body();
                //PRIMERAMENTE SE PONE EL PareoResponse y se le asigna un nombre de variable y luego : la variable lista
                for(PareoResponse PareoB : listaProductos ){
                    //Se utiliza una entidad Pareo con el constructor ya definido y se utilizan los gets
                    Pareo pareo = new Pareo(
                            PareoB.getPregunta_id(),
                            PareoB.getTematica_id(),
                            PareoB.getOrden_pareo(),
                            PareoB.getTexto(),
                            PareoB.getAudio()
                    );
                    //Se inserta los datos en la base de datos
                    pareo.pareoInsert(getApplicationContext());
                }
            }
            @Override
            public void onFailure(Call<List<PareoResponse>> call, Throwable t) {
                int x=1;
            }
        });
    }*/

    private void InicializarControles() {
        lstPareo = (ListView)findViewById(R.id.lstPareo1);
        lstPareo2 = (ListView)findViewById(R.id.lstPareo2);
        btnpareo = (Button)findViewById(R.id.btnPareoSiguiente);
        prgbar = (ProgressBar) findViewById(R.id.pgrPareo);
        //#ESTE METODO ERA PARA GENERAR LA PREGUNTA ADENTRO EN LA CLASE PAREO LE INSERTAS LA TEMATICA ID
        //########idPreguntaPareo = #///ASIGNAR EL ID DE LA PREGUNTA AQUÍ;
        CargarListView();
        CargarListView2();
        AttachEvent();
    }

    private void CargarListView() {
        ///////////////PONER EL ID EN variable idPreguntaPareo
        ///////////////PONER EL ID EN variable idPreguntaPareo/////////////////////////////////////////////////////////////////////////////////////////////////////
        //AL INSERTAR LA idPregunta ya no se utilizaria la variable idPregunta, eso era para pruebas locales con la tabla pareo
        List<Pareo> pareo = new Pareo().obtenerPareo(32,getApplicationContext());
        List<Pareo> pareosDivision = new ArrayList<>();
        //pareosDivision.add(pareo.get(0 - 4));
        //#Desordenar las posiciones opciones del listview
        for (int i= 0; i < 5; i++){
            pareosDivision.add(pareo.get(i));
        }
        Collections.shuffle(pareosDivision);
        PareoListviewAdapter adapter = new PareoListviewAdapter(getApplicationContext(),pareosDivision);
        lstPareo.setAdapter(adapter);
    }

    private void CargarListView2() {
        //ARREGLAR DUPLICIDAD DE DATOS, LISTS que podrían declararse globalmente para uso en todos partes con una sola consulta
        //a la base de datos
        ///////////////PONER EL ID EN variable idPreguntaPareo/////////////////////////////////////////////////////////////////////////////////////////////////////
        //#Ingresar id de la pregunta en primer parametro
        List<Pareo> pareo = new Pareo().obtenerPareo(32,getApplicationContext());
        List<Pareo> pareosDivision = new ArrayList<>();
        //pareosDivision.add(pareo.get(5 - 9));
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
                System.out.println(audio);
                MediaPlayer mediaPlayer =new MediaPlayer();
                try {
                    mediaPlayer.stop();
                    mediaPlayer.setDataSource(PareoActivity.this,Uri.parse(String.valueOf(Uri.parse("http://192.168.0.4:8000/prueba.mp3"))));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                //MediaPlayer.create(getApplicationContext(), Uri.parse("http://192.168.0.4:8000/prueba.mp3")).start();
                //#Se le asignara el valor del del textview a variable textview para tomar el valor
                TextView txt = (TextView) view.findViewById(R.id.lblPareoTemplate);
                //#Parsear el textview de la variable txt a un string para poder usarlo
                opcPareo1 = txt.getText().toString();
                //#Método obtenerIdPareo es utilizado para obtener el id del elemento seleccionado del listview
                idPareo1= pareo.obtenerIdPareo(opcPareo1,getApplicationContext());
                //#Método compararPreguntas es utilizado para comparar los dos elementos seleccionados de cada listview
                if(opcPareo1!=null && opcPareo2!=null){
                    compararPreguntas(idPareo1,idPareo2);
                }
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
                    mediaPlayer.setDataSource(PareoActivity.this,Uri.parse(String.valueOf(Uri.parse("http://192.168.0.4:8000/prueba.mp3"))));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
                //MediaPlayer.create(getApplicationContext(),Uri.parse("http://192.168.0.4:8000/prueba.mp3")).start();
                TextView txt = (TextView) view.findViewById(R.id.lblPareoTemplate);
                opcPareo2 = txt.getText().toString();
                idPareo2= pareo.obtenerIdPareo(opcPareo2,getApplicationContext());
                if(opcPareo1!=null && opcPareo2!=null){
                    compararPreguntas(idPareo1,idPareo2);
                }
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
                    prgbar.setProgress(6);
                    //#Se avtivará el botón para seguir a la próxima pregunta
                    btnpareo.setEnabled(true);
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
    //#Método utilizado para generar un numero aleatorio según el límite máximo dado en el parámetro
    /*public int generarNumero(int nMax, int nMin){
        Random random = new Random();
        //#bandera que sera usada para do while
        boolean flag;
        //#variable que guardará el número aleatorio generado
        int nAleatorio;
        //#Se necesita agregar un número a la lista por que si no daría error de tamaño la primera vez
        numerosPareo.add(100);
        //#Condicional para saber si tiene que repetir la generación de número o no, si encuentrá un número igual al
        //#Generado en la lista se repité la generación de número aleatorio
        do{
            int r = random.nextInt(nMax);
            flag=true;
            //#Recorrido de lista
            for(int i=0 ; i < numerosPareo.size() ;i++){
                //Comparación de lista y número aleatorio
                if(numerosPareo.get(i)==r){
                    flag=false;
                }
            }
            nAleatorio=r;
        }while(flag==false);
        //#Agregar el número aleatorio generado a la lista
        numerosPareo.add(nAleatorio);
        //#Retornar número aleatorio para desordenar el listview
        return nAleatorio;
        }*/


}