package com.example.proyectoindividual1;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
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
//Esta clase realiza la conexion a la base de datos externa para obtener la imagen de cada libro realizada por el usuario.
public class ConexionPHPImagenes extends Worker {
    public ConexionPHPImagenes(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork()
    {
        String usuario = getInputData().getString("usuario");
        String titulo = getInputData().getString("titulo");
        String direccion = "http://ec2-18-132-60-229.eu-west-2.compute.amazonaws.com/uhernandez008/WEB/selectImagen.php";
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
            String parametros = "usuario="+usuario+"&titulo="+titulo;
            Log.i("Parametros", "doWork: "+parametros);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();

            int statusCode = urlConnection.getResponseCode();
            Log.i("php","statusCode: " + statusCode);
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
                    Log.i("JSONImagenes", "doWork: "+jsonArray.getJSONObject(i));
                    resultado = jsonArray.getJSONObject(i).getString("resultado");
                    Log.i("JSONImagenes", "doWork: "+resultado);
                }
                Data json = new Data.Builder()
                        .putString("foto",resultado)
                        .build();
                return Result.success(json);
            }
            return Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {e.printStackTrace();}
        return ListenableWorker.Result.failure();
    }
}
