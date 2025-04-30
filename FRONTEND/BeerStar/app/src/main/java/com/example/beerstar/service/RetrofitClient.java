package com.example.beerstar.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://java-railway-backend-beerstar-production.up.railway.app"; // La URL base de la API
    private static Retrofit retrofit = null; // Instancia de Retrofit, inicialmente nula

    // Método que obtiene la instancia de ApiService
    public static ApiService getApiService() {
        if (retrofit == null) {
            // Si no se ha inicializado aún, crear el objeto Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Establece la URL base para las solicitudes
                    .addConverterFactory(GsonConverterFactory.create()) // Usar Gson para convertir las respuestas JSON
                    .build(); // Construye la instancia de Retrofit
        }
        // Devuelve la instancia de ApiService usando Retrofit
        return retrofit.create(ApiService.class);
    }
}
