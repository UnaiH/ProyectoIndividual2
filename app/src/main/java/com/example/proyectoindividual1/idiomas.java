package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class idiomas extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idiomas);
    }

    @Override
    public void onClick(View view) {
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }
    public void onClickEspañol(View view){

    }
    public void onClickEuskera(View view){

    }
    public void onClickIngles(View view){

    }
}
