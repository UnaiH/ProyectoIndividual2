package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprinc);
    }

    public void onClickUsar(View view) {
        finish();
        Intent i = new Intent(this, tipoListas.class);
        startActivity(i);
    }
    public void onClickConfig(View view) {
        finish();
        Intent i = new Intent(this, config.class);
        startActivity(i);
    }
    public void onClickIdiomas(View view) {
        finish();
        Intent i = new Intent(this, idiomas.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
}
