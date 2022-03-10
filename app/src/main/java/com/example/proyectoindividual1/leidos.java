package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class leidos extends AppCompatActivity {
    ListView l;
    String[] titulos = {};
    String[] autor = {};
    Date[] fechaFin = {};

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
        l = findViewById(R.id.lalista);
        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, android.R.id.text1, titulos){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View vista= super.getView(position, convertView, parent);
                TextView lineaprincipal=(TextView) vista.findViewById(android.R.id.text1);
                TextView lineasecundaria=(TextView) vista.findViewById(android.R.id.text2);
                TextView lineaterciaria=(TextView) vista.findViewById(android.R.id.text2);
                lineaprincipal.setText(titulos[position]);
                lineasecundaria.setText(autor[position]);
                lineaterciaria.setText(fechaFin[position].toString());
                return vista;
            }
        };
        l.setAdapter(arr);
    }
}
