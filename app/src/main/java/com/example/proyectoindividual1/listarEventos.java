package com.example.proyectoindividual1;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
//Esta clase lista todos los eventos del Calendar que se corresponden con la aplicacion.
public class listarEventos extends AppCompatActivity {
    private String usuario="";
    private String [] ids = {};
    private String [] titulos = {};
    private String [] colores = {};
    private ListView lista;
    private Uri uri;
    private AdaptadorEventos adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Idiomas().setIdioma(this);
        new Pantalla().cambiarPantallaListas(this);
        if (savedInstanceState!= null) {
            usuario= savedInstanceState.getString("usuario");
        }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            usuario = extras.getString("usuario");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_eventos);
        //Se inician los elementos de la lista obteniendo la informacion necesaria de la aplicacion de Calendar.
        if ((ContextCompat.checkSelfPermission(listarEventos.this, Manifest.permission.READ_CALENDAR)== PackageManager.PERMISSION_GRANTED)) {
            uri = CalendarContract.Events.CONTENT_URI;
            String[] columnas = {
                    CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.EVENT_COLOR
            };
            String[] selectionArgs={""};
            selectionArgs[0]="Libuk"+usuario;
            Cursor cursor = getContentResolver().query(uri, columnas, CalendarContract.Events.DESCRIPTION+"=?",selectionArgs , null);

            ids = new String[cursor.getCount()];
            titulos = new String[cursor.getCount()];
            colores = new String[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                int id = cursor.getColumnIndex(columnas[0]);
                int titulo = cursor.getColumnIndex(columnas[1]);
                int color = cursor.getColumnIndex(columnas[2]);
                String idC=cursor.getString(id);
                String tituloC=cursor.getString(titulo);;
                String colorC;
                if (cursor.getString(color)==null) {
                    colorC = getString(R.string.nulo);
                }
                else{
                    if (cursor.getString(color).equals("-65536")){
                        colorC="Red";
                    }
                    else if (cursor.getString(color).equals("-16776961")){
                        colorC="Blue";
                    }
                    else{
                        colorC="Green";
                    }
                }
                ids[i]=idC;
                titulos[i]=tituloC;
                colores[i]=colorC;

                Log.i("TAG", "onCreate: " + idC + "," + tituloC + "," + colorC);
            }
            cursor.close();
            lista=(ListView)findViewById(R.id.listaEventos);
            adaptador = new AdaptadorEventos(this, ids, titulos, colores);
            lista.setAdapter(adaptador);

        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Para evitar problemas de incoherencia cuando hay llamadas por ejemplo.
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("usuario",usuario);
    }
    //Se emplea para por un lado, configurar que al pulsar se alterne el evento entre rojo, verde y azul y por otro lado, para que al mantener pulsado se elimine el evento.
    @Override
    public void onStart() {
        super.onStart();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContentValues mCambios = new ContentValues();
                if (colores[i].equals(getString(R.string.nulo)) ||colores[i].equals("Red")){
                    mCambios.put(CalendarContract.Events.EVENT_COLOR, Color.GREEN);
                }
                else if (colores[i].equals("Green")){
                    mCambios.put(CalendarContract.Events.EVENT_COLOR,Color.BLUE);
                }
                else{
                    mCambios.put(CalendarContract.Events.EVENT_COLOR,Color.RED);
                }
                String condicion=CalendarContract.Events._ID+"=?";
                String[] selectionArgs={""+ids[i]};
                getContentResolver().update(uri,mCambios,condicion,selectionArgs);
                adaptador.notifyDataSetChanged();
            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String condicion=CalendarContract.Events._ID+"=?";
                String[] selectionArgs={""+ids[i]};
                getContentResolver().delete(uri,condicion,selectionArgs);
                adaptador.notifyDataSetChanged();
                return true;
            }
        });
    }
    public void onBackPressed() {
        //Se programa un Dialog para preguntar si se desea salir de la pantalla al pulsar Back.
        Intent i = new Intent(this, tipo_listas.class);
        i.putExtra("usuario", usuario);
        setResult(RESULT_OK, i);
        finish();
        startActivity(i);
    }
}