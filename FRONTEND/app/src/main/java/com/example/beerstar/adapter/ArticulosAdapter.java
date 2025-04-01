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

public class ArticulosAdapter extends RecyclerView.Adapter<ArticulosAdapter.ArticuloViewHolder> {

    private List<responseArticulo> responseArticulos;

    public ArticulosAdapter(List<responseArticulo> articulos) {
        this.responseArticulos = articulos;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tienda, parent, false);
        return new ArticuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        responseArticulo articulo = responseArticulos.get(position);

        // Cargar la imagen del artículo con Picasso
        Picasso.get()
                .load(articulo.getImagenUrl())
                .into(holder.imagenArticulo);

        // Asignar los datos a las vistas
        holder.nombreArticulo.setText(articulo.getNombre());
        holder.descripcionArticulo.setText(articulo.getDescripcion());
        holder.precioArticulo.setText("Precio: $" + articulo.getPrecio());

        // Configurar el botón para agregar al carrito
        holder.botonAgregarCarrito.setOnClickListener(v -> {
            // Aquí puedes agregar la lógica para agregar el artículo al carrito
            // Por ejemplo, mostrar un mensaje o actualizar una lista de artículos seleccionados
            String mensaje = "Agregado al carrito: " + articulo.getNombre();
            Toast.makeText(v.getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return responseArticulos.size();
    }

    public static class ArticuloViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenArticulo;
        TextView nombreArticulo;
        TextView descripcionArticulo;
        TextView precioArticulo;
        Button botonAgregarCarrito;

        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenArticulo = itemView.findViewById(R.id.imagen_articulo);
            nombreArticulo = itemView.findViewById(R.id.nombre_articulo);
            descripcionArticulo = itemView.findViewById(R.id.descripcion_articulo);
            precioArticulo = itemView.findViewById(R.id.precio_articulo);
            botonAgregarCarrito = itemView.findViewById(R.id.boton_agregar_carrito);
        }
    }
}