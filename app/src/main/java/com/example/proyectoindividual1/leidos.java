package com.example.proyectoindividual1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    public String[] imagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new permisos().permisosCamara(leidos.this, leidos.this);
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
        //Se realiza la selección aleatoria de los libros para cada elemento de la lista si no hay imagenes para el libro en la base de datos remota.
        for (int aux=0;aux<longit;aux++){
            libros[aux]=imag[rand.nextInt(imag.length)].toString();
        }
        String[] imags;
        imags=getImagenes();
        if(imagenes!=null) {
            for (int aux = 0; aux < longit; aux++) {
                if(imagenes[aux] !=null) {
                    if (!imagenes[aux].equals("0")) {
                        libros[aux] = imagenes[aux];
                    }
                }
            }
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
    //Para realizar una foto al mantener pulsado un elemento en la lista.
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
    //Se recoge el resultado de sacar una foto con la camara para guardarlo en la base de datos remota.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap theImage = (Bitmap) data.getExtras().get("data");
            String photo = getEncodedString(theImage);
            Data.Builder datos = new Data.Builder();
            datos.putString("usuario",usuario);
            datos.putString("titulo", titulos[clicado]);
            datos.putString("imagen", photo);
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionPHPInsertImagenes.class)
                    .setInputData(datos.build())
                    .build();
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo)
                        {
                            if(workInfo != null && workInfo.getState().isFinished())
                            {
                                String inicio = workInfo.getOutputData().getString("result");
                                Log.i("TAG", "onChanged: "+inicio);
                                if (inicio!=null) {
                                    if (inicio.equals("true")) {
                                        Intent i = new Intent(getApplicationContext(), leidos.class);
                                        i.putExtra("usuario", usuario);
                                        setResult(RESULT_OK, i);
                                        finish();
                                        startActivity(i);
                                    } else {
                                        enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errbd));
                                    }
                                }
                                else {
                                    enviarMensajeLocal(getResources().getString(R.string.error),getResources().getString(R.string.errorreg));
                                }
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
        }
    }
    //Se lleva acabo la codificacion de la imagen en string para guardarla en la base de datos con una calidad no muy alta para evitar problemas de capacidad.
    private String getEncodedString(Bitmap bitmap){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,45, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);
    }
    private String[] getImagenes(){
        int indice=0;
        imagenes=new String[titulos.length];
        Log.i("Datos", "usuario: "+usuario);
        while (indice<titulos.length) {
            Data.Builder datos = new Data.Builder();
            datos.putString("usuario", usuario);
            datos.putString("titulo", titulos[indice]);
            OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(ConexionPHPImagenes.class)
                    .setInputData(datos.build())
                    .build();
            int finalIndice = indice;
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                    .observe(this, new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if (workInfo != null && workInfo.getState().isFinished()) {
                                String foto = workInfo.getOutputData().getString("foto");
                                if (foto != null) {
                                    if (!foto.equals("false")) {
                                        try {
                                            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(usuario+titulos[finalIndice]+finalIndice +".txt", Context.MODE_PRIVATE));
                                            fichero.write(foto);
                                            fichero.close();
                                        } catch (IOException e) {
                                            //Si hay un error se comunica mediante una notificación.
                                            Log.i("Error", "Error Imag");
                                        }
                                    }
                                    else{
                                        try {
                                            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput(usuario+titulos[finalIndice]+finalIndice +".txt", Context.MODE_PRIVATE));
                                            fichero.write("0");
                                            fichero.close();
                                        } catch (IOException e) {
                                            //Si hay un error se comunica mediante una notificación.
                                            Log.i("Error", "Error Imag");
                                        }
                                    }
                                }
                            }
                        }
                    });
            WorkManager.getInstance(this).enqueue(otwr);
            String Linea ="";
            String imagen = "";
            try {
                String archivo= usuario;
                archivo += titulos[indice];
                archivo+=indice;
                Log.i("indicefinal", "getImagenes: "+archivo);
                BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(openFileInput(archivo+".txt")));
                Linea=ficherointerno.readLine();
                while (Linea!=null) {
                    imagen=imagen+Linea;
                    Linea = ficherointerno.readLine();
                }
                ficherointerno.close();
            } catch (IOException e) {
                //Si se produjera un error se lanzará una notificación local para informar de que se ha producido y dónde.
                Log.i("Error", "Error");
            }
            Log.i("Leido", ""+imagen);
            imagenes[indice]=imagen;
            indice++;
        }
        return imagenes;
    }
    private void enviarMensajeLocal(String titulo, String contenido){
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(leidos.this, "CanalLibro");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("CanalLibro", "Mi Notificacion", NotificationManager.IMPORTANCE_HIGH);
            elManager.createNotificationChannel(elCanal);
        }
        builder.setContentTitle(titulo)
                .setContentText(contenido)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
        elManager.notify(1, builder.build());
    }
}