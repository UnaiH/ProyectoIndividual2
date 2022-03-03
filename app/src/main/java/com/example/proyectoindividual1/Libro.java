package com.example.proyectoindividual1;

import java.sql.Blob;

public class Libro {
    private String nombre;
    private String autor;
    private int paginas;
    private int actual;
    private String genero;
    private Blob imagen;
    public Libro(String pNombre, String pAutor, int pPaginas, String pGenero, Blob pImagen){
        this.actual = 0;
        this.nombre = pNombre;
        this.autor = pAutor;
        this.paginas = pPaginas;
        this.genero = pGenero;
        this.imagen = pImagen;
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
    public Blob getImagen(){
        return imagen;
    }
    public String getGenero(){
        return genero;
    }
    public void actualizarPaginas(int pag){
        this.actual = pag;
    }
}
