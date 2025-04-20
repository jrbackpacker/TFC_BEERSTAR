package com.example.beerstar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.beerstar.R;
import com.example.beerstar.service.RetrofitClient;
import com.example.beerstar.service.ApiService;
import com.example.beerstar.service.Usuario;
import com.example.beerstar.response.responseUsuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentUsuario extends Fragment {

    private static final String TAG = "fragmentUsuario";
    private EditText editTextEmail, editTextPassword;
    private Button botonLogin, botonRegistro;
    private ApiService apiService;

    public fragmentUsuario() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        botonLogin = view.findViewById(R.id.botonLogin);
        botonRegistro = view.findViewById(R.id.botonRegistro);
        apiService = RetrofitClient.getApiService(); // ✅ aquí está bien

        botonLogin.setOnClickListener(v -> iniciarSesion());
        botonRegistro.setOnClickListener(v -> registrarUsuario());

        return view;
    }

    private void iniciarSesion() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete email y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(email, password);
        Call<responseUsuario> call = apiService.loginUsuario(usuario);
        call.enqueue(new Callback<responseUsuario>() {
            @Override
            public void onResponse(Call<responseUsuario> call, Response<responseUsuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error en la autenticación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<responseUsuario> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarUsuario() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete email y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario(email, password);
        Call<responseUsuario> call = apiService.registrarUsuario(nuevoUsuario);
        call.enqueue(new Callback<responseUsuario>() {
            @Override
            public void onResponse(Call<responseUsuario> call, Response<responseUsuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<responseUsuario> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
