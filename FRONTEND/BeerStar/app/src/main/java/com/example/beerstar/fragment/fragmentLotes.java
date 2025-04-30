package com.example.beerstar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.beerstar.R;
import com.example.beerstar.adapter.LotesAdapter;
import com.example.beerstar.service.RetrofitClient;
import com.example.beerstar.service.ApiService;
import com.example.beerstar.response.responseLotes;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentLotes extends Fragment {

    // Declara una referencia al RecyclerView y su adaptador
    private RecyclerView recyclerView;
    private LotesAdapter lotesAdapter;

    // Método que se llama al crear la vista del fragmento
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla la vista del fragmento desde el archivo XML
        View view = inflater.inflate(R.layout.fragment_lotes, container, false);

        // Inicializa el RecyclerView y asigna un layout manager para organizar los ítems en una lista vertical
        recyclerView = view.findViewById(R.id.recyclerViewLotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Llama al método para obtener los lotes desde la API
        obtenerLotesDesdeAPI();

        return view;
    }

    // Método para obtener la lista de lotes desde la API
    private void obtenerLotesDesdeAPI() {
        // Obtiene la instancia del servicio de la API usando Retrofit
        ApiService apiService = RetrofitClient.getApiService();

        // Realiza la solicitud para obtener los lotes desde el servidor
        Call<List<responseLotes>> call = apiService.getLotes();
        call.enqueue(new Callback<List<responseLotes>>() {
            // Método que se ejecuta cuando la solicitud tiene éxito
            @Override
            public void onResponse(Call<List<responseLotes>> call, Response<List<responseLotes>> response) {
                // Verifica que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful() && response.body() != null) {
                    // Asigna el adaptador al RecyclerView con la lista de lotes obtenida
                    lotesAdapter = new LotesAdapter(response.body(), lote -> añadirAlCarrito(lote));
                    recyclerView.setAdapter(lotesAdapter);
                } else {
                    // Muestra un mensaje si la respuesta de la API no es válida
                    Toast.makeText(getContext(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                }
            }

            // Método que se ejecuta si la solicitud falla
            @Override
            public void onFailure(Call<List<responseLotes>> call, Throwable t) {
                // Muestra un mensaje de error si la conexión con la API falla
                Toast.makeText(getContext(), "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para añadir un lote al carrito y mostrar un mensaje
    private void añadirAlCarrito(responseLotes lote) {
        // Muestra un mensaje que indica que el lote fue añadido al carrito
        Toast.makeText(getContext(), lote.getMarca() + " añadido al carrito!", Toast.LENGTH_SHORT).show();
    }
}
