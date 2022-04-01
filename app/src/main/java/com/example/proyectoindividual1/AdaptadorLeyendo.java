package com.example.proyectoindividual1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
//Esta clase es el adaptador de la lista (en un fragment) de libros que se están leyendo.
public class AdaptadorLeyendo extends BaseAdapter {
    private LayoutInflater inflater;
    private String[] titulos;
    private String[] autores;
    private String[] fechaprev;
    private String[] fechaInicio;
    private String[] imageid;
    private Integer[] pagact;
    private Integer[] numpag;
    private Activity context;
    public AdaptadorLeyendo(Activity context, String[] titulos, String[] autores, String[] fechainicio, String[] fechaprev, String[] imageid, Integer[] act, Integer[] num){
        this.titulos=titulos;
        this.autores=autores;
        this.fechaInicio=fechainicio;
        this.fechaprev=fechaprev;
        this.imageid=imageid;
        this.pagact=act;
        this.numpag=num;
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
        view=inflater.inflate(R.layout.elementosleyendo,null);
        TextView textViewTitulo = (TextView) view.findViewById(R.id.tituloLeido);
        TextView textViewAutor = (TextView) view.findViewById(R.id.autorLibro);
        TextView textViewFechaIni = (TextView) view.findViewById(R.id.fechaInicio);
        TextView textViewFechaFin = (TextView) view.findViewById(R.id.fechaPrev);
        ImageView imagen = (ImageView) view.findViewById(R.id.imageLibro);
        TextView textViewactual = (TextView) view.findViewById(R.id.paginaAct);
        TextView textViewpaginas = (TextView) view.findViewById(R.id.paginas);

        textViewTitulo.setText(titulos[i]);
        textViewAutor.setText(autores[i]);
        textViewFechaIni.setText(fechaInicio[i]);
        textViewFechaFin.setText(fechaprev[i]);
        Log.i("TAG", "getView:"+imageid[i]);
        if (imageid[i].equals("2131230859")||imageid[i].equals("2131230860")||imageid[i].equals("2131230861")||imageid[i].equals("2131230862")||imageid[i].equals("2131230863")){
            imagen.setImageResource(Integer.parseInt(imageid[i]));
        }
        else{
            imagen.setImageBitmap(StringToBitMap(imageid[i]));
        }
        textViewactual.setText(pagact[i].toString());
        textViewpaginas.setText(numpag[i].toString());

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
