package com.example.beerstar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.beerstar.R;
import com.example.beerstar.response.responseArticulo;
import com.example.beerstar.response.responseUsuario;

import java.util.List;

public class fragmentDetallePedido extends Fragment {

    private TextView textUsuario, textFecha, textArticulos, textTotal, textResumen;

    public static fragmentDetallePedido newInstance(List<responseArticulo> articulos, responseUsuario usuario) {
        fragmentDetallePedido fragment = new fragmentDetallePedido();
        Bundle args = new Bundle();
        args.putSerializable("articulos", (java.io.Serializable) articulos);
        args.putSerializable("usuario", usuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_pedido, container, false);

        // Inicializar los TextViews
        textUsuario = view.findViewById(R.id.textUsuario);
        textFecha = view.findViewById(R.id.textFecha);
        textArticulos = view.findViewById(R.id.textArticulos);
        textTotal = view.findViewById(R.id.textTotal);
        textResumen = view.findViewById(R.id.textResumen);

        // Obtener los datos del Bundle
        List<responseArticulo> articulos = (List<responseArticulo>) getArguments().getSerializable("articulos");
        responseUsuario usuario = (responseUsuario) getArguments().getSerializable("usuario");

        // Mostrar los datos del usuario y el pedido
        textUsuario.setText("Usuario: " + usuario.getMensaje());
        textFecha.setText("Fecha: " + java.text.DateFormat.getDateTimeInstance().format(new java.util.Date()));

        // Mostrar los artículos en formato de lista
        StringBuilder articulosStr = new StringBuilder();
        double total = 0;
        for (responseArticulo articulo : articulos) {
            articulosStr.append(articulo.getNombre())
                    .append("\nCantidad: 1\nPrecio: $")
                    .append(articulo.getPrecio())
                    .append("\n\n");
            total += articulo.getPrecio();
        }

        textArticulos.setText(articulosStr.toString());
        textTotal.setText("Total: $" + total);
        textResumen.setText("Gracias por tu compra, el pedido será procesado y enviado a la brevedad.");

        return view;
    }
}

