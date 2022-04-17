package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
//Esta clase gestiona la interfaz que permite al usuario identificado comunicar un error.
public class mensajeerror extends AppCompatActivity {
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Llama a idiomas y pantalla para realizar los cambios de la interfaz acorde a lo especificado en las preferencias.
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaMenus(this);
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeerror);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, menuPrincipal.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(i);
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
}