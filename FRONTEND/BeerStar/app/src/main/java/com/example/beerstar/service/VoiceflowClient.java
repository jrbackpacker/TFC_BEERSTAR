package com.example.beerstar.service;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoiceflowClient {

    private final VoiceflowApi voiceflowApi;

    public VoiceflowClient() {
        // Obtener la instancia de la API usando el cliente Retrofit dedicado
        this.voiceflowApi = VoiceflowRetrofitClient.getApi();
    }

    /**
     * Sends a message to the Voiceflow API and handles the response.
     *
     * @param message   The user's text message.
     * @param versionId The Voiceflow version ID.
     * @param userId    The user ID (e.g., a session ID).
     * @param callback  The callback to handle success or error responses.
     */
    public void sendMessage(String message, String versionId, String userId, final ResponseCallback callback) {
        // Construir el cuerpo de la solicitud usando el modelo interno VoiceflowApi.VoiceflowRequest.
        // Se desactiva TTS (false) y se activa stripSSML (true) para un chat de texto típico.
        VoiceflowApi.VoiceflowRequest requestBody = new VoiceflowApi.VoiceflowRequest(message, false, true);

        // Log para verificar el JSON que se envía (opcional, útil para depuración)
        // Si usas HttpLoggingInterceptor, esto no es estrictamente necesario, pero ayuda a ver el body exacto creado.
        // String jsonBody = new com.google.gson.Gson().toJson(requestBody);
        // Log.d("VoiceflowClient", "Sending JSON Body: " + jsonBody);


        // Realizar la llamada a la API de forma asíncrona
        voiceflowApi.sendMessage(versionId, userId, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        // Leer la respuesta como String para que fragmentChat la parseé
                        String responseBodyString = response.body().string();
                        Log.d("VoiceflowClient", "Raw Response Body: " + responseBodyString); // Log de la respuesta cruda
                        callback.onSuccess(responseBodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("VoiceflowClient", "Error reading response body", e);
                        callback.onError("Error reading response body: " + e.getMessage());
                    }
                } else {
                    // Leer el cuerpo de error para más detalles del 500 o 401
                    String errorBody = "No error body";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (IOException e) {
                        Log.e("VoiceflowClient", "Error reading error body", e);
                    }
                    String errorMessage = "API Error: " + response.code() + " - " + response.message() + " Body: " + errorBody;
                    Log.e("VoiceflowClient", errorMessage);
                    callback.onError(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // Manejar errores de red (sin conexión, timeout, etc.)
                Log.e("VoiceflowClient", "Network Error: " + t.getMessage(), t);
                callback.onError("Network Error: " + t.getMessage());
            }
        });
    }
}