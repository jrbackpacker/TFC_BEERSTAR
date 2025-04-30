package com.example.beerstar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.response.responseLotes;

import java.util.List;

public class LotesAdapter extends RecyclerView.Adapter<LotesAdapter.LoteViewHolder> {

    private List<responseLotes> lotes;
    private OnAddToCartClickListener addToCartClickListener;

    public LotesAdapter(List<responseLotes> lotes, OnAddToCartClickListener addToCartClickListener) {
        this.lotes = lotes;
        this.addToCartClickListener = addToCartClickListener;
    }

    @NonNull
    @Override
    public LoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lote, parent, false);
        return new LoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoteViewHolder holder, int position) {
        responseLotes lote = lotes.get(position);

        // Asignar los datos a las vistas
        holder.nombreLote.setText(lote.getNombre());
        holder.descripcionLote.setText(lote.getDescripcion());
        holder.precioLote.setText("Precio: $" + lote.getPrecio());

        // Configurar el botón para añadir al carrito
        holder.botonAñadir.setOnClickListener(v -> {
            addToCartClickListener.onAddToCartClick(lote);
        });
    }

    @Override
    public int getItemCount() {
        return lotes.size();
    }

    public static class LoteViewHolder extends RecyclerView.ViewHolder {
        TextView nombreLote, descripcionLote, precioLote;
        Button botonAñadir;

        public LoteViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreLote = itemView.findViewById(R.id.nombre_lote);
            descripcionLote = itemView.findViewById(R.id.descripcion_lote);
            precioLote = itemView.findViewById(R.id.precio_lote);
            botonAñadir = itemView.findViewById(R.id.boton_añadir);
        }
    }

    // Interfaz para manejar el clic en el botón de añadir al carrito
    public interface OnAddToCartClickListener {
        void onAddToCartClick(responseLotes lote);
    }
}