package com.example.proyectoindividual1;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
//Este fragmento gestiona la interfaz que permite realizar cambios en los datos del libro seleccionado.
public class datos_leyendo extends Fragment{
    public Libro libof;
    private String usuario="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_leyendo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Se realizan los cambios necesarios en función de las preferencias.
        usuario = getArguments().getString("usuario");
        super.onViewCreated(view, savedInstanceState);
        EditText gen = view.findViewById(R.id.nuevogen);
        EditText marca = view.findViewById(R.id.nuevaMarca);
        if(libof!=null) {
            gen.setText(libof.getGenero());
            marca.setText("" + libof.getActual());
        }
        else{
            gen.setText("");
            marca.setText("" + 0);
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if ( prefs.contains("genero")) {
            Boolean genero = prefs.getBoolean("genero", true);
            if (!genero) {
                gen.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.AplicaGenLib).setVisibility(View.INVISIBLE);
            } else {
                gen.setVisibility(View.VISIBLE);
                view.findViewById(R.id.AplicaGenLib).setVisibility(View.VISIBLE);
            }
        }
        Button botoncam=(Button) view.findViewById(R.id.cambiarMarcaLib);
        Button botonfin=(Button) view.findViewById(R.id.FinalizarLib);
        Button aplic=(Button) view.findViewById(R.id.AplicaGenLib);
        if (libof!=null) {
            botoncam.setOnClickListener(this::onClickCambiarMarca);
            botonfin.setOnClickListener(this::onClickFinalizar);
            aplic.setOnClickListener(this::onClickAplicar);
        }
    }

    public void onClickCambiarMarca(View view) {
        //Acciones al pulsar el Cambiar si la página escrita es menor que la guardada en el libro previamente se lanza un Dialog y no se realizan cambios (el menor posible es 0). Si la página especifacada es mayor que la guardada se cambia. Si además es mayor que el número de páginas se finalizará el libro.
        view=getView();
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        EditText marca = (EditText) view.findViewById(R.id.nuevaMarca);
        int marcnum = Integer.parseInt(marca.getText().toString());
        if (marcnum<libof.getPaginas()) {
            if(marcnum<libof.getActual()){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.valorno)
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
            else{
                if (db != null) {
                    base.actualizarMarcador(marcnum, usuario, libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
                }
            }
        }
        else{
            String ahora = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            if (db != null) {
                base.actualizarFechaFin(ahora, usuario, libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
                notificarFin();
            }
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(libof, "noleer");
    }
    public void onClickFinalizar(View view) {
        //Gestionar el botón para finalzar los libros.
        view=getView();
        String ahora = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            base.actualizarFechaFin(ahora,usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
            notificarFin();
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(libof,"noleer");
    }
    public void onClickAplicar(View view) {
        //Gestiona el botón para cambiar el género del libro.
        view=getView();
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText gen = (EditText) view.findViewById(R.id.nuevogen);
            base.actualizarGenero(gen.getText().toString(),usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(libof,"noleer");
    }
    public void notificarFin(){
        //Realiza una notificación local para decir que el libro está finalizado.
        NotificationManager elManager = (NotificationManager)getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"CanalLibro");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
            elManager.createNotificationChannel(elCanal);
        }
        builder.setContentTitle(getResources().getString(R.string.fin))
                .setContentText(getResources().getString(R.string.felfin))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
        elManager.notify(1, builder.build());
    }
}