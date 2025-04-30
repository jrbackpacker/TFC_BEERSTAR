package com.example.beerstar.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.adapter.ProductosAdapter;
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragmentTienda extends Fragment {

    private RecyclerView recyclerView;
    private ProductosAdapter productosAdapter;
    private List<responseProductos> listaProductos = new ArrayList<>();
    private List<responseProductos> listaProductosFiltrados = new ArrayList<>();
    private Spinner spinnerFiltro;
    private EditText edtFiltroValor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTienda);
        spinnerFiltro = view.findViewById(R.id.spinnerFiltro);
        edtFiltroValor = view.findViewById(R.id.edtFiltroValor);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productosAdapter = new ProductosAdapter(listaProductosFiltrados, () -> {});
        recyclerView.setAdapter(productosAdapter);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.filtros, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);

        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aplicarFiltro();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        edtFiltroValor.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                aplicarFiltro();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        obtenerProductos();

        return view;
    }

    private void obtenerProductos() {
        RetrofitClient.getApiService().obtenerProductos().enqueue(new Callback<List<responseProductos>>() {
            @Override
            public void onResponse(@NonNull Call<List<responseProductos>> call, @NonNull Response<List<responseProductos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaProductos.clear();
                    listaProductos.addAll(response.body());
                    aplicarFiltro(); // Se aplica el filtro con la lista completa
                } else {
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<responseProductos>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aplicarFiltro() {
        String valorFiltro = edtFiltroValor.getText().toString().trim().toLowerCase();
        String tipoFiltro = spinnerFiltro.getSelectedItem().toString().toLowerCase();

        listaProductosFiltrados.clear();

        for (responseProductos producto : listaProductos) {
            boolean coincide = false;

            if (tipoFiltro.equals("nombre")) {
                coincide = producto.getNombre().toLowerCase().contains(valorFiltro);
            } else if (tipoFiltro.equals("precio")) {
                try {
                    double precio = Double.parseDouble(valorFiltro);
                    coincide = producto.getPrecio() == precio;
                } catch (NumberFormatException e) {
                    // Ignorar entrada no numérica
                }
            }

            if (coincide) {
                listaProductosFiltrados.add(producto);
            }
        }

        productosAdapter.notifyDataSetChanged();
    }
}



