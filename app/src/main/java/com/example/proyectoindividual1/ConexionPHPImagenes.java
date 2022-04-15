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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ConexionPHPImagenes extends Worker {
    public ConexionPHPImagenes(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork()
    {
        String direccion = "http://ec2-18-132-60-229.eu-west-2.compute.amazonaws.com/uhernandez008/WEB/selectImagen.php";
        HttpURLConnection urlConnection = null;
        try
        {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

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
                ArrayList<String> lista = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    String imagen = jsonArray.getJSONObject(i).getString("imagen");
                    lista.add(imagen);
                }
                Log.i("php","listaJson: " + lista);
                Data jason = new Data.Builder()
                        .putString("nombres",lista.toString())
                        .build();
                return ListenableWorker.Result.success(jason);
            }
            return ListenableWorker.Result.failure();
        }
        catch (MalformedURLException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (JSONException e) {e.printStackTrace();}
        return ListenableWorker.Result.failure();
    }
}
