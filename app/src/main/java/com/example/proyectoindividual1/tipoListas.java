package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class tipoListas extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiposlistas);
    }

    public void onClickEnLectura(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }
    public void onClickLeidos(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }
    public void onClickAnadir(View view) {
        finish();
        Intent i = new Intent(this, anadirLibro.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }
}
