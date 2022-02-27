package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprinc);
    }

    public void onClickUsar(View view) {
        setContentView(R.layout.tiposlistas);
        Intent i = new Intent(this, tipolistas.class);

    }

    @Override
    public void onClick(View view) {

    }
}
