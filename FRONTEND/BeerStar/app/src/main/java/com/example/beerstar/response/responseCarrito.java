package com.example.beerstar.response;

public class responseCarrito {

    // Enum que define los dos tipos posibles: PRODUCTO o LOTE
    public enum Tipo {
        PRODUCTO, LOTE
    }

    private String nombre;         // Nombre del producto o lote
    private String descripcion;    // Descripción del producto o lote
    private double precio;         // Precio del producto o lote
    private int cantidad;          // Cantidad de unidades en el carrito
    private String imagenUrl;      // URL de la imagen del producto o lote
    private Tipo tipo;             // Tipo que puede ser producto o lote

    // Constructor que inicializa todos los valores del carrito
    public responseCarrito(String nombre, String descripcion, double precio, int cantidad, String imagenUrl, Tipo tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagenUrl = imagenUrl;
        this.tipo = tipo;
    }

    // Métodos getter para obtener los valores de las propiedades

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public Tipo getTipo() {
        return tipo;
    }

    // Método setter para actualizar la cantidad en el carrito
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
