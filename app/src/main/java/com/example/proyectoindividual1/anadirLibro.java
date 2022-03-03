package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class anadirLibro extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_libro);
    }

    @Override
    public void onClick(View view) {
        finish();
        Intent i = new Intent(this, tipoListas.class);
        startActivity(i);
    }
    public void onClickAnadirLibro(View view) {
        finish();
        Intent i = new Intent(this, anadirLibro.class);
        startActivity(i);
    }
}