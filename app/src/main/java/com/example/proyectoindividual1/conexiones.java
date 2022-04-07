package com.example.proyectoindividual1;

import android.content.Context;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class conexiones extends Worker {
    public conexiones(Context context, WorkerParameters params) {
        super(context, params);
    }
    @Override
    public Result doWork(){
        Connection conn=null;
        try{
            String ip = "255.255.255.255";
            String nombreBD = "NombreBD";
            String usuario = "usuario";
            String password = "password";
            String conexion = "jdbc:mysql://" + ip + "/" + nombreBD + "";
            conn = DriverManager.getConnection(conexion, usuario, password);
            Statement sentencia = conn.createStatement();
            ResultSet resultado = sentencia.executeQuery("Select * from Usuarios");
            while(resultado.next())
            {
                Log.i("TAG", "doWork: "+resultado);
            }
        }catch(Exception e) {
            Log.i("Error", "Error de conexión" +e.toString());
        }
        return null;
    }
}
