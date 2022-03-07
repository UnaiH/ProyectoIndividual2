package com.example.proyectoindividual1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprinc);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma =prefs.getString("Nombre","Usuario");
        Log.i("Idioma", "Idioma"+idioma);
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
}
