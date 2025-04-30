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

    // Declaración de variables necesarias para el RecyclerView, adaptador y filtros
    private RecyclerView recyclerView;
    private ProductosAdapter productosAdapter;
    private List<responseProductos> listaProductos = new ArrayList<>();
    private List<responseProductos> listaProductosFiltrados = new ArrayList<>();
    private Spinner spinnerFiltro;
    private EditText edtFiltroValor;

    // Método que se llama cuando se crea la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Infla la vista del fragmento desde el archivo XML
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        // Inicialización de las vistas necesarias: RecyclerView, Spinner y EditText
        recyclerView = view.findViewById(R.id.recyclerViewTienda);
        spinnerFiltro = view.findViewById(R.id.spinnerFiltro);
        edtFiltroValor = view.findViewById(R.id.edtFiltroValor);

        // Configuración del RecyclerView para usar un LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialización del adaptador y asignación al RecyclerView
        productosAdapter = new ProductosAdapter(listaProductosFiltrados, () -> {});
        recyclerView.setAdapter(productosAdapter);

        // Creación y configuración del adaptador para el Spinner (filtros)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.filtros, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);

        // Establece el listener para el Spinner para que aplique el filtro cuando se seleccione una opción
        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aplicarFiltro(); // Aplica el filtro cada vez que se cambia la selección
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Establece un TextWatcher para el EditText y aplica el filtro al cambiar el texto
        edtFiltroValor.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                aplicarFiltro(); // Aplica el filtro con cada cambio en el texto
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        // Llama al método para obtener los productos desde la API
        obtenerProductos();

        return view;
    }

    // Método para obtener los productos desde la API
    private void obtenerProductos() {
        // Realiza una llamada al servicio API para obtener la lista de productos
        RetrofitClient.getApiService().obtenerProductos().enqueue(new Callback<List<responseProductos>>() {
            // Se llama cuando la respuesta es exitosa
            @Override
            public void onResponse(@NonNull Call<List<responseProductos>> call, @NonNull Response<List<responseProductos>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Limpiar la lista existente y agregar los nuevos productos
                    listaProductos.clear();
                    listaProductos.addAll(response.body());
                    aplicarFiltro(); // Aplica el filtro con la lista completa
                } else {
                    // Si ocurre un error en la respuesta, muestra un mensaje de error
                    Toast.makeText(getContext(), "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            // Se llama si la solicitud falla (por ejemplo, por problemas de red)
            @Override
            public void onFailure(@NonNull Call<List<responseProductos>> call, @NonNull Throwable t) {
                // Muestra un mensaje de error si falla la conexión
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para aplicar el filtro a la lista de productos
    private void aplicarFiltro() {
        // Obtiene el texto de filtro y lo convierte a minúsculas para comparación
        String valorFiltro = edtFiltroValor.getText().toString().trim().toLowerCase();
        // Obtiene el tipo de filtro seleccionado en el Spinner
        String tipoFiltro = spinnerFiltro.getSelectedItem().toString().toLowerCase();

        // Limpia la lista de productos filtrados antes de aplicar el nuevo filtro
        listaProductosFiltrados.clear();

        // Recorre todos los productos y aplica el filtro
        for (responseProductos producto : listaProductos) {
            boolean coincide = false;

            // Si el filtro seleccionado es "nombre", se filtra por nombre del producto
            if (tipoFiltro.equals("nombre")) {
                coincide = producto.getNombre().toLowerCase().contains(valorFiltro);
            }
            // Si el filtro seleccionado es "precio", se filtra por precio
            else if (tipoFiltro.equals("precio")) {
                try {
                    double precio = Double.parseDouble(valorFiltro);
                    coincide = producto.getPrecio() == precio;
                } catch (NumberFormatException e) {
                    // Si el filtro de precio no es un número válido, no se aplica el filtro
                }
            }

            // Si el producto coincide con el filtro, se añade a la lista filtrada
            if (coincide) {
                listaProductosFiltrados.add(producto);
            }
        }

        // Notifica al adaptador que la lista filtrada ha cambiado
        productosAdapter.notifyDataSetChanged();
    }
}
