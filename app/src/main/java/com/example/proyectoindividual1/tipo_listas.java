package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class tipo_listas extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_listas);
    }

    public void onClickALibro(View view) {
        Intent i = new Intent(this, anadirLibroALista.class);
        startActivity(i);
    }

    public void onClickVolver(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
}