package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

public class activListaleyendo extends AppCompatActivity{
    private String usu= "";
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
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ftransac = manager.beginTransaction();
        Fragment fragmentolista = new listaleyendo();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usu= extras.getString("usuario");
        }
        Bundle bundle = new Bundle();
        bundle.putString("usuario",usu);
        ftransac.replace(R.id.fragmento_leyendo,fragmentolista);
        ftransac.addToBackStack(null);
        ftransac.commit();
    }
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la lista de lisbros que se están leyendo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
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