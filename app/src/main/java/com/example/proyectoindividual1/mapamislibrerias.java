package com.example.proyectoindividual1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class mapamislibrerias extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private String usuario;
    private SupportMapFragment fragmentoMapa;
    private GoogleMap mapa;
    private Marker marker;
    private LatLng latLng;
    private String value;
    Geocoder geocoder;
    private double Latact=0; private double Longact=0;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapamislibrerias);
        geocoder = new Geocoder(this, Locale.getDefault());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapmislib);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, locact.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        BD base = new BD(mapamislibrerias.this);
        SQLiteDatabase db = base.getWritableDatabase();
        if (db==null){
            //Si hay un error se comunica mediante una notificación.
            enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errbd));
        }
        else {
            ArrayList<Marcador> marcadores = base.misMarcadores(usuario);
            if (marcadores.size() > 0 || marcadores != null) {
                Iterator<Marcador> itr = marcadores.iterator();
                while(itr.hasNext()){
                    Marcador m = itr.next();
                    mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(m.getLat(),m.getLon()))
                            .title(m.getTit()));
                }
            }
        }
        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng punto) {
                String titulo="";
                BD base = new BD(mapamislibrerias.this);
                SQLiteDatabase db = base.getWritableDatabase();
                if (db!=null) {
                    latLng = punto;
                    List<Address> addresses = new ArrayList<>();
                    try {
                        addresses = geocoder.getFromLocation(punto.latitude, punto.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    android.location.Address address = addresses.get(0);
                    if (address != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i) + "\n");
                        }
                        Toast.makeText(mapamislibrerias.this, sb.toString(), Toast.LENGTH_LONG).show();
                    }
                    //remove previously placed Marker
                    if (marker != null) {
                        marker.remove();
                    }
                    //place marker where user just clicked
                    base.anadirMarcador(usuario,getString(R.string.mapamislibrerias),punto.latitude, punto.longitude);
                    marker = mapa.addMarker(new MarkerOptions().position(punto).title("Mark")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                    Intent i = new Intent(mapamislibrerias.this, mapamislibrerias.class);
                    i.putExtra("usuario", usuario);
                    setResult(RESULT_OK, i);
                    finish();
                    startActivity(i);
                }
            }
        });
    }
    private void enviarMensajeLocal(String titulo, String contenido){
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mapamislibrerias.this, "CanalLibro");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
            elManager.createNotificationChannel(elCanal);
        }
        builder.setContentTitle(titulo)
                .setContentText(contenido)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
        elManager.notify(1, builder.build());
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Para evitar problemas de incoherencia cuando hay llamadas por ejemplo.
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}