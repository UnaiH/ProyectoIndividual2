package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void IniciarSesion(View view) {
        setContentView(R.layout.menuprinc);
        Intent i = new Intent(this, menuPrincipal.class);
    }

    @Override
    public void onClick(View view) {

    }
}