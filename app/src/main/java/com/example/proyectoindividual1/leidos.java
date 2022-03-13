package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class leidos extends AppCompatActivity {
    String[] titulos = {"uno","dos","tres","cuatro","cinco"};
    String[] autor = {"uno","dos","tres","cuatro","cinco"};
    String[] fechaFin = {"uno","dos","tres","cuatro","cinco"};
    String[] fechaInicio = {"uno","dos","tres","cuatro","cinco"};
    Integer[] imag={R.drawable.libro1,R.drawable.libro2,R.drawable.libro3,R.drawable.libro4,R.drawable.libro5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int longit = titulos.length;
        Integer[] libros = new Integer[longit];
        Random rand = new Random();
        Log.i("TAG", "onCreate: "+libros.length);
        Log.i("TAG", "onCreate: "+longit);
        for (int aux=0;aux<longit;aux++){
            libros[aux]=imag[rand.nextInt(longit)];
            Log.i("TAG", ""+aux);
        }
        Log.i("TAG", "onCreate: "+libros.length);
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
        setContentView(R.layout.activity_leidos);
        ListView lista=(ListView)findViewById(R.id.listaleidos);
        AdaptadorLeidos adaptador = new AdaptadorLeidos(this, titulos, autor, fechaInicio, fechaFin, libros);
        lista.setAdapter(adaptador);
    }
}