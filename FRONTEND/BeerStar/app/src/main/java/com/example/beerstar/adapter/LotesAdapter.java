// Paquete al que pertenece este adaptador
package com.example.beerstar.adapter;

// Importaciones necesarias para vistas y componentes
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.response.responseLotes;
import com.squareup.picasso.Picasso;

import java.util.List;

// Adaptador para mostrar una lista de lotes en un RecyclerView
public class LotesAdapter extends RecyclerView.Adapter<LotesAdapter.LoteViewHolder> {

    // Lista que contiene los lotes que se van a mostrar
    private List<responseLotes> lotesList;

    // Listener que se ejecuta cuando el usuario hace clic en un lote (para añadirlo al carrito, por ejemplo)
    private OnAñadirCarritoClickListener listener;

    // Interfaz para manejar clics sobre los items del RecyclerView
    public interface OnAñadirCarritoClickListener {
        void onAñadirClick(responseLotes lote); // Método que se llama al hacer clic
    }

    // Constructor del adaptador, recibe la lista de lotes y el listener
    public LotesAdapter(List<responseLotes> lotesList, OnAñadirCarritoClickListener listener) {
        this.lotesList = lotesList;
        this.listener = listener;
    }

    // Método que crea las vistas de cada item del RecyclerView (infla item_lote.xml)
    @NonNull
    @Override
    public LoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crea una vista desde el archivo XML de layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lote, parent, false);
        return new LoteViewHolder(view); // Devuelve un nuevo ViewHolder con la vista creada
    }

    // Método que asigna datos a cada elemento visual según su posición
    @Override
    public void onBindViewHolder(@NonNull LoteViewHolder holder, int position) {
        // Obtiene el lote actual de la lista
        responseLotes lote = lotesList.get(position);

        // Muestra los datos del lote en los TextView correspondientes
        holder.nombreTextView.setText(lote.getMarca());
        holder.descripcionTextView.setText(lote.getDescripcion());
        holder.precioTextView.setText("Precio: " + lote.getPrecio() + " €");

        // Carga la imagen del lote usando la librería Picasso
        Picasso.get()
                .load(lote.getImagen_url())               // URL de la imagen
                .placeholder(R.drawable.usuario)          // Imagen mostrada mientras carga
                .error(R.drawable.usuario)                // Imagen si falla la carga
                .into(holder.imagenLote);                 // ImageView donde se mostrará

        // Al hacer clic en el item, se ejecuta el método del listener
        holder.itemView.setOnClickListener(v -> listener.onAñadirClick(lote));
    }

    // Devuelve la cantidad total de lotes en la lista
    @Override
    public int getItemCount() {
        return lotesList.size();
    }

    // Clase interna que representa cada fila individual en el RecyclerView
    public static class LoteViewHolder extends RecyclerView.ViewHolder {

        // Elementos visuales que se mostrarán por cada lote
        TextView nombreTextView, descripcionTextView, precioTextView;
        ImageView imagenLote;

        // Constructor que enlaza los elementos de la interfaz con las variables
        public LoteViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);           // Nombre del lote (marca)
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView); // Descripción del lote
            precioTextView = itemView.findViewById(R.id.precioTextView);           // Precio del lote
            imagenLote = itemView.findViewById(R.id.imagen_lote);                  // Imagen del lote
        }
    }
}
