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
    private static LayoutInflater inflater = null;
    private String[] titulos;
    private String[] autores;
    private String[] fechafin;
    private String[] fechaInicio;
    private Integer[] imageid;
    private Activity context;

    public AdaptadorLeidos(Context context, String[] titulos, String[] autores, String[] fechafin, String[] fechaInicio, Integer[] imageid) {
        this.context = (Activity) context;
        this.titulos = titulos;
        this.autores = autores;
        this.fechafin = fechafin;
        this.fechaInicio = fechaInicio;
        this.imageid = imageid;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
        final View row = inflater.inflate(R.layout.elementosleidos, null);
        TextView textViewTitulo = (TextView) row.findViewById(R.id.tituloLeido);
        TextView textViewAutor = (TextView) row.findViewById(R.id.autorLibro);
        TextView textViewFechaFin = (TextView) row.findViewById(R.id.fechaFin);
        TextView textViewFechaInicio = (TextView) row.findViewById(R.id.fechaInicio);
        ImageView imagen = (ImageView) row.findViewById(R.id.imageLibro);

        textViewTitulo.setText(titulos[i]);
        textViewAutor.setText(autores[i]);
        textViewFechaFin.setText(fechafin[i]);
        textViewFechaInicio.setText(fechaInicio[i]);
        imagen.setImageResource(imageid[i]);
        return null;
    }
}
