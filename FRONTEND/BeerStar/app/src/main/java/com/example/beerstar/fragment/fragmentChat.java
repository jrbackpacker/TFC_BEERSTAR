package com.example.beerstar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.chat.ChatAdapter;
import com.example.beerstar.chat.ChatMessage;
import com.example.beerstar.service.ResponseCallback;
import com.example.beerstar.service.VoiceflowClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class fragmentChat extends Fragment {

    private RecyclerView recyclerView;
    private EditText userInput;
    private Button sendButton;
    private ChatAdapter chatAdapter;

    // <-- ¡Cambiado para usar el alias "production"! -->
    private static final String VOICEFLOW_VERSION_ID = "6813334e87e329c87bbb68d4"; // Usar el alias "production"
    // Si quisieras usar la versión de desarrollo, sería:
    // private static final String VOICEFLOW_VERSION_ID = "development";

    private final String sessionId = UUID.randomUUID().toString(); // Usar como User ID
    // La API Key NO va aquí, va en el encabezado de VoiceflowApi.java

    private VoiceflowClient voiceflowClient; // Declarar la instancia del cliente

    public fragmentChat() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.chatRecycler);
        userInput = view.findViewById(R.id.userInput);
        sendButton = view.findViewById(R.id.sendButton);

        chatAdapter = new ChatAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        // Inicializar el VoiceflowClient una vez
        voiceflowClient = new VoiceflowClient();

        sendButton.setOnClickListener(v -> {
            String message = userInput.getText().toString();
            if (!message.trim().isEmpty()) {
                sendUserMessage(message);
                userInput.setText("");
            }
        });

        return view;
    }

    private void sendUserMessage(String message) {
        // Añadir el mensaje del usuario al chat
        chatAdapter.addMessage(new ChatMessage(message, true));

        // Usamos la instancia del VoiceflowClient para enviar el mensaje
        // Pasamos el mensaje, el VersionId (ahora "production"), el userId (sessionId) y el callback
        voiceflowClient.sendMessage(message, VOICEFLOW_VERSION_ID, sessionId, new ResponseCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d("Voiceflow", "Respuesta JSON: " + response); // Loggear la respuesta completa
                try {
                    // Parsear la respuesta JSON (asumiendo que es un JSONArray)
                    JSONArray responseArray = new JSONArray(response);
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject obj = responseArray.getJSONObject(i);
                        // Verificar si el objeto es de tipo "text"
                        if (obj.getString("type").equals("text")) {
                            // Obtener el texto del payload
                            // !!! REVISA ESTO BASADO EN EL LOG DE LA RESPUESTA CRUDA !!!
                            // String reply = obj.getJSONObject("payload").getString("message"); // Voiceflow 3.0+ structure
                            String reply = obj.getString("payload"); // Versiones antiguas o payload directo

                            requireActivity().runOnUiThread(() -> {
                                chatAdapter.addMessage(new ChatMessage(reply, false));
                                // Desplazar automáticamente hacia el último mensaje
                                recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                            });
                        }
                        // Puedes añadir lógica para otros tipos de respuesta (imagen, botón, etc.)
                        // else if (obj.getString("type").equals("image")) { ... }
                        // else if (obj.getString("type").equals("button")) { ... }

                    }
                } catch (JSONException e) {
                    Log.e("Voiceflow", "Error parsing JSON response", e);
                    // Mostrar un mensaje de error en el chat o log
                    requireActivity().runOnUiThread(() -> {
                        chatAdapter.addMessage(new ChatMessage("Error processing bot response.", false));
                        recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                    });
                }
            }

            @Override
            public void onError(String error) {
                Log.e("Voiceflow", "Error sending message: " + error);
                // Mostrar un mensaje de error en el chat
                requireActivity().runOnUiThread(() -> {
                    chatAdapter.addMessage(new ChatMessage("Bot error: " + error, false));
                    recyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                });
            }
        });
    }
}