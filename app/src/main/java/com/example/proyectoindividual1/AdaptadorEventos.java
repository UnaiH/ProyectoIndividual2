package com.example.proyectoindividual1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//Esta clase es el adaptador de la lista de eventos del calendar de la aplicacion.
public class AdaptadorEventos extends BaseAdapter {
    private Activity context;
    private String[] ids;
    private String[] titulos;
    private String[] colores;
    private LayoutInflater inflater;
    public AdaptadorEventos(Activity context,String[] ids, String[] titulos, String[] colores){
        this.context=context;
        this.ids=ids;
        this.titulos=titulos;
        this.colores=colores;
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
        view=inflater.inflate(R.layout.elemevento,null);
        TextView textViewIDS = (TextView) view.findViewById(R.id.idse);
        TextView textViewTitulos = (TextView) view.findViewById(R.id.titulose);
        TextView textViewColores = (TextView) view.findViewById(R.id.colorese);

        textViewIDS.setText(ids[i]);
        textViewTitulos.setText(titulos[i]);
        textViewColores.setText(colores[i]);
        return view;
    }
}
