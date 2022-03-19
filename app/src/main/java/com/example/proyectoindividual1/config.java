package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class config extends AppCompatActivity implements View.OnClickListener {
    //Esta clase se encarga de la interfaz de configuración que se emplea además para las preferencias.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se pone en modo oscuro o no la pantalla
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                setTheme(R.style.ModoOscuro);
            } else {
                setTheme(R.style.Normal);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);
        //se añade el fragment que contiene la pref_config. Si no está ya añadida el fragmento se añadiría.
        Fragment fragmento = new configuracion();
        FragmentTransaction ftransac = getFragmentManager().beginTransaction();
        if(savedInstanceState==null){
            ftransac.add(R.id.layoutconfig, fragmento, "fragset");
            ftransac.commit();
        }
        else{
            fragmento = getFragmentManager().findFragmentById(Integer.parseInt("fragset"));
        }
    }

    @Override
    public void onClick(View view) {
        //Este es el botón aplicar de config. que mandará al usuario al menu principal.
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

    public static class configuracion extends PreferenceFragment{
        //Esta clase sería el fragmento de preferencias.
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_config);
        }
    }
    @Override
    public void onBackPressed() {
        //Al pulsar el botón para volver a atrás se muestra un diálog para preguntar si el usuario quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salirConf)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle extras = getIntent().getExtras();
                        Intent i = new Intent(config.this, menuPrincipal.class);
                        String usuario = "";
                        if (extras != null) {
                            usuario = extras.getString("usuario");
                        }
                        i.putExtra("usuario", usuario);
                        setResult(RESULT_OK, i);
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