package com.example.beerstar.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.adapter.ProveedorAdapter;
import com.example.beerstar.response.responseProveedores;
import com.example.beerstar.service.ApiService;
import com.example.beerstar.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentProveedores extends Fragment {

    // Declara el RecyclerView, el adaptador y la lista de proveedores
    private RecyclerView recyclerView;
    private ProveedorAdapter proveedorAdapter;
    private List<responseProveedores> proveedores = new ArrayList<>();
    private static final String TAG = "fragmentProveedores"; // Etiqueta para los logs

    // Constructor vacío del fragmento
    public fragmentProveedores() {
    }

    // Método que se llama cuando se crea la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla la vista del fragmento desde el archivo XML
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        // Inicializa el RecyclerView y establece un LinearLayoutManager para mostrar la lista de proveedores
        recyclerView = view.findViewById(R.id.recyclerViewProveedores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crea un adaptador con la lista de proveedores y lo asigna al RecyclerView
        proveedorAdapter = new ProveedorAdapter(proveedores);
        recyclerView.setAdapter(proveedorAdapter);

        // Llama al método para cargar los proveedores desde la API
        cargarProveedores();

        return view;
    }

    // Método para cargar los proveedores desde la API
    private void cargarProveedores() {
        // Obtiene el servicio de la API usando Retrofit
        ApiService api = RetrofitClient.getApiService();

        // Itera sobre un rango de IDs para hacer las peticiones de proveedores
        for (int id = 2; id <= 11; id++) {
            final int finalId = id;
            // Realiza una solicitud para obtener el proveedor por su ID
            api.getProveedorById(id).enqueue(new Callback<responseProveedores>() {
                // Se llama cuando la solicitud tiene éxito
                @Override
                public void onResponse(Call<responseProveedores> call, Response<responseProveedores> response) {
                    // Verifica si la respuesta fue exitosa y contiene datos
                    if (response.isSuccessful() && response.body() != null) {
                        // Añade el proveedor a la lista y actualiza el adaptador
                        proveedores.add(response.body());
                        proveedorAdapter.notifyDataSetChanged();
                    } else {
                        // Si la respuesta no es válida, se registran los detalles del error en los logs
                        Log.e(TAG, "Error en respuesta para ID: " + finalId);
                        Log.e(TAG, "Código: " + response.code());
                    }
                }

                // Se llama si la solicitud falla
                @Override
                public void onFailure(Call<responseProveedores> call, Throwable t) {
                    // Registra el fallo de la petición en los logs
                    Log.e(TAG, "Fallo en la petición para ID: " + finalId, t);
                }
            });
        }
    }
}
