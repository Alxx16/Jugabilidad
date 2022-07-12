package com.example.jugabilidad2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.jugabilidad2.Entidades.Pareo;
import com.example.jugabilidad2.R;

import java.util.ArrayList;
import java.util.List;

public class PareoListviewAdapter extends ArrayAdapter {

    List<Pareo> opciones = new ArrayList<>();

    public PareoListviewAdapter(@NonNull Context context,  List<Pareo> datos) {
        super(context, R.layout.pareolistview_template, datos);

        this.opciones = datos;
    }
    //#Adaptador de vista del xml pareolistview_template
    public View getView(int position, View v, ViewGroup viewGrou){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.pareolistview_template,null);
        //#Agregar texto al template
        TextView texto = (TextView)item.findViewById(R.id.lblPareoTemplate);
        texto.setText(opciones.get(position).getTexto());
        //#Agregar audio al template
        TextView texto2 = (TextView)item.findViewById(R.id.lblPareoAudio);
        texto2.setText(opciones.get(position).getAudio());
        return item;
    }
}
