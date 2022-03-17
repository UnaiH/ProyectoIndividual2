package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

//Es el menú principal una vez iniciada la sesión
public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Si en la configuración se ha indicado que se desea el modo oscuro o no.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idioma;
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
                setTheme(R.style.Theme_ProyectoIndividual1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprinc);
        Bundle extras = getIntent().getExtras();
        //Se da la bienvenida con la escritura del usuario en el TextView.
        if (extras != null) {
            String usu= extras.getString("usuario");
            TextView bienvenida = (TextView) findViewById(R.id.bienvenidatext);
            bienvenida.setText(getResources().getString(R.string.bienv)+" "+usu);
        }
    }

    public void onClickUsar(View view) {
        //El botón usar hará que se pase a tipo_listas
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, tipo_listas.class);
        String usuario = "";
        if (extras != null) {
           usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
    public void onClickConfig(View view) {
        //El botón configuración hará que se pase a config
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, config.class);
        String usuario = "";
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salida)
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
