package com.example.proyectoindividual1;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
//Esta clase implementa el fragment de la lista de libros que se están leyendo.
public class listaleyendo extends ListFragment{
    String[] titulos = {};
    String[] autor = {};
    String[] fechaPrev = {};
    String[] fechaInicio = {};
    Integer[] imag={R.drawable.libroabierto,R.drawable.libroabierto2,R.drawable.libroabierto3,R.drawable.libroabierto4,R.drawable.libroabierto5};
    Integer[] act={};
    Integer[] paginas={};
    private String usuario;
    private ArrayList<Libro> librs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listaleyendo,container,false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        //Se realiza la carga de los datos necesarios y las llamadas a la base de datos requeridas.
        int indice=0;
        usuario = getArguments().getString("usuario");
        BD base = new BD(this.getContext());
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            librs = base.misLeyendo(usuario);
            titulos = new String[librs.size()];
            autor = new String[librs.size()];
            fechaPrev = new String[librs.size()];
            fechaInicio = new String[librs.size()];
            act = new Integer[librs.size()];
            paginas = new Integer[librs.size()];
            Iterator<Libro> it = librs.iterator();
            while(it.hasNext()){
                Libro libitr=it.next();
                titulos[indice]=libitr.getNombre();
                autor[indice]=libitr.getAutor();
                fechaPrev[indice]=libitr.getFechaPrevista();
                fechaInicio[indice]=libitr.getFechaInicio();
                act[indice]=libitr.getActual();
                paginas[indice]=libitr.getPaginas();
                indice++;
            }
        }
        int longit = titulos.length;
        Integer[] libros = new Integer[longit];
        Random rand = new Random();
        int auxiliar=0;
        //Se seleccionan las imágenes aleatoriamente para mostrarlas por cada elemento de la lista.
        for (int aux=0;aux<longit;aux++){
            auxiliar=rand.nextInt(imag.length);
            libros[aux]=imag[auxiliar];
        }
        super.onViewCreated(view, savedInstanceState);
        AdaptadorLeyendo adaptador = new AdaptadorLeyendo(this.getActivity(), titulos, autor, fechaInicio, fechaPrev, libros, act, paginas);
        setListAdapter(adaptador);
    }
    //En éste método se implementa lo que sucede al pulsar un elemento de la lista.
    @Override
    public void onStart() {
        super.onStart();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Libro lib1 = librs.get(i);
                activListaleyendo leyendo = (activListaleyendo) getActivity();
                String leer="leer";
                leyendo.gestorFragmentos(lib1,leer);
            }
        });
    }
}