package com.example.proyectoindividual1;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConexionPHP  extends Worker {
    public ConexionPHP(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }
    //Esta clase se emplea unicamente para la conexión a la base de datos para iniciar sesión en la aplicación (se comprueba la contraseña en el .php correpondiente).
    @NonNull
    @Override
    public Result doWork()
    {
        String usuario = getInputData().getString("usuario");
        String contrasena = getInputData().getString("contrasena");
        Log.i("TAG1", "doWork: "+usuario);
        Log.i("TAG1", "doWork: "+contrasena);
        String direccion = "http://ec2-52-56-170-196.eu-west-2.compute.amazonaws.com/uhernandez008/WEB/iniciarSesion.php";
        HttpURLConnection urlConnection = null;
        try
        {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String parametros = "usu="+usuario+"&contrasena="+contrasena;
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();
            Log.i("TAG","statusCode: " + urlConnection);
            int statusCode = urlConnection.getResponseCode();
            Log.i("TAG","statusCode: " + statusCode);
            if (statusCode == 200)
            {
                BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, result = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                inputStream.close();
                JSONArray jsonArray = new JSONArray(result);
                String resultado="";
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    Log.i("TAG", "doWork: "+jsonArray.getJSONObject(i));
                    resultado = jsonArray.getJSONObject(i).getString("resultado");
                }
                Data json = new Data.Builder()
                        .putString("result",resultado)
                        .build();
                Log.i("php","listaJson: " + json);
                return Result.success(json);
            }
            return Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {e.printStackTrace();}
        return Result.failure();
    }
}