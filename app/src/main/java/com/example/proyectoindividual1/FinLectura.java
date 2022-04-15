package com.example.proyectoindividual1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class FinLectura extends AppCompatActivity implements View.OnClickListener {
    private TextView notificaciones;
    private int alarmID = 1;
    private SharedPreferences definidas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_lectura);
        definidas = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String hora, minuto;
        hora = definidas.getString("hora","");
        minuto = definidas.getString("minuto","");
        notificaciones=findViewById(R.id.horaSelect);
        if (hora == null || hora.equals("")){
            int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            if (h>12){
                h-=12;
            }
            hora =""+h;
        }
        if (minuto == null|| minuto.equals("")){
            minuto =""+Calendar.getInstance().get(Calendar.MINUTE);
        }
        Log.i("TAGLH", "onCreate: Llega Hora"+hora);
        notificaciones.setText(getString(R.string.hdefinida)+" "+hora+":"+minuto);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, menuPrincipal.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
    public void onClickDefhora(View view) {
        Calendar actual = Calendar.getInstance();
        int hora = actual.get(Calendar.HOUR_OF_DAY);
        int minuto = actual.get(Calendar.MINUTE);
        SharedPreferences.Editor edit = definidas.edit();
        String hFin, minFin;
        int h = (hora+1);
        if (h>12){
            h-=12;
        }
        Calendar horaAlarma= Calendar.getInstance();
        horaAlarma.set(Calendar.HOUR_OF_DAY, hora+1);
        horaAlarma.set(Calendar.MINUTE, minuto);
        horaAlarma.set(Calendar.SECOND,00);
        hFin=""+h;
        if (minuto<10) {
            minFin = "0" + minuto;
        }
        else{
            minFin = "" + minuto;
        }
        definirHora(hFin,minFin,horaAlarma);
    }
    public void onClickDef2hora(View view) {
        Calendar actual = Calendar.getInstance();
        int hora = actual.get(Calendar.HOUR_OF_DAY);
        int minuto = actual.get(Calendar.MINUTE);
        String hFin, minFin;
        int h = (hora+2);
        if (h>12){
            h-=12;
        }
        Calendar horaAlarma= Calendar.getInstance();
        horaAlarma.set(Calendar.HOUR_OF_DAY, hora+2);
        horaAlarma.set(Calendar.MINUTE, minuto);
        horaAlarma.set(Calendar.SECOND,00);
        hFin=""+h;
        if (minuto<10) {
            minFin = "0" + minuto;
        }
        else{
            minFin = "" + minuto;
        }
        definirHora(hFin,minFin,horaAlarma);

    }
    public void onClickDef3hora(View view) {
        Calendar actual = Calendar.getInstance();
        int hora = actual.get(Calendar.HOUR_OF_DAY);
        int minuto = actual.get(Calendar.MINUTE);
        String hFin, minFin;
        int h = (hora+3);
        if (h>12){
            h-=12;
        }
        Calendar horaAlarma= Calendar.getInstance();
        horaAlarma.set(Calendar.HOUR_OF_DAY, hora+2);
        horaAlarma.set(Calendar.MINUTE, minuto);
        horaAlarma.set(Calendar.SECOND,00);
        hFin=""+h;
        if (minuto<10) {
            minFin = "0" + minuto;
        }
        else{
            minFin = "" + minuto;
        }
        definirHora(hFin,minFin,horaAlarma);

    }
    public void onClickDefmediahora(View view) {
        Calendar actual = Calendar.getInstance();
        int hora = actual.get(Calendar.HOUR_OF_DAY);
        int minuto = actual.get(Calendar.MINUTE);
        String hFin, minFin;
        minuto = (minuto+30);
        int h = (hora);
        if(minuto>60){
            minuto-=60;
            h++;
        }
        if (h>12){
            h-=12;
        }
        Calendar horaAlarma= Calendar.getInstance();
        horaAlarma.set(Calendar.HOUR_OF_DAY, hora+2);
        horaAlarma.set(Calendar.MINUTE, minuto);
        horaAlarma.set(Calendar.SECOND,00);
        hFin=""+h;
        if (minuto<10) {
            minFin = "0" + minuto;
        }
        else{
            minFin = "" + minuto;
        }
        definirHora(hFin,minFin,horaAlarma);

    }
    private void definirHora(String hFin, String minFin, Calendar horaAlarma){
        Calendar actual = Calendar.getInstance();
        SharedPreferences.Editor edit = definidas.edit();
        notificaciones.setText(getString(R.string.hdefinida)+" "+hFin+":"+minFin);
        edit.putString("hour", hFin);
        edit.putString("minute", minFin);
        edit.putInt("alarmID", alarmID);
        edit.putLong("alarmTime", actual.getTimeInMillis());
        edit.commit();
        Utils.setAlarm(alarmID,  horaAlarma.getTimeInMillis(), FinLectura.this);
    }
}