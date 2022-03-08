package com.example.proyectoindividual1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class anadirLibroALista extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_anadir_libro_alista);
    }
    @Override
    public void onClick(View view) {
        EditText titulo = (EditText) findViewById(R.id.ntitulo2);
        EditText autor = (EditText) findViewById(R.id.nautor3);
        EditText paginas = (EditText) findViewById(R.id.npag2);
        EditText genero = (EditText) findViewById(R.id.ngenero);

        if (paginas==null || Integer.parseInt(paginas.getText().toString())<=0){
            NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(anadirLibroALista.this,"CanalLibro");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
                elManager.createNotificationChannel(elCanal);
            }
            builder.setContentTitle("Registro libro")
                    .setContentText("No se ha podido registrar el libro correctamente debido a que no se han definido las páginas o es un número no válido")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true);
            elManager.notify(1, builder.build());
        }
        else{
            class DialogoAnadirLibro extends DialogFragment{
                @NonNull
                @Override
                public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                    super.onCreateDialog(savedInstanceState);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Añadir libro");
                    builder.setMessage("¿Deseas añadir el libro?");
                    builder.setPositiveButton("Por supuesto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    return builder.create();
                }
            }
        }
        finish();
        Intent i = new Intent(this, anadirLibroALista.class);
        startActivity(i);
    }
}