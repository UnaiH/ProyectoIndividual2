package com.example.proyectoindividual1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Locale;

public class activListaleyendo extends AppCompatActivity{
    //Esta lista se encarga de gestionar los fragments.
    private boolean listaact=true;
    private String usu= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        usu = "";
        if (extras != null) {
            usu = extras.getString("usuario");
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma;
        //Para que el idioma esté acorde a las preferencias al igual que el tema de la interfaz.
        if (prefs.contains("listapreferencias")) {
            String idim = prefs.getString("listapreferencias", "Español");
            if (idim.equals("Español")) {
                idioma = "es";
                this.cambiarIdioma(idioma);
            }
            else if (idim.equals("Euskara")){
                idioma = "eu";
                this.cambiarIdioma(idioma);
            }
            else{
                idioma = "en";
                this.cambiarIdioma(idioma);
            }
        }
        if (prefs.contains("tema")) {
            Boolean modOsc = prefs.getBoolean("tema", false);
            if (modOsc) {
                setTheme(R.style.ModoOscuro);
            } else {
                setTheme(R.style.Listas);
            }
        }
        //Se carga el fragment correspondiente al carga que es el de la lista.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leyendo);
        Bundle bundle = new Bundle();
        bundle.putString("usuario",usu);
        listaleyendo fragmento = new listaleyendo();
        fragmento.setArguments(bundle);
        FragmentManager gestor = getSupportFragmentManager();
        FragmentTransaction transaccion = gestor.beginTransaction();
        transaccion.replace(R.id.fragmentos,fragmento);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Se tiene un fragmento en blanco que se emplea para "reiniciar" el content del fragment cuando se cambia la orientación.
            transaccion.replace(R.id.fragmentosdatos,new fragmentoblanco());
        }
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
    public void gestorFragmentos(Libro lib1, String leer){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (listaact && leer.equals("leer")){
                listaact=false;
                FragmentManager gestor = getSupportFragmentManager();
                FragmentTransaction ftransac = gestor.beginTransaction();
                datos_leyendo fragmento = new datos_leyendo();
                Bundle bundle = new Bundle();
                bundle.putString("usuario",usu);
                fragmento.setArguments(bundle);
                fragmento.libof=lib1;
                ftransac.replace(R.id.fragmentosdatos,fragmento);
                ftransac.commit();
            }
            else{
                listaact=true;
                FragmentManager gestor = getSupportFragmentManager();
                FragmentTransaction ftransac = gestor.beginTransaction();
                listaleyendo fragmento = new listaleyendo();
                gestor.popBackStack();
                Bundle bundle = new Bundle();
                bundle.putString("usuario",usu);
                fragmento.setArguments(bundle);
                ftransac.replace(R.id.fragmentos,fragmento);
                ftransac.commit();
            }
        }
        else{
            if(listaact && leer.equals("leer")){
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
                gestor.popBackStack();
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

    private void cambiarIdioma(String idioma){
        Locale local = new Locale(idioma);
        Locale.setDefault(local);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.setLocale(local);
        config.setLayoutDirection(local);
        Context con = getBaseContext().createConfigurationContext(config);
        getBaseContext().getResources().updateConfiguration(config,con.getResources().getDisplayMetrics());
    }
}