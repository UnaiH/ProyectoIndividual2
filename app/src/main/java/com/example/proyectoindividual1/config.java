package com.example.proyectoindividual1;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class config extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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