package com.example.proyectoindividual1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

//Es el menú principal una vez iniciada la sesión
public class menuPrincipal extends AppCompatActivity implements View.OnClickListener {
    private String usu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se realiza el cambio de idioma de la aplicación pues es la primera activiadad por la que se pasa tras haber estado en configuración. También se comprueba si se ha especificado si se quiere el modo oscuro o el que se poen de forma predeterminada.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprinc);
        Bundle extras = getIntent().getExtras();
        //Se da la bienvenida con la escritura del usuario en el TextView.
        if (extras != null) {
            usu= extras.getString("usuario");
            TextView bienvenida = (TextView) findViewById(R.id.bienvenidatext);
            bienvenida.setText(getResources().getString(R.string.bienv)+" "+usu);
        }
        if (savedInstanceState!= null) {
            usu= savedInstanceState.getString("usuario");
        }
        pedirpermisos();
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

    public void onClickLoc(View view){
        Bundle extras = getIntent().getExtras();
        Intent i = new Intent(this, locact.class);
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
    //Los dos sisguientes métodos se emplean para no perder el usuario si se cierra inesperadamente la actividad.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usu);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usu);
    }
    private void pedirpermisos() {
        if (ContextCompat.checkSelfPermission(menuPrincipal.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(menuPrincipal.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else if (ContextCompat.checkSelfPermission(menuPrincipal.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(menuPrincipal.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (ContextCompat.checkSelfPermission(menuPrincipal.this, Manifest.permission.WRITE_CALENDAR)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(menuPrincipal.this, new String[]{Manifest.permission.WRITE_CALENDAR},1);
        }
        if (ContextCompat.checkSelfPermission(menuPrincipal.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(menuPrincipal.this, new String[]{Manifest.permission.CAMERA},1);
        }
    }
}
