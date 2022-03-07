package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class anadirLibroALista extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_libro_alista);
    }

    @Override
    public void onClick(View view) {
        finish();
        Intent i = new Intent(this, anadirLibroALista.class);
        startActivity(i);
    }
}