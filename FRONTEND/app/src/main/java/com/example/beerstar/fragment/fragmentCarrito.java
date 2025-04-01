package com.example.beerstar.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.beerstar.adapter.CarritoAdapter;
import com.example.beerstar.R;
import com.example.beerstar.response.responseArticulo;
import com.example.beerstar.response.responseUsuario;

import java.util.ArrayList;
import java.util.List;

public class fragmentCarrito extends Fragment {

    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private TextView textTotalCarrito;
    private Button botonFinalizarCompra;
    private double totalCompra;

    public fragmentCarrito() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        textTotalCarrito = view.findViewById(R.id.textTotalPrecio);
        botonFinalizarCompra = view.findViewById(R.id.botonFinalizarCompra);

        // Lista de artículos de ejemplo
        List<responseArticulo> articulosCarrito = new ArrayList<>();
        articulosCarrito.add(new responseArticulo("https://via.placeholder.com/150", "Artículo 1", "Descripción del artículo 1", 10.00));
        articulosCarrito.add(new responseArticulo("https://via.placeholder.com/150", "Artículo 2", "Descripción del artículo 2", 15.00));

        // Calcular el total de la compra
        totalCompra = 0;
        for (responseArticulo articulo : articulosCarrito) {
            totalCompra += articulo.getPrecio();
        }

        // Mostrar el total en el TextView
        textTotalCarrito.setText("Total: $" + totalCompra);

        // Configurar el adaptador para el RecyclerView
        carritoAdapter = new CarritoAdapter(articulosCarrito);
        recyclerView.setAdapter(carritoAdapter);

        // Configurar el botón de finalizar compra
        botonFinalizarCompra.setOnClickListener(v -> {
            // Crear el fragmento de detalle del pedido y pasarle los artículos y los datos del usuario
            responseUsuario usuario = new responseUsuario(true, "Usuario activo", 1, "Cliente", "Regular"); // Ejemplo de usuario
            fragmentDetallePedido detalleFragment = fragmentDetallePedido.newInstance(articulosCarrito, usuario);

            // Reemplazar el fragmento actual con el fragmento de detalle del pedido
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detalleFragment) // Reemplazar el contenedor con el nuevo fragmento
                    .addToBackStack(null)  // Añadir al back stack para que el usuario pueda volver
                    .commit();
        });

        return view;
    }
}
