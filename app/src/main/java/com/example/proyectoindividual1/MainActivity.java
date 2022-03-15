package com.example.proyectoindividual1;
//Esta clase se emplea como pantalla de inicio al ejecutar la aplicación
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int valor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String idioma;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains("listapreferencias")) {
            String idim = prefs.getString("listapreferencias", "Español");
            if (idim.equals("Inglés")) {
                idioma = "en";
                Locale local = new Locale(idioma);
                Locale.setDefault(local);
                Configuration configuracion = getBaseContext().getResources().getConfiguration();
                configuracion.setLocale(local);
                configuracion.setLayoutDirection(local);
                Context context = getBaseContext().createConfigurationContext(configuracion);
                getBaseContext().getResources().updateConfiguration(configuracion,context.getResources().getDisplayMetrics());
            }
            else if (idim.equals("Euskera")){
                idioma = "eu";
                Locale local = new Locale(idioma);
                Locale.setDefault(local);
                Configuration configuracion = getBaseContext().getResources().getConfiguration();
                configuracion.setLocale(local);
                configuracion.setLayoutDirection(local);
                Context context = getBaseContext().createConfigurationContext(configuracion);
                getBaseContext().getResources().updateConfiguration(configuracion,context.getResources().getDisplayMetrics());
            }
            else{
                idioma = "es";
                Locale local = new Locale(idioma);
                Locale.setDefault(local);
                Configuration configuracion = getBaseContext().getResources().getConfiguration();
                configuracion.setLocale(local);
                configuracion.setLayoutDirection(local);
                Context context = getBaseContext().createConfigurationContext(configuracion);
                getBaseContext().getResources().updateConfiguration(configuracion,context.getResources().getDisplayMetrics());
            }
        }
        valor=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Existe un toast para indicar que al registrarse y volver a esta el registro se ha realizado correctamente.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            valor= extras.getInt("regok");
        }
        if ( valor == 1) {
            LayoutInflater inflater = getLayoutInflater();
            View el_layout = inflater.inflate(R.layout.toastregistro,(ViewGroup) findViewById(R.id.ltoastregpos));
            Toast toastcustomizado = new Toast(this);
            toastcustomizado.setGravity(Gravity.TOP, 0, 0);
            toastcustomizado.setDuration(Toast.LENGTH_LONG);
            toastcustomizado.setView(el_layout);
            toastcustomizado.show();
        }
        Log.i("TAG", "onCreate: ");
    }

    public void IniciarSesion(View view) {
        //OnClick del botón para iniciar sesión una vez se clica se lee lo escrito en EditText en el que se escribe el Usuario. Se comprueba que el archivo que tiene como nombre el usuario.txt y se comprueba que el fichero tiene la contraseña indicada en el EditText
        EditText usuarios = (EditText) findViewById(R.id.Usuario);
        String Linea ="";
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(
                    openFileInput(usuarios.getText().toString()+".txt")));
            Linea = ficherointerno.readLine();
            ficherointerno.close();
        } catch (IOException e) {
            Log.i("Error", "Error en el inicio de sesión");
        }
        EditText contr = (EditText) findViewById(R.id.Contr);
        if(contr.getText().toString().equals(Linea)) {
            Intent i = new Intent(this, menuPrincipal.class);
            i.putExtra("usuario", usuarios.getText().toString());
            setResult(RESULT_OK, i);
            finish();
            startActivity(i);
        }
        else{
            //Si la constraseña no coincide o en su defecto no existe el usuario se mostrará un toast indicando que las contraseñas no coinciden.
            LayoutInflater inflater = getLayoutInflater();
            View el_layout = inflater.inflate(R.layout.ltoastregneg,(ViewGroup) findViewById(R.id.ltoastregmal));
            Toast toastcustomizado = new Toast(this);
            toastcustomizado.setGravity(Gravity.TOP, 0, 0);
            toastcustomizado.setDuration(Toast.LENGTH_LONG);
            toastcustomizado.setView(el_layout);
            toastcustomizado.show();
        }
    }
    public void Registrarse(View view) {
        //El boton registrarse redirige a la actividad del registro.
        finish();
        Intent i = new Intent(this, registro.class);
        startActivity(i);
    }
    @Override
    public void onBackPressed() {
        //En caso de pulsar el botón de retroceder se lanzará un Dialog preguntando si realmente se quiere salir.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Seguro que deseas salir de la aplicacion?")
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
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

    @Override
    public void onClick(View view) {

    }
}