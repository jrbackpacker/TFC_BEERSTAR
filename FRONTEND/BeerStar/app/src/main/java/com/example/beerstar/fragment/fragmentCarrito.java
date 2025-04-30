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

import com.example.beerstar.R;
import com.example.beerstar.adapter.CarritoAdapter;
import com.example.beerstar.response.responseCarrito;
import com.example.beerstar.response.responseProductos;
import com.example.beerstar.response.responseUsuario;

import java.util.ArrayList;
import java.util.List;

// Fragmento que muestra los productos en el carrito
public class fragmentCarrito extends Fragment {

    private RecyclerView recyclerView;
    private CarritoAdapter carritoAdapter;
    private TextView textTotalCarrito;
    private Button botonFinalizarCompra;
    private List<responseCarrito> itemsCarrito = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        // Inicialización de vistas
        recyclerView = view.findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        textTotalCarrito = view.findViewById(R.id.textTotalPrecio);
        botonFinalizarCompra = view.findViewById(R.id.botonFinalizarCompra);

        // Carga los productos del carrito global a esta lista
        for (responseProductos p : responseProductos.carritoGlobal) {
            itemsCarrito.add(new responseCarrito(
                    p.getNombre(),
                    p.getDescripcion(),
                    p.getPrecio(),
                    p.getCantidad(),
                    p.getImagen(),
                    responseCarrito.Tipo.PRODUCTO));
        }

        // Crea el adapter del carrito con un listener que actualiza el total
        carritoAdapter = new CarritoAdapter(itemsCarrito, (total, iva) -> actualizarTotal(total, iva));
        recyclerView.setAdapter(carritoAdapter);

        // Calcula el total inicial
        actualizarTotal();

        // Al pulsar finalizar compra se navega al fragmento detalle
        botonFinalizarCompra.setOnClickListener(v -> {
            responseUsuario usuario = new responseUsuario(true, "Usuario activo", 1, "Cliente", "Regular");
            fragmentDetallePedido detalleFragment = fragmentDetallePedido.newInstance(itemsCarrito, usuario);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detalleFragment)
                    .addToBackStack(null).commit();
        });

        return view;
    }

    // Actualiza el total mostrado con IVA
    private void actualizarTotal(double total, double iva) {
        textTotalCarrito.setText("Total: €" + String.format("%.2f", total) + " (IVA: €" + String.format("%.2f", iva) + ")");
    }

    // Calcula el total desde cero (cuando no se recibe como parámetro)
    private void actualizarTotal() {
        double total = 0;
        for (responseCarrito item : itemsCarrito) {
            total += item.getPrecio() * item.getCantidad();
        }
        double iva = total * 0.21;
        actualizarTotal(total, iva);
    }

    // Se llama al volver al fragmento para actualizar vista y total
    @Override
    public void onResume() {
        super.onResume();
        if (carritoAdapter != null) {
            carritoAdapter.notifyDataSetChanged();
            actualizarTotal();
        }
    }
}
