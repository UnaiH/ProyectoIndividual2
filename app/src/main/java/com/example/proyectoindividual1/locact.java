package com.example.proyectoindividual1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
//Esta clase implementa lo necesario en la interfaz buscargoogle.xml
public class locact extends AppCompatActivity implements View.OnClickListener {
    private String usuario="";
    private FusedLocationProviderClient cliente;
    private LocationCallback actualizar;
    private TextView libcercana;
    private Double ActLog;
    private Double ActLat;
    private Location local;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new permisos().permisosInternet(locact.this, locact.this);
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        //Se pone el modo oscuro si asi se especifica en las preferencias y tambien se modifica el idioma.
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscargoogle);
        libcercana= findViewById(R.id.mascerc);
        libcercana.setVisibility(View.INVISIBLE);
        findViewById(R.id.bmascerc).setVisibility(View.INVISIBLE);
        localizacion();
    }
    //Para evitar problemas de incoherencia cuando hay llamadas por ejemplo.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    //Se realiza la geolocalizacion del movil para poder mostrar la dirección actual en la que se encuentra el usuario.
    public void localizacion(){
        TextView actual = (TextView) findViewById(R.id.locactual);
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        if(comprobarPlayServices()) {
            TextView localizacion = (TextView) findViewById(R.id.locactual);
            if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(locact.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            else if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(locact.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                cliente = LocationServices.getFusedLocationProviderClient(this);
                cliente.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        // se escribe la ultima posicion del movil (generalmente la actual).
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            List<Address> direccion;
                            try {
                                direccion=geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                ActLat=location.getLatitude();
                                ActLog=location.getLongitude();
                                local=location;
                                Log.i("Dir", "onSuccess: "+direccion);
                                if(!direccion.isEmpty()) {
                                    actual.setText(direccion.get(0).getAddressLine(0));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            //Si fuera null la localizacion se lanza una notificacion local.
                            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(locact.this,"CanalLibro");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                                elManager.createNotificationChannel(elCanal);
                            }
                            builder.setContentTitle("Error")
                                    .setContentText(String.valueOf(getResources().getString(R.string.errorLocali)))
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setAutoCancel(true);
                            elManager.notify(1, builder.build());
                            actual.setText(R.string.errorLocali);
                        }
                    }
                });
                //Ahora se realiza la actualizacion de la posicion del usuario generalmente se intentara cada 5 segundos y en casos necesarios incluso cada segundo. Tambien, se le dara una prioridad alta
                LocationRequest peticion = LocationRequest.create();
                peticion.setInterval(5000);
                peticion.setFastestInterval(1000);
                peticion.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                actualizar = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if(locationResult !=null){
                            List<Address> direccion;
                            try {
                                ActLat=locationResult.getLastLocation().getLatitude();
                                ActLog=locationResult.getLastLocation().getLongitude();
                                local=locationResult.getLastLocation();
                                direccion=geo.getFromLocation(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude(),1);
                                actual.setText(direccion.get(0).getAddressLine(0)+","+direccion.get(0).getLocality());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }}
                        else{
                            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(locact.this,"CanalLibro");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                                elManager.createNotificationChannel(elCanal);
                            }
                            builder.setContentTitle("Error")
                                    .setContentText(String.valueOf(getResources().getString(R.string.errorLocali)))
                                    .setSmallIcon(R.drawable.ic_launcher_background)
                                    .setAutoCancel(true);
                            elManager.notify(1, builder.build());
                            actual.setText(R.string.errorLocali);
                        }
                    }
                };
                cliente.requestLocationUpdates(peticion,actualizar, Looper.getMainLooper());
            } else {
                localizacion.setText(getResources().getString(R.string.errorLocali));
            }
        }
    }
    //Se comprueba la GoogleApiAvailability
    private boolean comprobarPlayServices(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int code = api.isGooglePlayServicesAvailable(this);
        if (code == ConnectionResult.SUCCESS) {
            return true;
        }
        else {
            if (api.isUserResolvableError(code)){
                api.getErrorDialog(this, code, 58).show();
            }
            return false;
        }
    }

    //Al destruirse la actividad se elimina la actualización de la geolocalización
    @Override
    protected void onDestroy() {
        cliente.removeLocationUpdates(actualizar);
        super.onDestroy();
    }

    //Al pulsar el primer botón de la interfaz se carga la correspondiente al mapa con las ciudades de la literatura
    @Override
    public void onClick(View view) {
        TextView libcercana=findViewById(R.id.mascerc);
        Log.i("TAG", "onClick: PasaVis"+libcercana.getVisibility());
        Intent i = new Intent(this, MainActivity_mapa.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
    //Si se pulsa el segundo boton se mostrara la distancia junto con la localizacion de tu marcador mas cercano y un boton para ver en el mapa todos tus marcadores.
    public void onClickCercanos(View view) throws IOException {
        double lat=ActLat;
        double lon=ActLog;
        Location loc = local;
        double dist=0.0;
        Button verMapa = findViewById(R.id.bmascerc);
        TextView libcercana=findViewById(R.id.mascerc);
        if (libcercana.getVisibility() == View.INVISIBLE){
            libcercana.setVisibility(View.VISIBLE);
            verMapa.setVisibility(View.VISIBLE);
        }
        BD base = new BD(locact.this);
        SQLiteDatabase db = base.getWritableDatabase();
        Log.i("TAG", "onClickCercanos: Pasando por aqui");
        if (db==null){
            //Si hay un error se comunica mediante una notificación.
            enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errbd));
        }
        else{
            ArrayList<Marcador> marcadores = base.misMarcadores(usuario);
            Log.i("TAG", "onClickCercanos: Pasando por aqui"+marcadores.size());
            if(marcadores.size()<=0 || marcadores ==null){
                libcercana.setText(R.string.sinmarcas);
            }
            else{
                int indicemax=0;
                double dmax=Double.POSITIVE_INFINITY;
                int indice=0;
                while(indice<marcadores.size()){
                    Location l = new Location("");
                    l.setLatitude(marcadores.get(indice).getLat());
                    l.setLongitude(marcadores.get(indice).getLon());
                    dist=loc.distanceTo(l);
                    Log.i("TAG", "onClickCercanos: "+dist);
                    if(dmax>dist){
                        indicemax=indice;
                        dmax=dist;
                    }
                    indice++;
                }
                Log.i("TAG", "onClickCercanos: "+marcadores.size());
                Geocoder geo = new Geocoder(this, Locale.getDefault());
                List<Address> direccion = geo.getFromLocation(marcadores.get(indicemax).getLat(), marcadores.get(indicemax).getLon(), 1);
                libcercana.setText(""+dmax+" m "+direccion.get(0).getAddressLine(0)+","+direccion.get(0).getLocality());
            }
        }

    }
    //Al pulsar el tercer boton de la interfaz que solo aparece tras pulsar el segundo antes se mostrará el mapa con todos los marcadores del usuario.
    public void onClickMapa2(View view){
        TextView libcercana=findViewById(R.id.mascerc);
        Log.i("TAG", "onClick: PasaVis"+libcercana.getVisibility());
        Intent i = new Intent(this, mapamislibrerias.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
    //Que hará al pulsar retroceder.
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, menuPrincipal.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(i);
                        finish();
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
    //Se encarga de enviar un mensaje local en esta clase.
    private void enviarMensajeLocal(String titulo, String contenido){
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(locact.this, "CanalLibro");
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
}
