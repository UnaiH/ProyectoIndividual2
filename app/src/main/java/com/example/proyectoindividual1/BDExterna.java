package com.example.proyectoindividual1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.work.WorkerParameters;

import java.util.ArrayList;

public class BDExterna extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "baseExterna.bd";

    public BDExterna(Context context, WorkerParameters workerParams) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public BDExterna(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Fotos (NombreUsuario VARCHAR(225), Titulo VARCHAR(225), Imagen VARCHAR(225), PRIMARY KEY (NombreUsuario,Titulo))");
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios (NombreUsuario VARCHAR(225), Contrasena VARCHAR(225), PRIMARY KEY (NombreUsuario))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean registrarse(String usuario, String contrasena){
        boolean existe=false;
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        Cursor c = bd.rawQuery("SELECT * FROM Usuarios WHERE Usuarios.NombreUsuario=='"+usuario+"'AND Usuarios.Contrasena=='"+contrasena+"'",null);
        while (c.moveToNext()){
            lista.add(c.getString(1));
        }
        if (lista.size()<=0) {
            existe=false;
            bd.execSQL("INSERT INTO Usuarios ('NombreUsuario', 'Contrasena') VALUES ('" + usuario + "','" + contrasena + "')");
            bd.close();
        }
        else{
            existe=true;
            bd.execSQL("UPDATE Usuarios SET Contrasena=='"+contrasena+"' WHERE NombreUsuario=='"+usuario+"'");
            bd.close();
        }
        return existe;
    }

    public boolean anadirFoto(String usuario, String titulo, String imagen){
        boolean existe =false;
        SQLiteDatabase bd = getWritableDatabase();
        ArrayList<String> lista = new ArrayList<String>();
        Cursor c = bd.rawQuery("SELECT * FROM Fotos WHERE Fotos.NombreUsuario=='"+usuario+"'AND Fotos.Titulo=='"+titulo+"'",null);
        while (c.moveToNext()){
            lista.add(c.getString(1));
        }
        if (lista.size()<=0) {
            existe=false;
            bd.execSQL("INSERT INTO Fotos ('NombreUsuario', 'Titulo', 'Imagen') VALUES ('" + usuario + "','" + titulo + "','"+imagen+"')");
            bd.close();
        }
        else{
            existe=true;
            bd.execSQL("UPDATE Fotos SET Imagen=='"+imagen+"' WHERE NombreUsuario=='"+usuario+"'AND Titulo=='"+titulo+"'");
            bd.close();
        }
        return existe;
    }
    public boolean iniciarSesion(String usuario, String contrasena){
        String contr ="";
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Usuarios WHERE Usuarios.NombreUsuario=='"+usuario+"'",null);
        while (c.moveToNext()){
            contr = c.getString(1);
        }
        c.close();
        bd.close();
        if (contr.equals(contrasena)){
            return true;
        }
        else{
            return false;
        }
    }
    public String getImagen(String usuario, String titulo){
        String imagen = "0";
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT * FROM Fotos WHERE Fotos.NombreUsuario=='"+usuario+"'AND Fotos.Titulo=='"+titulo+"'",null);
        while (c.moveToNext()){
            imagen=c.getString(2);
        }
        return imagen;
    }
}
