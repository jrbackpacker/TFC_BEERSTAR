package com.example.beerstar.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoiceflowRetrofitClient {

    private static final String BASE_URL = "https://general-runtime.voiceflow.com";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static VoiceflowApi getApi() {
        return getInstance().create(VoiceflowApi.class);
    }
}
