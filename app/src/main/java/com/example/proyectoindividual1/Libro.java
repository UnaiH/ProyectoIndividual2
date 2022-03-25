package com.example.proyectoindividual1;

import java.sql.Blob;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Libro {
    //Esta clase denominada Libro se empleará para guardar los datos de cada instancia de la base de datos al cargarlos. Además se implementan los get necesarios a la clase Libro.
    private String nombre;
    private String autor;
    private int paginas;
    private int actual;
    private String genero;
    private String fechaInicio;
    private String fechaFin;
    private String fechaPrevista;

    public Libro(String pNombre, String pAutor, int pPaginas, int pAct, String pGenero, String pfechaInit, String pfechaPrevista, String pfechaFin) {
        this.actual = 0;
        this.nombre = pNombre;
        this.autor = pAutor;
        this.paginas = pPaginas;
        this.actual = pAct;
        this.genero = pGenero;
        this.fechaInicio = pfechaInit;
        this.fechaPrevista = pfechaPrevista;
        this.fechaFin = pfechaFin;
    }

    public int getActual() {
        return actual;
    }

    public int getPaginas() {
        return paginas;
    }

    public String getAutor() {
        return autor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getFechaPrevista() {
        return fechaPrevista;
    }
}
