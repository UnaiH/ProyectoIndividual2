package com.example.proyectoindividual1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//Esta calse es el adaptador de la lista de los que se han leído.
public class AdaptadorLeidos extends BaseAdapter {
    //Esta clase es el adaptador de la lista de leidos y es el que se ocupa de ello para que además cada elemento de la lista esté personalizado de una forma en concreto.
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] autores;
    private String[] fechafin;
    private String[] fechaInicio;
    private String[] imageid;
    private Activity context;

    public AdaptadorLeidos(Activity context, String[] titulos, String[] autores,String[] fechainicio, String[] fechafin, String[] imageid) {
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
        TextView textViewFechaFin = (TextView) view.findViewById(R.id.fechaPrev);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageLibro);

        textViewTitulo.setText(titulos[i]);
        textViewAutor.setText(autores[i]);
        textViewFechaIni.setText(fechaInicio[i]);
        textViewFechaFin.setText(fechafin[i]);
        if (imageid[i].equals(2131230854)||imageid[i].equals("2131230855")||imageid[i].equals("2131230856")||imageid[i].equals("2131230857")||imageid[i].equals("2131230858")){
            imagen.setImageResource(Integer.parseInt(imageid[i]));
        }
        else{
            imagen.setImageBitmap(StringToBitMap(imageid[i]));
        }
        return  view;
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
