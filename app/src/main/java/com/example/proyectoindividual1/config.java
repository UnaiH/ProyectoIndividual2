package com.example.proyectoindividual1;

import android.app.FragmentTransaction;
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getBoolean("tema", false)){
            setTheme(R.style.ModoOscuro);
        }
        else {
            setTheme(R.style.Theme_ProyectoIndividual1);
        }
        finish();
        Intent i = new Intent(this, menuPrincipal.class);
        startActivity(i);
    }

    public static class configuracion extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_config);
        }
    }
}