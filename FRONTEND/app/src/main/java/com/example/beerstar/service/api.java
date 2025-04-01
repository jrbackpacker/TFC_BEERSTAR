package com.example.beerstar.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class api {

    private static final String BASE_URL = "https://java-railway-backend-beerstar-production.up.railway.app";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
