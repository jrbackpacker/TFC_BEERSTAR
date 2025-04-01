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

public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ProveedorViewHolder> {

    private List<responseProveedores> proveedores;

    public ProveedorAdapter(List<responseProveedores> proveedores) {
        this.proveedores = proveedores;
    }

    @NonNull
    @Override
    public ProveedorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proveedor, parent, false);
        return new ProveedorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProveedorViewHolder holder, int position) {
        responseProveedores proveedor = proveedores.get(position);

        // Cargar la imagen del logo con Picasso
        Picasso.get()
                .load(proveedor.getLogoUrl())
                .into(holder.logoProveedor);

        // Asignar los datos a las vistas
        holder.nombreProveedor.setText(proveedor.getNombre());
        holder.descripcionProveedor.setText(proveedor.getDescripcion());
        holder.contactoProveedor.setText("Contacto: " + proveedor.getContacto());
    }

    @Override
    public int getItemCount() {
        return proveedores.size();
    }

    public static class ProveedorViewHolder extends RecyclerView.ViewHolder {
        ImageView logoProveedor;
        TextView nombreProveedor;
        TextView descripcionProveedor;
        TextView contactoProveedor;

        public ProveedorViewHolder(@NonNull View itemView) {
            super(itemView);
            logoProveedor = itemView.findViewById(R.id.logo_proveedor);
            nombreProveedor = itemView.findViewById(R.id.nombre_proveedor);
            descripcionProveedor = itemView.findViewById(R.id.descripcion_proveedor);
            contactoProveedor = itemView.findViewById(R.id.contacto_proveedor);
        }
    }
}