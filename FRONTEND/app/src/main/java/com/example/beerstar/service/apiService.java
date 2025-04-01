package com.example.beerstar.service;

import com.example.beerstar.response.responseUsuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface apiService {

    @POST("/beerstar/usuarios/registro")
    Call<responseUsuario> registrarUsuario(@Body Usuario usuario);

    @POST("/beerstar/usuarios/login")
    Call<responseUsuario> loginUsuario(@Body Usuario usuario);

    @PUT("/beerstar/clientes/")
    Call<responseUsuario> actualizarUsuario(@Body Usuario usuario);
}
