package com.example.proyectoindividual1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class datos_leyendo extends Fragment implements View.OnClickListener {
    public Libro libof;
    private String usuario="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_datos_leyendo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        usuario = getArguments().getString("usuario");
        super.onViewCreated(view, savedInstanceState);
        EditText gen = view.findViewById(R.id.nuevogen);
        gen.setText(libof.getGenero());
    }

    @Override
    public void onClick(View view) {

    }
    public void onClickCambiarMarca(View view) {
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText marca = (EditText) view.findViewById(R.id.nuevaMarca);
            int marcnum = Integer.parseInt(marca.getText().toString());
            base.actualizarMarcador(marcnum,usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(null);
    }
    public void onClickFinalizar(View view) {
        String ahora = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText marca = (EditText) view.findViewById(R.id.nuevaMarca);
            base.actualizarFechaFin(ahora,usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(null);
    }
    public void onClickAplicar(View view) {
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            EditText gen = (EditText) view.findViewById(R.id.nuevogen);
            base.actualizarGenero(gen.getText().toString(),usuario,libof.getNombre(), libof.getAutor(), libof.getFechaInicio());
        }
        activListaleyendo leyendo = (activListaleyendo) getActivity();
        leyendo.gestorFragmentos(null);
    }
}