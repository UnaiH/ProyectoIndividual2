package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.preference.PreferenceManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class activListaleyendo extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                setTheme(R.style.ModoOscuro);
            } else {
                setTheme(R.style.Theme_ProyectoIndividual1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leyendo);
        FragmentManager fragmento = getFragmentManager();
        FragmentTransaction transaccion = fragmento.beginTransaction();
        listaleyendo leer = new listaleyendo();
        //transaccion.add(R.id.);
        transaccion.commit();
    }
}