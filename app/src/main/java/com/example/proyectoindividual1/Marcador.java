package com.example.proyectoindividual1;
//Esta clase se emplea para guardar la información para después se emplea en uno de los mapas del GoogleMaps que emplea la aplicación en el que se emplea es para indicar las librerías del usuario.
public class Marcador {
    private double lat=0.0;
    private double lon=0.0;
    private String titulo="";
    public Marcador(String tit, double lat, double lon){
        this.lat=lat;
        this.lon=lon;
        this.titulo=tit;
    }

    public String getTit() {
        return titulo;
    }


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
