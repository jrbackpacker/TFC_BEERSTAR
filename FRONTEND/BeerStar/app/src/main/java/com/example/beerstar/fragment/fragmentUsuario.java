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

    // Definición de las variables necesarias
    private static final String TAG = "fragmentUsuario";  // Etiqueta para el log
    private EditText editTextEmail, editTextPassword;     // Campos de texto para email y contraseña
    private Button botonLogin, botonRegistro;             // Botones para login y registro
    private ApiService apiService;                        // Servicio de API para las llamadas

    public fragmentUsuario() {
        // Constructor vacío requerido por el sistema de fragmentos
    }

    // Método que se llama al crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);

        // Inicialización de los componentes de la vista
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        botonLogin = view.findViewById(R.id.botonLogin);
        botonRegistro = view.findViewById(R.id.botonRegistro);

        // Obtener una instancia del servicio API
        apiService = RetrofitClient.getApiService(); // ✅ aquí está bien

        // Configuración de los listeners para los botones
        botonLogin.setOnClickListener(v -> iniciarSesion());  // Método para iniciar sesión
        botonRegistro.setOnClickListener(v -> registrarUsuario());  // Método para registrar usuario

        return view;  // Retorna la vista inflada
    }

    // Método para iniciar sesión
    private void iniciarSesion() {
        // Obtener los valores introducidos en los campos de texto
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validar si los campos están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete email y contraseña", Toast.LENGTH_SHORT).show();
            return; // Si están vacíos, no continuar
        }

        // Crear un objeto Usuario con los datos introducidos
        Usuario usuario = new Usuario(email, password);
        // Realizar la llamada para iniciar sesión
        Call<responseUsuario> call = apiService.loginUsuario(usuario);
        call.enqueue(new Callback<responseUsuario>() {
            @Override
            public void onResponse(Call<responseUsuario> call, Response<responseUsuario> response) {
                // Si la respuesta es exitosa y el cuerpo no es nulo
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    // Si hay un error en la autenticación
                    Toast.makeText(getContext(), "Error en la autenticación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<responseUsuario> call, Throwable t) {
                // Si falla la conexión con el servidor
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para registrar un nuevo usuario
    private void registrarUsuario() {
        // Obtener los valores introducidos en los campos de texto
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validar si los campos están vacíos
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete email y contraseña", Toast.LENGTH_SHORT).show();
            return; // Si están vacíos, no continuar
        }

        // Crear un objeto Usuario con los datos introducidos
        Usuario nuevoUsuario = new Usuario(email, password);
        // Realizar la llamada para registrar el usuario
        Call<responseUsuario> call = apiService.registrarUsuario(nuevoUsuario);
        call.enqueue(new Callback<responseUsuario>() {
            @Override
            public void onResponse(Call<responseUsuario> call, Response<responseUsuario> response) {
                // Si la respuesta es exitosa y el cuerpo no es nulo
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    // Si hay un error en el registro
                    Toast.makeText(getContext(), "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<responseUsuario> call, Throwable t) {
                // Si falla la conexión con el servidor
                Toast.makeText(getContext(), "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
