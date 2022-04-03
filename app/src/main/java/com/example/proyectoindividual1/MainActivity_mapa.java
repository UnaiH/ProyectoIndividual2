package com.example.proyectoindividual1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity_mapa extends FragmentActivity implements OnMapReadyCallback {
    private SupportMapFragment fragmentoMapa;
    private GoogleMap mapa;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double Latact=0; private double Longact=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mapa);
        fragmentoMapa = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentoMapa);
        //Array de tipos de sitios.
        String[] tipoSitios = {"bookshop","librería","liburu-denda"};
        //Array de nombres de sitios.
        String[] sitios = {"Bookshop","Librería","Liburu-denda"};

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(MainActivity_mapa.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocationactual();
        }
        else{
            new permisos().pedirpermisosLocalizar(MainActivity_mapa.this,MainActivity_mapa.this);
            getLocationactual();
        }
        int i=0;
        while (i<sitios.length) {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location" + Latact + "," + Longact + "&radius=500" + "&types" + tipoSitios[i] + "&sensor=true" + "&key=AIzaSyAZYm0UJayCtI0Ct4BZJ88W5X1azpPEJ_0";
            Log.i("url", "onCreate: " + url);
            new TareaDeLugar().execute(url);
            i++;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
    private void getLocationactual() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(MainActivity_mapa.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> tarea = fusedLocationProviderClient.getLastLocation();
            tarea.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location !=null){
                        Latact = location.getLatitude();
                        Longact = location.getLongitude();
                        fragmentoMapa.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                mapa = googleMap;
                                mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(Latact, Longact), 10
                                ));
                            }
                        });
                    }
                }
            });
        }
    }

    private class TareaDeLugar extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... strings) {
            String datos = null;
            try {
                datos = descargar(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return datos;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }
    private String descargar(String string) throws IOException {
        URL url= new URL(string);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.connect();
        InputStream stream = conexion.getInputStream();
        BufferedReader leer = new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder = new StringBuilder();
        String linea = "";
        while((linea=leer.readLine())!=null){
            builder.append(linea);
        }
        String datos = builder.toString();
        leer.close();
        return datos;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>>{

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser = new JsonParser();
            List<HashMap<String,String>> Listamapa =null;
            JSONObject objeto=null;
            try {
                 objeto = new JSONObject(strings[0]);
                 Listamapa = jsonParser.parseResult(objeto);
            } catch (JSONException e) {
                Log.i("TAG", "doInBackground: ParserTask");
                e.printStackTrace();
            }
            return Listamapa;
        }
        @Override
        protected void onPostExecute(List<HashMap<String,String>>hashMaps){
            mapa.clear();
            for (int i=0; i<hashMaps.size();i++){
                HashMap<String,String> hashMapList = hashMaps.get(i);
                double lat = Double.parseDouble(hashMapList.get("lat"));
                double lng = Double.parseDouble(hashMapList.get("lng"));
                String nombre = hashMapList.get("name");
                LatLng latL = new LatLng(lat,lng);
                MarkerOptions opciones = new MarkerOptions();
                opciones.position(latL);
                opciones.title(nombre);
                mapa.addMarker(opciones);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, locact.class);
        finish();
        startActivity(i);
    }
}