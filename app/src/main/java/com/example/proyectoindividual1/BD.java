package com.example.proyectoindividual1;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class BD extends SQLiteOpenHelper {
    //Esta clase se encarga de la conexiones con la base de datos.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "base.bd";
    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Se crea la tabla en la base de datos que se denominará Libros.
        Log.i("TAG", "onCreate: Pasa");
        sqLiteDatabase.execSQL("CREATE TABLE Libros (NombreUsuario VARCHAR(225), Titulo VARCHAR(225), Autor VARCHAR(225), Genero VARCHAR(225), Paginas INTEGER, Actual INTEGER, Empezado VARCHAR(225), Acabado VARCHAR(225), Prevista VARCHAR(225), PRIMARY KEY (NombreUsuario,Titulo,Autor,Empezado))");
        sqLiteDatabase.execSQL("CREATE TABLE Marcadores (NombreUsuario VARCHAR(225), Titulo VARCHAR(225), Latitud REAL, Longitud REAL, PRIMARY KEY (NombreUsuario,Latitud,Longitud))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean anadirLibro(String usuario, String titulo, String autor, String genero, int pag, int act, String comienzo, String finprev){
        //Método para añadir una instancia en la tabla de Libros.
        boolean existe = false;
        String fin = "nulo";
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        Cursor c = bd.rawQuery("SELECT * FROM Libros WHERE Libros.NombreUsuario=='"+usuario+"'AND Libros.Titulo=='"+titulo+"' AND Libros.Autor=='"+autor+"' AND Libros.Empezado=='"+comienzo+"'",null);
        while (c.moveToNext()){
            lista.add(c.getString(1));
        }
        if (lista.size()<=0) {
            existe=false;
            bd.execSQL("INSERT INTO Libros ('NombreUsuario', 'Titulo', 'Autor', 'Genero', 'Paginas', 'Actual', 'Empezado', 'Acabado', 'Prevista') VALUES ('" + usuario + "','" + titulo + "','" + autor + "','" + genero + "','" + pag + "','" + act + "','" + comienzo + "','" + fin + "','" + finprev + "')");
            bd.close();
        }
        else{
            existe=true;
        }
        Log.i("TAG", "anadirLibro: "+existe);
        bd.close();
        return existe;
    }
    public boolean actualizarMarcador(int marcar,String usu, String tit, String autor, String emp){
        //Se emplea para actualizar el atributo que contiene la página por la que va el usuario.
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE Libros SET Actual=='"+marcar+"' WHERE NombreUsuario=='"+usu+"' AND Titulo == '"+tit+"' AND Empezado=='"+emp+"'AND Autor=='"+autor+"'");
        bd.close();
        return true;
    }
    public boolean actualizarFechaFin(String fin,String usu, String tit, String autor, String emp){
        //Se emplea para que cuando se actualiza la fecha de fin del libro en el momento en que el usuario lo indique dándole a un botón. Pues se da por finalizado un libro cuando el atributo "Acabado" no es null.
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE Libros SET Acabado=='"+fin+"' WHERE NombreUsuario=='"+usu+"' AND Titulo == '"+tit+"' AND Empezado=='"+emp+"'AND Autor=='"+autor+"'");
        bd.close();
        return true;
    }
    public ArrayList misLeidos(String usu){
        //Se obtiene  los libros que ya han sido leídos en la base de datos siendo estos los que tengan en Acabado un valor distinto de nulo.
        String fin = "nulo";
        ArrayList<Libro> misLibros = new ArrayList<Libro>();
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Libros WHERE Libros.NombreUsuario=='"+usu+"'AND Libros.Acabado!='"+fin+"'",null);
        while (c.moveToNext()){
            Libro libact = new Libro(c.getString(1),c.getString(2),c.getInt(4),c.getInt(5),c.getString(3),c.getString(6),c.getString(8),c.getString(7));
            misLibros.add(libact);
        }
        c.close();
        bd.close();
        return misLibros;
    }
    public ArrayList misLeyendo(String usu){
        //Se obtienen todos los libros que todavía no se ha finalizado siendo que "Acabado" no tiene de valor nulo.
        String fin = "nulo";
        ArrayList<Libro> misLibros = new ArrayList<Libro>();
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Libros WHERE Libros.NombreUsuario=='"+usu+"' AND Libros.Acabado=='"+fin+"'",null);
        while (c.moveToNext()){
            Libro libact = new Libro(c.getString(1),c.getString(2),c.getInt(4),c.getInt(5),c.getString(3),c.getString(6),c.getString(8),c.getString(7));
            misLibros.add(libact);
        }
        c.close();
        bd.close();
        return misLibros;
    }
    public boolean actualizarGenero(String gen,String usu, String tit, String autor, String emp){
        //Se emplea para actualizar el atributo que contiene el género del libro.
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE Libros SET Genero=='"+gen+"' WHERE NombreUsuario=='"+usu+"' AND Titulo == '"+tit+"' AND Empezado=='"+emp+"'AND Autor=='"+autor+"'");
        bd.close();
        return true;
    }
    //Se obtienen los marcadores guardados en la base de datos local.
    public ArrayList<Marcador> misMarcadores(String usu){
        //Se obtienen todos los marcadores.
        String fin = "nulo";
        ArrayList<Marcador> misMarcadores = new ArrayList<Marcador>();
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Marcadores WHERE Marcadores.NombreUsuario=='"+usu+"'",null);
        while (c.moveToNext()){
            Log.i("TAG", "misMarcadores: "+usu);
            Marcador mark = new Marcador(c.getString(1),c.getDouble(2),c.getDouble(3));
            misMarcadores.add(mark);
        }
        c.close();
        bd.close();
        return misMarcadores;
    }
    //Se guarda el usuario, titulo y coordenadas de los marcadores creados por el usuario.
    public boolean anadirMarcador(String usu, String tit, Double lat, Double lon){
        boolean existe = false;
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        Cursor c = bd.rawQuery("SELECT * FROM Marcadores WHERE Marcadores.NombreUsuario=='"+usu+"' AND Marcadores.Latitud=='"+lat+"'AND Marcadores.Longitud=='"+lon+"'",null);
        while (c.moveToNext()){
            lista.add(c.getString(1));
        }
        if (lista.size()<=0) {
            existe=false;
            bd.execSQL("INSERT INTO Marcadores ('NombreUsuario', 'Titulo', 'Latitud', 'Longitud') VALUES ('" + usu + "','" + tit + "','" + lat + "','" + lon + "')");
            bd.close();
        }
        else{
            existe=true;
        }
        bd.close();
        return existe;
    }
}
