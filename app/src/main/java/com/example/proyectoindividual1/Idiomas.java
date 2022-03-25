package com.example.proyectoindividual1;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.util.Locale;

public class Idiomas {
    private String idioma="es";
    public Idiomas(){

    }
    public void setIdioma(Context contexto){
        String idioma;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(contexto);
        if (prefs.contains("listapreferencias")) {
            String idim = prefs.getString("listapreferencias", "Español");
            if (idim.equals("Español")) {
                idioma = "es";
                this.cambiarIdioma(idioma,contexto);
            }
            else if (idim.equals("Euskara")){
                idioma = "eu";
                this.cambiarIdioma(idioma,contexto);
            }
            else{
                idioma = "en";
                this.cambiarIdioma(idioma,contexto);
            }
        }

    }
    //Método para cambiar el idioma haciendo uso del Locale.
    private void cambiarIdioma(String idioma,Context contexto){
        Locale local = new Locale(idioma);
        Locale.setDefault(local);
        Configuration config = contexto.getResources().getConfiguration();
        config.setLocale(local);
        config.setLayoutDirection(local);
        Context con = contexto.createConfigurationContext(config);
        contexto.getResources().updateConfiguration(config,con.getResources().getDisplayMetrics());
    }
}
