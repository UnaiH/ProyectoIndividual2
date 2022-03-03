package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class registro extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
    }

    public void onClickRegistrarse(View view) {
        finish();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("regok", 1);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
}
