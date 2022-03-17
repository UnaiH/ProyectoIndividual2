package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class config extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.config);
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
        //Este es el botón aplicar de config.
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
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_config);
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la configuración? Se guardaran los cambios realizados.")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
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
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}