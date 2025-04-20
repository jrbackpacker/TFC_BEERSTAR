package com.example.beerstar.response;

import java.io.Serializable;

public class responseUsuario implements Serializable {

    private boolean success;
    private String mensaje;
    private int idUsuario;
    private String rol;
    private String tipoUsuario;

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
