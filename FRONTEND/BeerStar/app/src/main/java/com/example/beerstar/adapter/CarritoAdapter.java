package com.example.beerstar.adapter;

// Importaciones necesarias para vistas, botones, textos y listas
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
import com.example.beerstar.response.responseCarrito;
import com.squareup.picasso.Picasso;

import java.util.List;

// Adaptador personalizado para mostrar elementos del carrito en un RecyclerView
public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    // Lista que contiene los elementos del carrito (pueden ser productos o lotes)
    private List<responseCarrito> carritoItems;

    // Interfaz que permite notificar a otra clase cuando se actualiza el carrito
    private OnCarritoActualizadoListener listener;

    // Variables para almacenar el total de la compra y el cálculo del IVA
    private double totalCompra = 0.0;
    private double iva = 0.0;

    // Constructor del adaptador. Recibe la lista de productos y el listener para actualizar totales
    public CarritoAdapter(List<responseCarrito> carritoItems, OnCarritoActualizadoListener listener) {
        this.carritoItems = carritoItems;
        this.listener = listener;
        calcularTotal(); // Calcula el total e IVA al iniciar
    }

    // Crea el ViewHolder: representa cada fila del RecyclerView (vista individual del carrito)
    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new CarritoViewHolder(view);
    }

    // Asigna los datos de cada elemento del carrito a la vista correspondiente
    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        // Obtiene el producto/lote según la posición en la lista
        responseCarrito item = carritoItems.get(position);

        // Si el elemento es de tipo PRODUCTO, se carga su imagen con Picasso
        if (item.getTipo() == responseCarrito.Tipo.PRODUCTO) {
            Picasso.get().load(item.getImagenUrl()).into(holder.imagenArticulo);
        }

        // Se muestran nombre, descripción, precio total y cantidad
        holder.nombreArticulo.setText(item.getNombre());
        holder.descripcionArticulo.setText(item.getDescripcion());
        holder.precioArticulo.setText("Precio: €" + (item.getPrecio() * item.getCantidad()));
        holder.cantidadArticulo.setText("Cantidad: " + item.getCantidad());

        // Botón para reducir la cantidad del producto
        holder.botonRestar.setOnClickListener(v -> {
            if (item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                notifyItemChanged(position); // Se actualiza visualmente el item
                calcularTotal(); // Se actualizan totales
                if (listener != null) listener.onCarritoActualizado(totalCompra, iva);
            }
        });

        // Botón para aumentar la cantidad del producto
        holder.botonSumar.setOnClickListener(v -> {
            item.setCantidad(item.getCantidad() + 1);
            notifyItemChanged(position);
            calcularTotal();
            if (listener != null) listener.onCarritoActualizado(totalCompra, iva);
        });

        // Botón para eliminar completamente un producto del carrito
        holder.botonEliminar.setOnClickListener(v -> {
            carritoItems.remove(position); // Se elimina el item de la lista
            notifyItemRemoved(position); // Se notifica visualmente al RecyclerView
            notifyItemRangeChanged(position, carritoItems.size()); // Se actualiza el resto de la lista

            // Muestra un mensaje al usuario
            Toast.makeText(v.getContext(), "Eliminado del carrito: " + item.getNombre(), Toast.LENGTH_SHORT).show();

            calcularTotal();
            if (listener != null) listener.onCarritoActualizado(totalCompra, iva);
        });
    }

    // Devuelve la cantidad total de elementos en el carrito
    @Override
    public int getItemCount() {
        return carritoItems.size();
    }

    // Calcula el total de la compra y el IVA (21%)
    private void calcularTotal() {
        totalCompra = 0.0;
        for (responseCarrito item : carritoItems) {
            totalCompra += item.getPrecio() * item.getCantidad(); // Precio total = precio unitario * cantidad
        }
        iva = totalCompra * 0.21; // Cálculo del 21% de IVA
    }

    // Clase interna que representa la vista de cada item del carrito
    public static class CarritoViewHolder extends RecyclerView.ViewHolder {
        // Elementos visuales de la interfaz
        ImageView imagenArticulo;
        TextView nombreArticulo, descripcionArticulo, precioArticulo, cantidadArticulo;
        Button botonRestar, botonSumar, botonEliminar;

        // Constructor que vincula los elementos del XML con las variables de Java
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

    // Interfaz para comunicar al exterior que el carrito fue modificado (actualiza el total e IVA)
    public interface OnCarritoActualizadoListener {
        void onCarritoActualizado(double totalCompra, double iva);
    }
}
