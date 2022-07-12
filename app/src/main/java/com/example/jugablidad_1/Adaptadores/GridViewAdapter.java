package com.example.jugablidad_1.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jugablidad_1.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    Context context;
    List<String> respuesta;

    public GridViewAdapter(Context context, List<String> respuestas){
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

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.jugabilidad2_gridview_adapter,null);
        }
        TextView  textos = (TextView)convertView.findViewById(R.id.jugabilidad2_contResp);
        textos.setText(respuesta.get(position));

        return convertView;
    }
}
