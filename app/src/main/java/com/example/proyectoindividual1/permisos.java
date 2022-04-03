package com.example.proyectoindividual1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class permisos {
    private boolean concedidos=false;
    public void permisosCamara(Context contexto, Activity actividad){
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.CAMERA},1);
        }
    }
    public void pedirpermisosLocalizar(Context contexto, Activity actividad) {
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
    public void permisosCalendarEscribir(Context contexto, Activity actividad){
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.WRITE_CALENDAR},1);
        }
    }
    public void permisosInternet(Context contexto, Activity actividad){
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.INTERNET},1);
        }
    }
    public void permisosCalendarLeer(Context contexto, Activity actividad){
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.READ_CALENDAR)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(actividad, new String[]{Manifest.permission.READ_CALENDAR},1);
        }
    }
}
