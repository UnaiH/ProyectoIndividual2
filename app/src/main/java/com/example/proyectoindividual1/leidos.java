package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

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

public class leidos extends AppCompatActivity {
    String[] titulos = {"uno","dos","tres","cuatro","cinco"};
    String[] autor = {"uno","dos","tres","cuatro","cinco"};
    String[] fechaFin = {"uno","dos","tres","cuatro","cinco"};
    Integer[] libros={R.drawable.libro1,R.drawable.libro2,R.drawable.libro3,R.drawable.libro4,R.drawable.libro5};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        TextView tview = new TextView(this);
        tview.setTypeface(Typeface.DEFAULT_BOLD);
        tview.setText("Lista de libros leidos");
        ListView lista=(ListView)findViewById(android.R.id.list);
        lista.addHeaderView(tview);
        AdaptadorLeidos adaptador = new AdaptadorLeidos(this, titulos, autor, fechaFin,libros);
        lista.setAdapter(adaptador);
    }
}