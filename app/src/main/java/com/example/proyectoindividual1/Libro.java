package com.example.proyectoindividual1;

public class Libro {
    private String nombre;
    private int paginas;
    private int actual;
    public Libro(String pNombre, int pPaginas){
        this.actual = 0;
        this.nombre = pNombre;
        this.paginas = pPaginas;
    }

    public int getActual() {
        return actual;
    }

    public int getPaginas() {
        return paginas;
    }

    public String getNombre() {
        return nombre;
    }
    public void actualizarPaginas(int pag){
        this.actual = pag;
    }
}
