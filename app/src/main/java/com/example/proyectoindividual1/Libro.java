package com.example.proyectoindividual1;

import java.sql.Blob;
import java.sql.Date;
import java.util.Calendar;

public class Libro {
    private String nombre;
    private String autor;
    private int paginas;
    private int actual;
    private String genero;
    private Date fechaInicio;
    private Date fechaFin;
    private Date fechaPrevista;
    public Libro(String pNombre, String pAutor, int pPaginas, String pGenero, Date fechaPrevista){
        this.actual = 0;
        this.nombre = pNombre;
        this.autor = pAutor;
        this.paginas = pPaginas;
        this.genero = pGenero;
        this.fechaInicio = (Date) Calendar.getInstance().getTime();
        this.fechaPrevista = fechaPrevista;
    }

    public int getActual() {
        return actual;
    }

    public int getPaginas() {
        return paginas;
    }
    public String getAutor(){
        return autor;
    }
    public String getNombre() {
        return nombre;
    }
    public String getGenero(){
        return genero;
    }
    public void actualizarPaginas(int pag){
        this.actual = pag;
    }
    public Date getFechaInicio(){
        return fechaInicio;
    }
    public Date getFechaFin() {
        return fechaFin;
    }
    public Date getFechaPrevista(){
        return fechaPrevista;
    }
    public void setFechaFin() {
        this.fechaFin = (Date) Calendar.getInstance().getTime();
    }
}
