package com.example.jugablidad_1.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jugablidad_1.Modo3_Activity;
import com.example.jugablidad_1.R;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    GridView jugabilidad2_grdRespuesta_adapter;

    Context context;
    List<String> respuesta;
    List<String> respuestas_elegidas = new ArrayList<>();
    List<String> opcRespuestas = new ArrayList<>();



    public GridViewAdapter(Context context, List<String> respuestas, GridView respuestas_elegidas){
        this.context = context;
        this.respuesta = respuestas;
        this.jugabilidad2_grdRespuesta_adapter = respuestas_elegidas;

    }



    @Override
    public int getCount() {
        return respuesta.size();
    }

    @Override
    public Object getItem(int position) {
        return respuesta.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jugabilidad2_gridview_adapter,null);
        }
        TextView  textos = (TextView)convertView.findViewById(R.id.jugabilidad2_contResp);
        textos.setText(respuesta.get(position));

        textos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setVisibility(View.INVISIBLE);
                respuestas_elegidas.add(textos.getText().toString());
                GridViewAdapterRespuesta adapter = new GridViewAdapterRespuesta(context, respuestas_elegidas);
                jugabilidad2_grdRespuesta_adapter.setAdapter(adapter);



                /*swapItems(respuesta);*/
            }
        }
        );
        return convertView;
    }

    public List<String> respuestas_probar() {
        GridViewAdapterRespuesta adapter = new GridViewAdapterRespuesta(context, respuestas_elegidas);
        opcRespuestas = adapter.respuesta_seleccionada();

        return opcRespuestas;
    }

    //ignora esto, es un metodo que estabamos intentando usar para regresar los textos a donde estaban

    /*public void swapItems(List<String> itemsList){
        respuesta.clear();
        respuesta.addAll(itemsList);
        notifyDataSetChanged();
    }*/


}
