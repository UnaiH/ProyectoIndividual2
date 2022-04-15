package com.example.proyectoindividual1;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Pantalla {
    public Pantalla(){

    }
    public  void cambiarPantallaMenus(Context contexto){
        //Cambia entre el tema oscuro y el tema normal de las interfaces de los menús en función de las preferencias.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                contexto.setTheme(R.style.ModoOscuro);
            } else {
                contexto.setTheme(R.style.Normal);
            }
        }
        else{
            contexto.setTheme(R.style.Normal);
        }
    }
    public  void cambiarPantallaListas(Context contexto){
        //Cambia entre el tema oscuro y el tema normal de las interfaces de listas en función de las preferencias.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                contexto.setTheme(R.style.ModoOscuro);
            } else {
                contexto.setTheme(R.style.Listas);
            }
        }
        else{
            contexto.setTheme(R.style.Normal);
        }
    }
}
