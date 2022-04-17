package com.example.proyectoindividual1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//Esta clase gestiona la vista que contiene un mapa con marcadores posicionados en las ciudades de la literatura según la UNESCO.
public class MainActivity_mapa extends FragmentActivity implements OnMapReadyCallback {
    private SupportMapFragment fragmentoMapa;
    private GoogleMap mapa;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private double Latact=0; private double Longact=0;
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        Log.i("Main_mapa", "onCreate: Llega");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mapa);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentoMapa);
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
    //Cuando el mapa ya está listo se crean los marcadores de las ciudades de la Literatura (véase que la mayoría son en Europa y por eso parece un cúmulo en esa zona del mapa).
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        LatLng edim = new LatLng(55.9494, -3.165);
        mapa.addMarker(new MarkerOptions()
                .position(edim)
                .title("Edinburgh "+getResources().getString(R.string.desde)+" 2004"));
        LatLng iowa = new LatLng(41.66113,  -91.53017);
        mapa.addMarker(new MarkerOptions()
                .position(iowa)
                .title("Iowa City "+getResources().getString(R.string.desde)+" 2008"));
        LatLng melbourne = new LatLng( -37.8136,  144.963);
        mapa.addMarker(new MarkerOptions()
                .position(melbourne)
                .title("Melbourne "+getResources().getString(R.string.desde)+" 2008"));
        LatLng dublin = new LatLng( 53.3434,  -6.26761);
        mapa.addMarker(new MarkerOptions()
                .position(dublin)
                .title("Dublin "+getResources().getString(R.string.desde)+" 2010"));
        LatLng Reykjavik = new LatLng( 64.1353,  -21.8952);
        mapa.addMarker(new MarkerOptions()
                .position(Reykjavik)
                .title("Reykjavik "+getResources().getString(R.string.desde)+" 2011"));
        LatLng norwich = new LatLng( 52.6283,  1.29667 );
        mapa.addMarker(new MarkerOptions()
                .position(norwich)
                .title("Norwich "+getResources().getString(R.string.desde)+" 2012"));
        LatLng krak = new LatLng( 50.0614,  19.9383 );
        mapa.addMarker(new MarkerOptions()
                .position(krak)
                .title("Kraków "+getResources().getString(R.string.desde)+" 2013"));
        LatLng Dunedin = new LatLng( -45.87416 ,  170.50361);
        mapa.addMarker(new MarkerOptions()
                .position(Dunedin)
                .title("Dunedin "+getResources().getString(R.string.desde)+" 2014"));
        LatLng Granada = new LatLng( 37.18817 ,  -3.60667);
        mapa.addMarker(new MarkerOptions()
                .position(Granada)
                .title("Granada "+getResources().getString(R.string.desde)+" 2014"));
        LatLng Heidelberg = new LatLng( 49.4122,  8.71);
        mapa.addMarker(new MarkerOptions()
                .position(Heidelberg)
                .title("Heidelberg "+getResources().getString(R.string.desde)+" 2014"));
        LatLng Prague = new LatLng( 50.083,  14.417);
        mapa.addMarker(new MarkerOptions()
                .position(Prague)
                .title("Prague "+getResources().getString(R.string.desde)+" 2014"));
        LatLng Baghdad = new LatLng( 33.3245,  44.4214 );
        mapa.addMarker(new MarkerOptions()
                .position(Baghdad)
                .title("Baghdad "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Barcelona = new LatLng( 41.3879,  2.16992 );
        mapa.addMarker(new MarkerOptions()
                .position(Barcelona)
                .title("Barcelona "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Ljubljana = new LatLng( 46.0514,  14.506 );
        mapa.addMarker(new MarkerOptions()
                .position(Ljubljana)
                .title("Ljubljana "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Lviv = new LatLng( 49.83826 ,  24.02324);
        mapa.addMarker(new MarkerOptions()
                .position(Lviv)
                .title("Lviv "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Montevideo = new LatLng( -34.90328,  -56.18816);
        mapa.addMarker(new MarkerOptions()
                .position(Montevideo)
                .title("Montevideo "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Nottingham = new LatLng( 52.95,  -1.133);
        mapa.addMarker(new MarkerOptions()
                .position(Nottingham)
                .title("Nottingham "+getResources().getString(R.string.desde)+" 2015"));
        LatLng obidos = new LatLng( 39.3621,  -9.15714);
        mapa.addMarker(new MarkerOptions()
                .position(obidos)
                .title("Óbidos "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Tartu = new LatLng( 58.38,  26.7225);
        mapa.addMarker(new MarkerOptions()
                .position(Tartu)
                .title("Tartu "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Ulyanovsk = new LatLng( 54.32824 ,  48.38657);
        mapa.addMarker(new MarkerOptions()
                .position(Ulyanovsk)
                .title("Ulyanovsk "+getResources().getString(R.string.desde)+" 2015"));
        LatLng Bucheon = new LatLng( 37.44,  126.766);
        mapa.addMarker(new MarkerOptions()
                .position(Bucheon)
                .title("Bucheon "+getResources().getString(R.string.desde)+" 2017"));
        LatLng Durban = new LatLng( -29.8579 , 31.0292);
        mapa.addMarker(new MarkerOptions()
                .position(Durban)
                .title("Durban "+getResources().getString(R.string.desde)+" 2017"));
        LatLng Lillehammer = new LatLng( 61.1133,  10.4644 );
        mapa.addMarker(new MarkerOptions()
                .position(Lillehammer)
                .title("Lillehammer "+getResources().getString(R.string.desde)+" 2017"));
        LatLng Manchester = new LatLng( 53.467, -2.233);
        mapa.addMarker(new MarkerOptions()
                .position(Manchester)
                .title("Manchester "+getResources().getString(R.string.desde)+" 2017"));
        LatLng quebec = new LatLng( 46.8127,  -71.2199);
        mapa.addMarker(new MarkerOptions()
                .position(quebec)
                .title("Québec City "+getResources().getString(R.string.desde)+" 2017"));
        LatLng seattle = new LatLng( 47.6205,  -122.351);
        mapa.addMarker(new MarkerOptions()
                .position(seattle)
                .title("Seattle "+getResources().getString(R.string.desde)+" 2017"));
        LatLng utretch = new LatLng( 52.0913,  5.12275);
        mapa.addMarker(new MarkerOptions()
                .position(utretch)
                .title("Utretch "+getResources().getString(R.string.desde)+" 2017"));
        LatLng angouleme = new LatLng(45.6494,  0.157);
        mapa.addMarker(new MarkerOptions()
                .position(angouleme)
                .title("Angoulême "+getResources().getString(R.string.desde)+" 2019"));
        LatLng beirut = new LatLng( 33.8898,  35.5003);
        mapa.addMarker(new MarkerOptions()
                .position(beirut)
                .title("Beirut "+getResources().getString(R.string.desde)+" 2019"));
        LatLng exeter = new LatLng( 50.7218,  -3.53362);
        mapa.addMarker(new MarkerOptions()
                .position(exeter)
                .title("Exeter "+getResources().getString(R.string.desde)+" 2019"));
        LatLng kuhmo = new LatLng( 64.1265,  29.5105);
        mapa.addMarker(new MarkerOptions()
                .position(kuhmo)
                .title("Kuhmo "+getResources().getString(R.string.desde)+" 2019"));
        LatLng lahore = new LatLng( 31.558,  74.35071);
        mapa.addMarker(new MarkerOptions()
                .position(lahore)
                .title("Lahore "+getResources().getString(R.string.desde)+" 2019"));
        LatLng leeuwarden = new LatLng( 53.2034,  5.79131);
        mapa.addMarker(new MarkerOptions()
                .position(leeuwarden)
                .title("Leeuwarden "+getResources().getString(R.string.desde)+" 2019"));
        LatLng Nanjing = new LatLng( 31.9825,  118.766);
        mapa.addMarker(new MarkerOptions()
                .position(Nanjing)
                .title("Nanjing "+getResources().getString(R.string.desde)+" 2019"));
        LatLng odesa = new LatLng( 46.4123,  30.7343);
        mapa.addMarker(new MarkerOptions()
                .position(odesa)
                .title("Odesa "+getResources().getString(R.string.desde)+" 2019"));
        LatLng slemani = new LatLng( 35.55, 45.4333);
        mapa.addMarker(new MarkerOptions()
                .position(slemani)
                .title("Slemani "+getResources().getString(R.string.desde)+" 2019"));
        LatLng Wonju = new LatLng( 37.341667, 127.920833);
        mapa.addMarker(new MarkerOptions()
                .position(Wonju)
                .title("Wonju "+getResources().getString(R.string.desde)+" 2019"));
        LatLng wroclaw = new LatLng( 51.1142,  17.0214);
        mapa.addMarker(new MarkerOptions()
                .position(wroclaw)
                .title("Wrocław "+getResources().getString(R.string.desde)+" 2019"));
        LatLng Gothenburg = new LatLng( 57.70716, 11.96679);
        mapa.addMarker(new MarkerOptions()
                .position(Gothenburg)
                .title("Gothenburg "+getResources().getString(R.string.desde)+" 2021"));
        LatLng Jakarta = new LatLng( -6.20199, 106.829 );
        mapa.addMarker(new MarkerOptions()
                .position(Jakarta)
                .title("Jakarta "+getResources().getString(R.string.desde)+" 2021"));
        LatLng vilnius = new LatLng( 54.6869, 25.2778);
        mapa.addMarker(new MarkerOptions()
                .position(vilnius)
                .title("Vilnius "+getResources().getString(R.string.desde)+" 2021"));
    }
}