package com.example.beerstar.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VoiceflowApi {

    @Headers({
            "Authorization: Bearer VF.DM.68135a020d6a45d0871f6b19.DoyEtiYQZbMJiHWa", // <-- Tu API Key exacta está aquí
            "Content-Type: application/json"
    })
    // Usar version_id en la URL del endpoint interact
    @POST("state/{version_id}/user/{user_id}/interact")
    Call<ResponseBody> sendMessage(
            @Path("version_id") String versionId, // Parámetro de ruta para el Version ID
            @Path("user_id") String userId,       // Parámetro de ruta para el User ID
            @Body VoiceflowRequest body          // Cuerpo de la solicitud con action y config
    );

    // Modelo para el cuerpo de la solicitud a Voiceflow
    class VoiceflowRequest {
        private Action action;
        private Config config;

        public VoiceflowRequest(String message, boolean tts, boolean stripSSML) {
            this.action = new Action("text", message);
            this.config = new Config(tts, stripSSML);
        }

        private static class Action {
            private String type;
            private String payload;

            public Action(String type, String payload) {
                this.type = type;
                this.payload = payload;
            }
        }

        private static class Config {
            private boolean tts;
            private boolean stripSSML;

            public Config(boolean tts, boolean stripSSML) {
                this.tts = tts;
                this.stripSSML = stripSSML;
            }
        }
    }
}