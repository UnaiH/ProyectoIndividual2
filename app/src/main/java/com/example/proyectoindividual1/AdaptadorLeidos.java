package com.example.proyectoindividual1;

import static com.example.proyectoindividual1.R.layout.activity_leidos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorLeidos extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] autores;
    private String[] fechafin;
    private String[] fechaInicio;
    private Integer[] imageid;
    private Activity context;

    public AdaptadorLeidos(Activity context, String[] titulos, String[] autores,String[] fechainicio, String[] fechafin, Integer[] imageid) {
        this.context = context;
        this.titulos = titulos;
        this.autores = autores;
        this.fechaInicio=fechainicio;
        this.fechafin = fechafin;
        this.imageid = imageid;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public Object getItem(int i) {
        return titulos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.elementosleidos,null);
        TextView textViewTitulo = (TextView) view.findViewById(R.id.tituloLeido);
        TextView textViewAutor = (TextView) view.findViewById(R.id.autorLibro);
        TextView textViewFechaIni = (TextView) view.findViewById(R.id.fechaInicio);
        TextView textViewFechaFin = (TextView) view.findViewById(R.id.fechaFin);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageLibro);

        textViewTitulo.setText(titulos[i]);
        textViewAutor.setText(autores[i]);
        textViewFechaIni.setText(fechaInicio[i]);
        textViewFechaFin.setText(fechafin[i]);
        imagen.setImageResource(imageid[i]);
        return  view;
    }
}
