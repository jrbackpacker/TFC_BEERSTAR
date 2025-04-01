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
import com.example.beerstar.response.responseArticulo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<responseArticulo> articulosCarrito;
    private OnCarritoActualizadoListener listener;

    public interface OnCarritoActualizadoListener {
        void onCarritoActualizado();
    }

    public CarritoAdapter(List<responseArticulo> articulosCarrito) {
        this.articulosCarrito = articulosCarrito;
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
        responseArticulo articulo = articulosCarrito.get(position);

        Picasso.get().load(articulo.getImagenUrl()).into(holder.imagenArticulo);

        holder.nombreArticulo.setText(articulo.getNombre());
        holder.descripcionArticulo.setText(articulo.getDescripcion());
        holder.precioArticulo.setText("Precio: $" + articulo.getPrecio());

        holder.botonEliminar.setOnClickListener(v -> {
            articulosCarrito.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, articulosCarrito.size());
            Toast.makeText(v.getContext(), "Eliminado del carrito: " + articulo.getNombre(), Toast.LENGTH_SHORT).show();
            listener.onCarritoActualizado(); // Notifica al fragmento
        });
    }

    @Override
    public int getItemCount() {
        return articulosCarrito.size();
    }

    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenArticulo;
        TextView nombreArticulo;
        TextView descripcionArticulo;
        TextView precioArticulo;
        Button botonEliminar;

        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenArticulo = itemView.findViewById(R.id.imagen_articulo);
            nombreArticulo = itemView.findViewById(R.id.nombre_articulo);
            descripcionArticulo = itemView.findViewById(R.id.descripcion_articulo);
            precioArticulo = itemView.findViewById(R.id.precio_articulo);
            botonEliminar = itemView.findViewById(R.id.boton_eliminar);
        }
    }
}
