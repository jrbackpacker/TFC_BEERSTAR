package com.example.beerstar.response;

import com.google.gson.annotations.SerializedName;

public class responseProveedores {

    private int idProveedor;             // Identificador único del proveedor
    private Usuario usuario;             // Información del usuario asociado al proveedor
    private String nombre;               // Nombre del proveedor
    private String direccion;            // Dirección física del proveedor
    private String telefono;             // Teléfono del proveedor
    private String fechaRegistro;        // Fecha de registro del proveedor

    @SerializedName("url")               // Mapea el campo "url" del JSON al atributo "imagen"
    private String imagen;               // URL de la imagen del proveedor

    // Métodos getters para acceder a los valores de los atributos
    public int getIdProveedor() { return idProveedor; }
    public Usuario getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getFechaRegistro() { return fechaRegistro; }

    // Método que se puede usar para acceder a la URL de la imagen del proveedor
    public String getUrl() { return imagen; }

    // Clase interna Usuario que contiene información sobre el usuario del proveedor
    public static class Usuario {
        private int idUsuario;          // Identificador único del usuario
        private String email;           // Correo electrónico del usuario
        private String rol;             // Rol del usuario (por ejemplo: "admin", "user")
        private String tipoUsuario;     // Tipo de usuario (puede ser "proveedor", "cliente", etc.)

        // Métodos getters para acceder a los valores de los atributos del usuario
        public int getIdUsuario() { return idUsuario; }
        public String getEmail() { return email; }
        public String getRol() { return rol; }
        public String getTipoUsuario() { return tipoUsuario; }
    }
}
