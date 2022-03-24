package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class activListaleyendo extends AppCompatActivity{
    private boolean listaact=true;
    private String usu= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int indice=0;
        Bundle extras = getIntent().getExtras();
        usu = "";
        if (extras != null) {
            usu = extras.getString("usuario");
        }
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
        setContentView(R.layout.activity_leyendo);
        Bundle bundle = new Bundle();
        bundle.putString("usuario",usu);
        listaleyendo fragmento = new listaleyendo();
        fragmento.setArguments(bundle);
        FragmentManager gestor = getSupportFragmentManager();
        FragmentTransaction transaccion = gestor.beginTransaction();
        transaccion.replace(R.id.fragmentos,fragmento);
        transaccion.commit();
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, tipo_listas.class);
        i.putExtra("usuario", usu);
        setResult(RESULT_OK, i);
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salirleyendo)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
    public void gestorFragmentos(Libro lib1){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.i("TAG", "gestorFragmentos: "+lib1.getPaginas());
        }
        else{
            Log.i("TAG", "gestorFragmentos: "+lib1.getFechaInicio());
            if(listaact){
                listaact=false;
                FragmentManager gestor = getSupportFragmentManager();
                FragmentTransaction ftransac = gestor.beginTransaction();
                datos_leyendo fragmento = new datos_leyendo();
                Bundle bundle = new Bundle();
                bundle.putString("usuario",usu);
                fragmento.setArguments(bundle);
                fragmento.libof=lib1;
                ftransac.replace(R.id.fragmentos,fragmento);
                ftransac.commit();
            }
            else{
                listaact=true;
                FragmentManager gestor = getSupportFragmentManager();
                FragmentTransaction ftransac = gestor.beginTransaction();
                listaleyendo fragmento = new listaleyendo();
                Bundle bundle = new Bundle();
                bundle.putString("usuario",usu);
                fragmento.setArguments(bundle);
                ftransac.replace(R.id.fragmentos,fragmento);
                ftransac.commit();
            }
        }
    }
}