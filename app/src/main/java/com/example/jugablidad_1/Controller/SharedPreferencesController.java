package com.example.jugablidad_1.Controller;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesController {

    public static final String globalVariable = "data.preguntas.respondidas";

    public void guardar(Context context, String token, String id){
        SharedPreferences.Editor editor = context.getSharedPreferences(globalVariable,MODE_PRIVATE).edit();
        editor.putString(token,id);
        editor.commit();
    }

    public String leer(Context context, String token){
        SharedPreferences sharedPref = context.getSharedPreferences(globalVariable, Context.MODE_PRIVATE);
        String dato = sharedPref.getString(token,"");
        return dato;
    }

}
