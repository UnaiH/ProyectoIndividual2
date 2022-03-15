package com.example.proyectoindividual1;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class BD extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "base.bd";
    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Libros (NombreUsuario VARCHAR(225), Titulo VARCHAR(225), Autor VARCHAR(225), Genero VARCHAR(225), Paginas INTEGER, Actual INTEGER, Empezado VARCHAR(225), Acabado VARCHAR(225), Prevista VARCHAR(225), PRIMARY KEY (NombreUsuario,Titulo,Autor,Empezado))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean anadirLibro(String usuario, String titulo, String autor, String genero, int pag, int act, String comienzo, String finprev){
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("INSERT INTO Libros ('NombreUsuario', 'Titulo', 'Autor', 'Genero', 'Paginas', 'Actual', 'Empezado', 'Prevista') VALUES ('"+usuario+"','"+titulo+"','"+autor+"','"+genero+"','"+pag+"','"+act+"','"+comienzo+"','"+finprev+"')");
        bd.close();
        return true;
    }
    public boolean actualizarMarcador(int marcar,String usu, String tit, String autor, LocalDate emp){
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE Libros SET Actual=='"+marcar+"' WHERE NombreUsuario=='"+usu+"' AND Titulo == '"+tit+"' AND Empezado=='"+emp+"'AND Autor=='"+autor+"'");
        bd.close();
        return true;
    }
    public boolean actualizarFechaFin(LocalDate fin,String usu, String tit, String autor, LocalDate emp){
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("UPDATE Libros SET Acabado=='"+fin+"' WHERE NombreUsuario=='"+usu+"' AND Titulo == '"+tit+"' AND Empezado=='"+emp+"'AND Autor=='"+autor+"'");
        bd.close();
        return true;
    }
    public ArrayList misLeidos(String usu){
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Libros WHERE NombreUsuario=='"+usu+"'",null);
        while (c.moveToNext()){
            Log.i("TAG", ""+c);
        }
        c.close();
        bd.close();
        ArrayList<Libro> misLibros = new ArrayList<Libro>();
        return misLibros;
    }
}
