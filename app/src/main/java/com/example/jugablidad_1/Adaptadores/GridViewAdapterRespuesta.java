package com.example.jugablidad_1.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jugablidad_1.R;

import java.util.List;

public class GridViewAdapterRespuesta  extends ArrayAdapter {
    Context context;
    List<String> respuesta;

    public GridViewAdapterRespuesta(Context context, List<String> respuestas) {
        super(context, R.layout.jugabilidad2_gridview_adapter, respuestas);
        this.context = context;
        this.respuesta = respuestas;

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

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jugabilidad2_gridview_adapter, null);
        }

        TextView textos = (TextView) convertView.findViewById(R.id.jugabilidad2_contResp);
        textos.setText(respuesta.get(position));

        textos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                respuesta.remove(position);



               /* swapItems(respuesta);*/
            }
        });


        return convertView;
    }
   //ignora esto, es un metodo que estabamos intentando usar para regresar los textos a donde estaban
    public void swapItems(List<String> itemsList) {
        respuesta.clear();
        respuesta.addAll(itemsList);
        notifyDataSetChanged();

    }
}