package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class anadirLibroALista extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);

            if (modOsc) {
                setTheme(R.style.ModoOscuro);
            } else {
                setTheme(R.style.Theme_ProyectoIndividual1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_libro_alista);
        EditText etgen=(EditText) findViewById(R.id.ngeneroanadirlibro);
        if ( prefs.contains("genero")) {
            Boolean gen = prefs.getBoolean("genero", true);
            Log.i("TAG", ""+gen);
            if (!gen) {
                etgen.setVisibility(View.INVISIBLE);
            } else {
                etgen.setVisibility(View.VISIBLE);
            }
        }
    }
    @Override
    public void onClick(View view) {
        EditText titulo = (EditText) findViewById(R.id.ntitulo2);
        EditText autor = (EditText) findViewById(R.id.nautor3);
        EditText paginas = (EditText) findViewById(R.id.npag2);
        EditText genero = (EditText) findViewById(R.id.ngeneroanadirlibro);
        Log.i("TAG", "onClick: "+Integer.parseInt(paginas.getText().toString()));
        if (paginas.getText()==null){
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle("Registro libro")
                    .setContentText("No se ha podido registrar el libro correctamente debido a que no se han definido las páginas o es un número no válido")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());
            finish();
            Intent i = new Intent(this, tipo_listas.class);
            startActivity(i);
        }
        else{
            if (Integer.parseInt(paginas.getText().toString())<=0){
                NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this,"CanalLibro");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                    elManager.createNotificationChannel(elCanal);
                }
                builder.setContentTitle("Registro libro")
                        .setContentText("No se ha podido registrar el libro correctamente debido a que no se han definido las páginas o es un número no válido")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setAutoCancel(true);
                elManager.notify(1, builder.build());
                finish();
                Intent i = new Intent(this, tipo_listas.class);
                startActivity(i);
            }
            else {
                Intent i = new Intent(this, tipo_listas.class);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("¿Seguro que deseas añadir el libro?")
                        .setCancelable(false)
                        .setPositiveButton("Por supuesto", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this, "CanalLibro");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                                    elManager.createNotificationChannel(elCanal);
                                }
                                builder.setContentTitle("Adición correcta")
                                        .setContentText("Se ha añadido el libro correctamente")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setAutoCancel(true);
                                elManager.notify(1, builder.build());
                                finish();
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, tipo_listas.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de esta pantalla? Si lo haces no se registrará el libro")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(i);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}