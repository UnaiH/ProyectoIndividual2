package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class anadirLibroALista extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_libro_alista);
    }
    @Override
    public void onClick(View view) {
        EditText titulo = (EditText) findViewById(R.id.ntitulo2);
        EditText autor = (EditText) findViewById(R.id.nautor3);
        EditText paginas = (EditText) findViewById(R.id.npag2);
        EditText genero = (EditText) findViewById(R.id.ngenero);

        finish();
        Intent i = new Intent(this, anadirLibroALista.class);
        startActivity(i);
    }
}