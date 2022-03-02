package com.example.proyectoindividual1;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "base.bd";
    public BD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios ('NombreUsuario' VARCHAR(225) PRIMARY KEY, 'Contrasena' VARCHAR(255))");
        sqLiteDatabase.execSQL("CREATE TABLE Libros ('Titulo' VARCHAR(225), 'Paginas' INTEGER, 'Actual' INTEGER, 'Acabado' INTEGER, 'Portada' VARCHAR(225))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Libro getLibros(){

    }
}
