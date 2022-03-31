package com.example.proyectoindividual1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class leidos extends AppCompatActivity {
    //Esta clase implementa la interfaz para mostrar los libros ya leídos.
    String[] titulos = {};
    String[] autor = {};
    String[] fechaFin = {};
    String[] fechaInicio = {};
    Integer[] imag={R.drawable.libro1,R.drawable.libro2,R.drawable.libro3,R.drawable.libro4,R.drawable.libro5};
    private String usuario="";
    private ListView lista;
    private String[] libros;
    private static final int CAMERA_REQUEST = 1888;
    private int clicado=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Se realiza la carga de los datos necesarios
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        new Idiomas().setIdioma(this);
        int indice=0;
        ArrayList<Libro> librs;
        BD base = new BD(leidos.this);
        SQLiteDatabase db = base.getWritableDatabase();
        if (db != null) {
            librs = base.misLeidos(usuario);
            titulos = new String[librs.size()];
            autor = new String[librs.size()];
            fechaFin = new String[librs.size()];
            fechaInicio = new String[librs.size()];
            Iterator <Libro> it = librs.iterator();
            while(it.hasNext()){
                Libro libitr=it.next();
                titulos[indice]=libitr.getNombre();
                autor[indice]=libitr.getAutor();
                fechaFin[indice]=libitr.getFechaFin();
                fechaInicio[indice]=libitr.getFechaInicio();
                indice++;
            }
        }
        int longit = titulos.length;
        libros = new String[longit];
        Random rand = new Random();
        //Se realiza la selección aleatoria de los libros para cada elemento de la lista.
        for (int aux=0;aux<longit;aux++){
            libros[aux]=imag[rand.nextInt(imag.length)].toString();
        }
        new Pantalla().cambiarPantallaListas(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leidos);
        lista=(ListView)findViewById(R.id.listaleidos);
        AdaptadorLeidos adaptador = new AdaptadorLeidos(this, titulos, autor, fechaInicio, fechaFin, libros);
        //Se llama al adaptador de esta lista.
        lista.setAdapter(adaptador);
    }
    public void onBackPressed() {
        //Se programa un Dialog para preguntar si se desea salir de la pantalla al pulsar Back.
        Intent i = new Intent(this, tipo_listas.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.salidapan)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    public void onStart() {
        super.onStart();
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent camara = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara,CAMERA_REQUEST);
                clicado=i;
                return true;
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            String photo = getEncodedString(theImage);
            libros[clicado]=photo;
        }
    }
    private String getEncodedString(Bitmap bitmap){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);
    }
}