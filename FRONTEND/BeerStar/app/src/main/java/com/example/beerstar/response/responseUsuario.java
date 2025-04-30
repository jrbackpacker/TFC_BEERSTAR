package com.example.beerstar.response;

import java.io.Serializable;

public class responseUsuario implements Serializable {

    private boolean success;        // Indica si la operación fue exitosa (por ejemplo, si el inicio de sesión fue correcto).
    private String mensaje;         // Un mensaje informativo sobre el resultado de la operación (ej., "Inicio de sesión exitoso").
    private int idUsuario;          // El identificador único del usuario en el sistema.
    private String rol;             // El rol del usuario (por ejemplo, "admin", "cliente").
    private String tipoUsuario;     // El tipo de usuario (puede ser "proveedor", "cliente", etc.).

    // Constructor
    public responseUsuario(boolean success, String mensaje, int idUsuario, String rol, String tipoUsuario) {
        this.success = success;
        this.mensaje = mensaje;
        this.idUsuario = idUsuario;
        this.rol = rol;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
}
