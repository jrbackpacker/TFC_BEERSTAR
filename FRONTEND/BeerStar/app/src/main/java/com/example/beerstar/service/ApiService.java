package com.example.beerstar.service;

import com.example.beerstar.response.responseProveedores;
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.response.responseUsuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("beerstar/articulos/listarArticulos")
    Call<List<responseProductos>> obtenerProductos();

    @POST("/beerstar/usuarios/registro")
    Call<responseUsuario> registrarUsuario(@Body Usuario usuario);

    @POST("/beerstar/usuarios/login")
    Call<responseUsuario> loginUsuario(@Body Usuario usuario);

    @PUT("/beerstar/clientes/")
    Call<responseUsuario> actualizarUsuario(@Body Usuario usuario);

    @GET("/beerstar/usuarios/proveedores/{proveedorId}")
    Call<responseProveedores> getProveedorById(@Path("proveedorId") int proveedorId);
}

