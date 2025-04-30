package com.example.beerstar.response;

public class responseLotes {

    private int id;                 // ID único para el lote
    private String marca;           // Marca del lote (por ejemplo, el nombre de la marca de cerveza)
    private double precio;          // Precio del lote
    private int stock;              // Cantidad disponible en stock para este lote
    private String imagen_nombre;   // Nombre de la imagen asociada al lote
    private String descripcion;     // Descripción del lote
    private String url;             // URL de la imagen del lote

    // Constructor que inicializa todos los campos de la clase
    public responseLotes(int id, String marca, double precio, int stock, String imagen_nombre, String descripcion, String url) {
        this.id = id;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.imagen_nombre = imagen_nombre;
        this.descripcion = descripcion;
        this.url = url;
    }

    // Métodos getter para obtener los valores de los atributos

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen_nombre() {
        return imagen_nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen_url() {
        return url;
    }

    // Métodos setter para actualizar los valores de los atributos

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImagen_nombre(String imagen_nombre) {
        this.imagen_nombre = imagen_nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen_url(String imagen_url) {
        this.url = imagen_url;
    }
}
