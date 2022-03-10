package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.menuprinc);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String usu= extras.getString("usuario");
            TextView bienvenida = (TextView) findViewById(R.id.bienvenidatext);
            bienvenida.setText("Te damos la bienvenida "+usu);
        }
        String idioma =prefs.getString("Nombre","Usuario");
    }

    public void onClickUsar(View view) {
        finish();
        Intent i = new Intent(this, tipo_listas.class);
        startActivity(i);
    }
    public void onClickConfig(View view) {
        finish();
        Intent i = new Intent(this, config.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la aplicacion?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
