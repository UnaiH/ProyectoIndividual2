package com.example.proyectoindividual1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

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
        view=getView();
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText marca = (EditText) view.findViewById(R.id.nuevaMarca);
            int marcnum = Integer.parseInt(marca.getText().toString());
            base.actualizarMarcador(marcnum,usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(libof,"noleer");
    }
    public void onClickFinalizar(View view) {
        view=getView();
        String ahora = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText marca = (EditText) view.findViewById(R.id.nuevaMarca);
            base.actualizarFechaFin(ahora,usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(libof,"noleer");
    }
    public void onClickAplicar(View view) {
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
}