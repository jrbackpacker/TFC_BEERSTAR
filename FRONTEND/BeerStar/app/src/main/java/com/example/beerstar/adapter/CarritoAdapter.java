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
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<responseProductos> productosCarrito;
    private OnCarritoActualizadoListener listener;

    // Interfaz para notificar cambios en el carrito
    public interface OnCarritoActualizadoListener {
        void onCarritoActualizado();
    }

    // Constructor actualizado que recibe el listener
    public CarritoAdapter(List<responseProductos> productosCarrito, OnCarritoActualizadoListener listener) {
        this.productosCarrito = productosCarrito;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        responseProductos producto = productosCarrito.get(position);

        Picasso.get().load(producto.getImagenUrl()).into(holder.imagenArticulo);

        holder.nombreArticulo.setText(producto.getNombre());
        holder.descripcionArticulo.setText(producto.getDescripcion());
        holder.precioArticulo.setText("Precio: $" + (producto.getPrecio() * producto.getCantidad()));
        holder.cantidadArticulo.setText("Cantidad: " + producto.getCantidad());

        holder.botonRestar.setOnClickListener(v -> {
            if (producto.getCantidad() > 1) {
                producto.setCantidad(producto.getCantidad() - 1);
                notifyItemChanged(position);
                if (listener != null) listener.onCarritoActualizado();
            }
        });

        holder.botonSumar.setOnClickListener(v -> {
            producto.setCantidad(producto.getCantidad() + 1);
            notifyItemChanged(position);
            if (listener != null) listener.onCarritoActualizado();
        });

        holder.botonEliminar.setOnClickListener(v -> {
            productosCarrito.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, productosCarrito.size());
            Toast.makeText(v.getContext(), "Eliminado del carrito: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
            if (listener != null) listener.onCarritoActualizado();
        });
    }

    @Override
    public int getItemCount() {
        return productosCarrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenArticulo;
        TextView nombreArticulo, descripcionArticulo, precioArticulo, cantidadArticulo;
        Button botonRestar, botonSumar, botonEliminar;

        public CarritoViewHolder(View itemView) {
            super(itemView);
            imagenArticulo = itemView.findViewById(R.id.imagen_articulo);
            nombreArticulo = itemView.findViewById(R.id.nombre_articulo);
            descripcionArticulo = itemView.findViewById(R.id.descripcion_articulo);
            precioArticulo = itemView.findViewById(R.id.precio_articulo);
            cantidadArticulo = itemView.findViewById(R.id.cantidad_articulo);
            botonRestar = itemView.findViewById(R.id.btnRestar);
            botonSumar = itemView.findViewById(R.id.btnSumar);
            botonEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
