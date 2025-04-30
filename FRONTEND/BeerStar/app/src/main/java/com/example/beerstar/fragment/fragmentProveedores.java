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

    private RecyclerView recyclerView;
    private ProveedorAdapter proveedorAdapter;
    private List<responseProveedores> proveedores = new ArrayList<>();
    private static final String TAG = "fragmentProveedores";

    public fragmentProveedores() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewProveedores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        proveedorAdapter = new ProveedorAdapter(proveedores);
        recyclerView.setAdapter(proveedorAdapter);

        cargarProveedores();

        return view;
    }

    private void cargarProveedores() {
        ApiService api = RetrofitClient.getApiService();

        for (int id = 1; id <= 4; id++) {
            Call<responseProveedores> call = api.getProveedorById(id);

            int finalId = id; // necesario para usar dentro del callback

            call.enqueue(new Callback<responseProveedores>() {
                @Override
                public void onResponse(Call<responseProveedores> call, Response<responseProveedores> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        proveedores.add(response.body());
                        proveedorAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "Error en respuesta para ID: " + finalId);
                        Log.e(TAG, "Código de respuesta: " + response.code());
                        try {
                            if (response.errorBody() != null) {
                                Log.e(TAG, "Mensaje de error: " + response.errorBody().string());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error al leer el errorBody", e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<responseProveedores> call, Throwable t) {
                    Log.e(TAG, "Fallo en la petición para ID: " + finalId, t);
                }
            });
        }
    }

}


