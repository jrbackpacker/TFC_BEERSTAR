package com.example.beerstar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.response.responseProductos;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

    private List<responseProductos> listaProductos;
    private Runnable onProductoAñadido;  // <- para avisar cuando se añade un producto

    // 👇 Constructor nuevo con 2 parámetros
    public ProductosAdapter(List<responseProductos> lista, Runnable onProductoAñadido) {
        this.listaProductos = lista;
        this.onProductoAñadido = onProductoAñadido;
    }

    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tienda, parent, false);
        return new ProductoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        responseProductos producto = listaProductos.get(position);
        holder.nombre.setText(producto.getNombre());
        holder.descripcion.setText(producto.getDescripcion());
        holder.precio.setText("$" + producto.getPrecio());

        holder.btnAgregar.setOnClickListener(v -> {
            if (!responseProductos.carritoGlobal.contains(producto)) {
                responseProductos.carritoGlobal.add(producto);
            }

            if (onProductoAñadido != null) {
                onProductoAñadido.run();
            }
            Toast.makeText(v.getContext(), "Producto añadido al carrito: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, precio;
        ImageView imagen;
        Button btnAgregar;

        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_articulo);
            descripcion = itemView.findViewById(R.id.descripcion_articulo);
            precio = itemView.findViewById(R.id.precio_articulo);
            imagen = itemView.findViewById(R.id.imagen_articulo); // aunque esté en GONE
            btnAgregar = itemView.findViewById(R.id.boton_agregar_carrito);
        }
    }
}
