package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Locale;

public class tipo_listas extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idioma;
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
        setContentView(R.layout.activity_tipo_listas);
    }

    public void onClickALibro(View view) {
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, anadirLibroALista.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
    public void onClickLeidos(View view) {
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, leidos.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        startActivity(i);
    }
    public void onClickLeyendo(View view) {
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, activListaleyendo.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        startActivity(i);
    }

    public void onClickVolver(View view) {
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, menuPrincipal.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salida)
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