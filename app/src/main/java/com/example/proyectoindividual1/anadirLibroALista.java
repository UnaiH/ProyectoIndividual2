package com.example.proyectoindividual1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class anadirLibroALista extends AppCompatActivity implements View.OnClickListener {
    private CalendarView calendario;
    private String fecha;
    private int dia;
    private int mes;
    private int anno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
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
        calendario = (CalendarView) findViewById(R.id.calendario);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                fecha = day+"/"+month+"/"+year;
                dia = day;
                mes = month;
                anno = year;
                Log.i("TAGF", "onSelectedDayChange:"+fecha);
            }
        });
    }

    @Override
    public void onClick(View view) {
        EditText titulo = (EditText) findViewById(R.id.ntitulo2);
        EditText autor = (EditText) findViewById(R.id.nautor3);
        EditText paginas = (EditText) findViewById(R.id.npag2);
        EditText genero = (EditText) findViewById(R.id.ngeneroanadirlibro);
        Bundle extras = getIntent().getExtras();
        String usu = "";
        if (extras != null) {
            usu= extras.getString("usuario");
        }
        if(fecha==""){
            fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        }
        if (paginas.getText()==null){
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle(getResources().getString(R.string.reglib))
                    .setContentText(getResources().getString(R.string.nopodregistrar))
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());
            Intent i = new Intent(this, tipo_listas.class);
            i.putExtra("usuario", usu);
            setResult(RESULT_OK, i);
            finish();
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
                builder.setContentTitle(getResources().getString(R.string.reglib))
                        .setContentText(getResources().getString(R.string.nopodregistrar))
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setAutoCancel(true);
                elManager.notify(1, builder.build());
                Intent i = new Intent(this, tipo_listas.class);
                i.putExtra("usuario", usu);
                setResult(RESULT_OK, i);
                finish();
                startActivity(i);
            }
            else {
                Intent i = new Intent(this, tipo_listas.class);
                i.putExtra("usuario", usu);
                setResult(RESULT_OK, i);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String finalUsu = usu;
                builder.setMessage(R.string.anlibdiag)
                        .setCancelable(false)
                        .setPositiveButton(R.string.psop, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                BD base = new BD(anadirLibroALista.this);
                                SQLiteDatabase db = base.getWritableDatabase();
                                if (db != null) {
                                    NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this, "CanalLibro");
                                    String usu="";
                                    if (extras != null) {
                                        usu= extras.getString("usuario");
                                    }
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                                        elManager.createNotificationChannel(elCanal);
                                    }
                                    String ahora = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                    boolean existe = false;
                                    existe=base.anadirLibro(usu,titulo.getText().toString(),autor.getText().toString(),genero.getText().toString(),Integer.parseInt(paginas.getText().toString()),0,ahora,fecha);
                                    if(!existe) {
                                        if (ContextCompat.checkSelfPermission(anadirLibroALista.this, Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(anadirLibroALista.this, new String[]{Manifest.permission.WRITE_CALENDAR},1);
                                        }
                                        if ((ContextCompat.checkSelfPermission(anadirLibroALista.this, Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_GRANTED)) {
                                            fecha = fecha+" 00:00";
                                            try
                                            {
                                                Log.i("TAG", "onClick: Pasa por calendar");
                                                long empMillis = 0;
                                                long acabaMillis = 0;
                                                Calendar empieza = Calendar.getInstance();
                                                empieza.set(anno,mes,dia,0,0);
                                                empMillis = empieza.getTimeInMillis();
                                                Calendar acaba = Calendar.getInstance();
                                                acaba.set(anno,mes,dia,23,59);
                                                acabaMillis = acaba.getTimeInMillis();
                                                Intent valores = new Intent(Intent.ACTION_INSERT);
                                                valores.setData(CalendarContract.Events.CONTENT_URI);
                                                valores.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,empMillis);
                                                valores.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,acabaMillis);
                                                valores.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString()+" "+autor.getText().toString());
                                                valores.putExtra(CalendarContract.Events.DESCRIPTION, genero.getText().toString()+ finalUsu);
                                                valores.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
                                                if(valores.resolveActivity(getPackageManager()) == null){
                                                    Log.i("TAG", "onClick: inicia activity calendar");
                                                    startActivity(valores);
                                                }
                                            }
                                            catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                        builder.setContentTitle(getResources().getString(R.string.addcor))
                                                .setContentText(getResources().getString(R.string.addlibmen))
                                                .setSmallIcon(R.drawable.ic_launcher_background)
                                                .setAutoCancel(true);
                                        elManager.notify(1, builder.build());
                                    }
                                    else{
                                        builder.setContentTitle(getResources().getString(R.string.BDe))
                                                .setContentText(getResources().getString(R.string.BDMe))
                                                .setSmallIcon(R.drawable.ic_launcher_background)
                                                .setAutoCancel(true);
                                        elManager.notify(1, builder.build());
                                        finish();
                                        startActivity(i);
                                    }
                                }
                                else {
                                    NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this, "CanalLibro");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                                        elManager.createNotificationChannel(elCanal);
                                    }
                                    builder.setContentTitle(getResources().getString(R.string.BDe))
                                            .setContentText(getResources().getString(R.string.BDMe))
                                            .setSmallIcon(R.drawable.ic_launcher_background)
                                            .setAutoCancel(true);
                                    elManager.notify(1, builder.build());
                                    finish();
                                    startActivity(i);
                                }
                            }
                        })
                        .setNegativeButton(R.string.can, new DialogInterface.OnClickListener() {
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
        Bundle extras = getIntent().getExtras();
        String usu = "";
        if (extras != null) {
            usu= extras.getString("usuario");
        }
        i.putExtra("usuario", usu);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.saliranlib)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}