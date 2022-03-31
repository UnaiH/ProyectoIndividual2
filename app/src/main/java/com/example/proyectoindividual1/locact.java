package com.example.proyectoindividual1;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import java.util.List;
import java.util.Locale;

public class locact extends AppCompatActivity implements View.OnClickListener {
    private String usuario;
    private FusedLocationProviderClient cliente;
    private LocationCallback actualizar;
    private TextView libcercana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        //Se pone el modo oscuro si así se especifica en las preferencias y también se modifica el idioma.
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscargoogle);
        libcercana= findViewById(R.id.mascerc);
        libcercana.setVisibility(View.INVISIBLE);
        localizacion();
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
    public void localizacion(){
        TextView actual = (TextView) findViewById(R.id.locactual);
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        if(comprobarPlayServices()) {
            TextView localizacion = (TextView) findViewById(R.id.locactual);
            if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(locact.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(locact.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            if (ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(locact.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                cliente = LocationServices.getFusedLocationProviderClient(this);
                cliente.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>(){
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            List<Address> direccion;
                            try {
                                direccion=geo.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                if(!direccion.isEmpty()) {
                                    actual.setText(direccion.get(0).getAddressLine(0));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
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
                });
                LocationRequest peticion = LocationRequest.create();
                peticion.setInterval(10000);
                peticion.setFastestInterval(5000);
                peticion.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                actualizar = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        if(locationResult !=null){
                            List<Address> direccion;
                            try {
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
                cliente.requestLocationUpdates(peticion,actualizar,null);
            } else {
                localizacion.setText(getResources().getString(R.string.errorLocali));
            }
        }
    }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cliente.removeLocationUpdates(actualizar);
    }

    @Override
    public void onClick(View view) {
        TextView libcercana=findViewById(R.id.mascerc);
        Log.i("TAG", "onClick: PasaVis"+libcercana.getVisibility());
        if (libcercana.getVisibility() == View.INVISIBLE){
            libcercana.setVisibility(View.VISIBLE);
        }
    }
}
