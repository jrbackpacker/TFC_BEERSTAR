package com.example.beerstar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.R;
import com.example.beerstar.response.responseProveedores;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adaptador para RecyclerView que muestra una lista de proveedores.
 * Cada proveedor contiene nombre, dirección, teléfono y logo.
 */
public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ProveedorViewHolder> {

    // Lista de objetos proveedor que se mostrarán
    private List<responseProveedores> proveedores;

    // Constructor que recibe la lista a mostrar
    public ProveedorAdapter(List<responseProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    /**
     * Infla el layout XML de cada ítem (fila) del RecyclerView.
     * Este método se llama cuando se necesita crear una nueva vista.
     */
    @NonNull
    @Override
    public ProveedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos la vista personalizada del proveedor (item_proveedor.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedor, parent, false);
        return new ProveedorViewHolder(view);
    }

    /**
     * Asocia los datos de un proveedor con los componentes visuales del ítem.
     */
    @Override
    public void onBindViewHolder(@NonNull ProveedorViewHolder holder, int position) {
        // Obtenemos el proveedor correspondiente a la posición
        responseProveedores proveedor = proveedores.get(position);

        // Asignamos los datos al layout del ítem
        holder.nombreProveedor.setText(proveedor.getNombre());
        holder.descripcionProveedor.setText(proveedor.getDireccion());
        holder.contactoProveedor.setText("Tel: " + proveedor.getTelefono());

        // Si hay una URL de imagen, la cargamos usando Picasso
        if (proveedor.getUrl() != null && !proveedor.getUrl().isEmpty()) {
            Picasso.get().load(proveedor.getUrl()).into(holder.logoProveedor);
        }
    }

    /**
     * Devuelve la cantidad total de elementos en la lista.
     */
    @Override
    public int getItemCount() {
        return proveedores.size();
    }

    /**
     * ViewHolder personalizado para mantener las referencias a las vistas
     * de cada ítem del RecyclerView, evitando llamadas innecesarias a findViewById.
     */
    public static class ProveedorViewHolder extends RecyclerView.ViewHolder {
        TextView nombreProveedor;
        TextView descripcionProveedor;
        TextView contactoProveedor;
        ImageView logoProveedor;

        public ProveedorViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asociamos las vistas con los elementos del layout
            nombreProveedor = itemView.findViewById(R.id.nombre_proveedor);
            descripcionProveedor = itemView.findViewById(R.id.descripcion_proveedor);
            contactoProveedor = itemView.findViewById(R.id.contacto_proveedor);
            logoProveedor = itemView.findViewById(R.id.logo_proveedor);
        }
    }
}
