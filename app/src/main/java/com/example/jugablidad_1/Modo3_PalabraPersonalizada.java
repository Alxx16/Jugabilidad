package com.example.jugablidad_1;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.nex3z.flowlayout.FlowLayout;

public class Modo3_PalabraPersonalizada extends AppCompatTextView {

    private String palabra;

    //Al recibir la palabra en un String. La convierte dentro de un TextView para despues ser agregada a la VistaPerzonalizada
    public Modo3_PalabraPersonalizada(Context context, String palabra) {
        super(context);
        this.palabra = palabra;

        setText(palabra);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(15, 5,15, 0);

        setTextColor(getResources().getColor(R.color.black));
        setPadding(5,5,5,5);
        setLayoutParams(layoutParams);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTextSize(15);

        setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_jugabilidad2_palabra));
    }

    public void goToViewGroup(Modo3_VistaPersonalizada vistaPersonalizada, FlowLayout sentenceVista) {
        ViewParent parent = getParent();

        if (parent instanceof Modo3_VistaPersonalizada) {
            //Quitar palabra del grupo de palabras. Y agregar la palabra a la vista de oracion
            vistaPersonalizada.removerDeLaVista(this);
            sentenceVista.addView(this);

        } else {
            //Quitar palabra de la vista de la oracion. Y agregarla devuelta al grupo de Palabra.
            sentenceVista.removeView(this);
            vistaPersonalizada.agregarALaVista(this);
        }
    }
}
