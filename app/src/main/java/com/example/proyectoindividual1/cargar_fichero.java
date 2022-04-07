package com.example.proyectoindividual1;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class cargar_fichero {
    cargar_fichero(){

    }
    public ArrayList<String> obtenerListaAutores(Context contexto){
        ArrayList<String> lista = new ArrayList<String>();
        try {
            BufferedReader ficherointerno = new BufferedReader(new InputStreamReader(contexto.openFileInput("recomautores.txt")));
            String linea = ficherointerno.readLine();
            while (linea != null) {
                lista.add(linea);
                linea = ficherointerno.readLine();
            }
            ficherointerno.close();
        }
        catch(IOException e){
            Log.i("Error", "onClickAnadir");
        }

        return lista;
    }
}
