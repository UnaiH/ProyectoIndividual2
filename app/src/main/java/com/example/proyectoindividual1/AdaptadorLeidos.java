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
        private String[] titulos;
        private String[] autores;
        private String[] fechafin;
        private Integer[] imageid;
        private Activity context;

        public AdaptadorLeidos(Activity context, String[] titulos, String[] autores, String[] fechafin, Integer[] imageid) {
            this.context = context;
            this.titulos = titulos;
            this.autores = autores;
            this.fechafin = fechafin;
            this.imageid = imageid;
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
        View row=view;
        LayoutInflater inflater = context.getLayoutInflater();
        if(view==null)
            row = inflater.inflate(activity_leidos, null, true);
        TextView textViewTitulo = (TextView) row.findViewById(R.id.tituloLeido);
        TextView textViewAutor = (TextView) row.findViewById(R.id.autorLibro);
        TextView textViewFechaFin = (TextView) row.findViewById(R.id.fechaFin);
        ImageView imagen = (ImageView) row.findViewById(R.id.imageLibro);

        textViewTitulo.setText(titulos[i]);
        textViewAutor.setText(autores[i]);
        textViewFechaFin.setText(fechafin[i]);
        imagen.setImageResource(imageid[i]);
        return  row;
    }
}
