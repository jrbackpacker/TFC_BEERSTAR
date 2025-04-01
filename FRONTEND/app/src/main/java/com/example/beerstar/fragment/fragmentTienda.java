package com.example.beerstar.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beerstar.adapter.ArticulosAdapter;
import com.example.beerstar.R;
import com.example.beerstar.response.responseArticulo;

import java.util.ArrayList;
import java.util.List;

public class fragmentTienda extends Fragment {

    private RecyclerView recyclerView;
    private ArticulosAdapter articuloAdapter;

    public fragmentTienda() {
        // Constructor público vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTienda);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear una lista de artículos simulados
        List<responseArticulo> articulos = new ArrayList<>();
        articulos.add(new responseArticulo("https://as1.ftcdn.net/v2/jpg/01/41/29/82/1000_F_141298273_XQo83L2Ru1JZVX6JL7Ax1QwosNO6R4Ko.jpg", "Artículo 1", "Descripción del artículo 1", 10.00));
        articulos.add(new responseArticulo("https://as1.ftcdn.net/v2/jpg/01/41/29/82/1000_F_141298273_XQo83L2Ru1JZVX6JL7Ax1QwosNO6R4Ko.jpg", "Artículo 2", "Descripción del artículo 2", 15.00));
        articulos.add(new responseArticulo("https://as1.ftcdn.net/v2/jpg/01/41/29/82/1000_F_141298273_XQo83L2Ru1JZVX6JL7Ax1QwosNO6R4Ko.jpg", "Artículo 3", "Descripción del artículo 3", 20.00));

        // Configurar el adaptador con los datos simulados
        articuloAdapter = new ArticulosAdapter(articulos);
        recyclerView.setAdapter(articuloAdapter);

        return view;
    }
}