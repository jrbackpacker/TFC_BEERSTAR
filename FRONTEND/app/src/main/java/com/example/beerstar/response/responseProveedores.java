package com.example.beerstar.response;

public class responseProveedores {
    private String logoUrl;
    private String nombre;
    private String descripcion;
    private String contacto;

    // Constructor para datos simulados
    public responseProveedores(String logoUrl, String nombre, String descripcion, String contacto) {
        this.logoUrl = logoUrl;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contacto = contacto;
    }

    // Getters
    public String getLogoUrl() {
        return logoUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getContacto() {
        return contacto;
    }
}