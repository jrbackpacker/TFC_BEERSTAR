package com.example.beerstar.service;

public class Usuario {
    private String email; // ✅ Corregido
    private String password;
    private String telefono;
    private String direccion;
    private String nombre;



    public Usuario(String email, String nombre) { // Cambiado el nombre del parámetro
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;

    }

    public Usuario(String email, String password, String direccion, String telefono) {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() { // También corregido
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}


