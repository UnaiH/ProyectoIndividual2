package com.example.proyectoindividual1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String,String> parseJsonObject(JSONObject objeto) throws JSONException {
        HashMap<String,String> listaDatos = new HashMap<>();
        try {
            String nombre = objeto.getString("name");
            String lat = objeto.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String lon = objeto.getJSONObject("geometry").getJSONObject("location").getString("lng");
            listaDatos.put("name",nombre);
            listaDatos.put("lat",lat);
            listaDatos.put("lng",lon);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listaDatos;
    }
    private List<HashMap<String,String>> parseJSONArray(JSONArray jsonArray){
        List<HashMap<String,String>> listaDatos = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            try {
                HashMap<String,String> datos = parseJsonObject((JSONObject) jsonArray.get(i));
                listaDatos.add(datos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return listaDatos;
    }
    public List<HashMap<String,String>> parseResult(JSONObject objeto){
        JSONArray jsonArray = null;
        try {
            jsonArray = objeto.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJSONArray(jsonArray);
    }
}
