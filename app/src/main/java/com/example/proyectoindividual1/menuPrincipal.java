package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
//Es el menú principal una vez iniciada la sesión
public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Si en la configuración se ha indicado que se desea el modo oscuro o no.
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
        setContentView(R.layout.menuprinc);
        Bundle extras = getIntent().getExtras();
        //Se da la bienvenida con la escritura del usuario en el TextView.
        if (extras != null) {
            String usu= extras.getString("usuario");
            TextView bienvenida = (TextView) findViewById(R.id.bienvenidatext);
            bienvenida.setText("Te damos la bienvenida "+usu);
        }
    }

    public void onClickUsar(View view) {
        //El botón usar hará que se pase a tipo_listas
        finish();
        Intent i = new Intent(this, tipo_listas.class);
        startActivity(i);
    }
    public void onClickConfig(View view) {
        //El botón usar hará que se pase a config
        finish();
        Intent i = new Intent(this, config.class);
        startActivity(i);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la aplicacion?")
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
