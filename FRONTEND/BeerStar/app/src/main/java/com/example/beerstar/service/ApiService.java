package com.example.beerstar.service;

import com.example.beerstar.response.responseProveedores;
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.response.responseUsuario;
import com.example.beerstar.response.responseLotes;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    /**
     * Obtiene la lista de productos desde el servidor.
     *
     * @return Call<List<responseProductos>> Lista de productos
     */
    @GET("beerstar/articulos/listarArticulos")
    Call<List<responseProductos>> obtenerProductos();

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario El usuario que se quiere registrar
     * @return Call<responseUsuario> Respuesta con los detalles del usuario registrado
     */
    @POST("/beerstar/usuarios/registro")
    Call<responseUsuario> registrarUsuario(@Body Usuario usuario);

    /**
     * Realiza el login de un usuario con las credenciales proporcionadas.
     *
     * @param usuario El usuario con las credenciales de login
     * @return Call<responseUsuario> Respuesta con los detalles del usuario logueado
     */
    @POST("/beerstar/usuarios/login")
    Call<responseUsuario> loginUsuario(@Body Usuario usuario);

    /**
     * Actualiza la información de un usuario en el sistema.
     *
     * @param usuario El usuario con la nueva información
     * @return Call<responseUsuario> Respuesta con los detalles del usuario actualizado
     */
    @PUT("/beerstar/clientes/")
    Call<responseUsuario> actualizarUsuario(@Body Usuario usuario);

    /**
     * Obtiene un proveedor específico por su ID.
     *
     * @param proveedorId El ID del proveedor
     * @return Call<responseProveedores> Respuesta con los detalles del proveedor
     */
    @GET("/beerstar/usuarios/proveedores/{proveedorId}")
    Call<responseProveedores> getProveedorById(@Path("proveedorId") int proveedorId);

    /**
     * Obtiene la lista de lotes desde el servidor.
     *
     * @return Call<List<responseLotes>> Lista de lotes
     */
    @GET("/beerstar/lotes/listarLotes")
    Call<List<responseLotes>> getLotes();
}
