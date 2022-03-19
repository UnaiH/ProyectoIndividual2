package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class leidos extends AppCompatActivity {
    String[] titulos = {};
    String[] autor = {};
    String[] fechaFin = {};
    String[] fechaInicio = {};
    Integer[] imag={R.drawable.libro1,R.drawable.libro2,R.drawable.libro3,R.drawable.libro4,R.drawable.libro5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int indice=0;
        Bundle extras = getIntent().getExtras();
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        ArrayList<Libro> librs;
        BD base = new BD(leidos.this);
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            librs = base.misLeidos(usuario);
            titulos = new String[librs.size()];
            autor = new String[librs.size()];
            fechaFin = new String[librs.size()];
            fechaInicio = new String[librs.size()];
            Iterator <Libro> it = librs.iterator();
            while(it.hasNext()){
                Libro libitr=it.next();
                titulos[indice]=libitr.getNombre();
                autor[indice]=libitr.getAutor();
                fechaFin[indice]=libitr.getFechaFin();
                fechaInicio[indice]=libitr.getFechaInicio();
                indice++;
                Log.i("TAG", "onCreate: "+titulos[2]);
            }
        }
        int longit = titulos.length;
        Integer[] libros = new Integer[longit];
        Random rand = new Random();
        for (int aux=0;aux<longit;aux++){
            libros[aux]=imag[rand.nextInt(longit)];
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                setTheme(R.style.ModoOscuro);
            } else {
                setTheme(R.style.Normal);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leidos);
        ListView lista=(ListView)findViewById(R.id.listaleidos);
        AdaptadorLeidos adaptador = new AdaptadorLeidos(this, titulos, autor, fechaInicio, fechaFin, libros);
        lista.setAdapter(adaptador);
    }
    public void onBackPressed() {
        Intent i = new Intent(this, tipo_listas.class);
        Bundle extras = getIntent().getExtras();
        String usu = "";
        if (extras != null) {
            usu= extras.getString("usuario");
        }
        i.putExtra("usuario", usu);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}