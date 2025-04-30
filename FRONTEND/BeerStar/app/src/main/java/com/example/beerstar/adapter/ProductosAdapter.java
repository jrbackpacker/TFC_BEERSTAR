// Paquete donde se encuentra este adaptador
package com.example.beerstar.adapter;

// Importaciones necesarias para el funcionamiento de vistas, botones e imágenes
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

// Adaptador para mostrar una lista de productos individuales en un RecyclerView
public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder> {

    // Lista de productos que se mostrarán en la tienda
    private List<responseProductos> listaProductos;

    // Callback que se ejecuta cuando se añade un producto al carrito
    private Runnable onProductoAñadido;

    // Constructor del adaptador que recibe la lista de productos y el callback
    public ProductosAdapter(List<responseProductos> lista, Runnable onProductoAñadido) {
        this.listaProductos = lista;
        this.onProductoAñadido = onProductoAñadido;
    }

    // Crea una nueva vista para un producto (infla el layout 'item_tienda.xml')
    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tienda, parent, false);
        return new ProductoViewHolder(itemView);
    }

    // Asocia los datos de un producto con la vista correspondiente
    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        responseProductos producto = listaProductos.get(position);

        // Muestra los datos del producto en los campos correspondientes
        holder.nombre.setText(producto.getNombre());
        holder.descripcion.setText(producto.getDescripcion());
        holder.precio.setText("$" + producto.getPrecio());
        holder.graduacion.setText("Graduación: " + producto.getGraduacion() + "%");
        holder.categoria.setText("Categoría: " + producto.getNombreCategoria());

        // Cargar la imagen del producto usando Picasso
        if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
            Picasso.get().load(producto.getImagen())
                    .placeholder(R.drawable.usuario) // Imagen temporal mientras se carga
                    .error(R.drawable.usuario)       // Imagen si falla la carga
                    .into(holder.imagen);            // Cargar en el ImageView
        } else {
            holder.imagen.setVisibility(View.GONE); // Oculta la imagen si no hay URL válida
        }

        // Configura el botón de "Agregar al carrito"
        holder.btnAgregar.setOnClickListener(v -> {
            // Añade el producto al carrito global si no está ya
            if (!responseProductos.carritoGlobal.contains(producto)) {
                responseProductos.carritoGlobal.add(producto);
            }

            // Ejecuta la acción si se definió (actualizar contador, refrescar UI, etc.)
            if (onProductoAñadido != null) {
                onProductoAñadido.run();
            }

            // Muestra un mensaje emergente al usuario
            Toast.makeText(v.getContext(), "Producto añadido al carrito: " + producto.getNombre(), Toast.LENGTH_SHORT).show();
        });
    }

    // Devuelve el número total de productos en la lista
    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    // ViewHolder: clase que representa cada tarjeta de producto en el RecyclerView
    public static class ProductoViewHolder extends RecyclerView.ViewHolder {

        // Elementos visuales del layout del producto
        TextView nombre, descripcion, precio, graduacion, categoria;
        ImageView imagen;
        Button btnAgregar;

        // Constructor que enlaza los elementos del layout XML con las variables del ViewHolder
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_articulo);
            descripcion = itemView.findViewById(R.id.descripcion_articulo);
            precio = itemView.findViewById(R.id.precio_articulo);
            graduacion = itemView.findViewById(R.id.graduacion_articulo);
            categoria = itemView.findViewById(R.id.categoria_articulo);
            imagen = itemView.findViewById(R.id.imagen_articulo);
            btnAgregar = itemView.findViewById(R.id.boton_agregar_carrito);
        }
    }
}
