package com.example.beerstar.response;

import java.io.Serializable;

public class responseArticulo implements Serializable {
    private String imagenUrl;
    private String nombre;
    private String descripcion;
    private double precio;

    public responseArticulo(String imagenUrl, String nombre, String descripcion, double precio) {
        this.imagenUrl = imagenUrl;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }
}
