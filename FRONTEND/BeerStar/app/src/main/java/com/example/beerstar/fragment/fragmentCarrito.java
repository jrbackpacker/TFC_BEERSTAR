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
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.response.responseUsuario;

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

        List<responseProductos> productosCarrito = responseProductos.carritoGlobal;

        carritoAdapter = new CarritoAdapter(productosCarrito, this::actualizarTotal);
        recyclerView.setAdapter(carritoAdapter);

        actualizarTotal(); // Inicializa el total

        botonFinalizarCompra.setOnClickListener(v -> {
            responseUsuario usuario = new responseUsuario(true, "Usuario activo", 1, "Cliente", "Regular");
            fragmentDetallePedido detalleFragment = fragmentDetallePedido.newInstance(productosCarrito, usuario);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detalleFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void actualizarTotal() {
        totalCompra = 0;
        for (responseProductos producto : responseProductos.carritoGlobal) {
            totalCompra += producto.getPrecio() * producto.getCantidad();
        }
        textTotalCarrito.setText("Total: €" + totalCompra);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (carritoAdapter != null) {
            carritoAdapter.notifyDataSetChanged();
            actualizarTotal();
        }
    }
}
