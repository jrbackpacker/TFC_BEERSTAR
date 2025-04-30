package com.example.beerstar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beerstar.adapter.LotesAdapter;
import com.example.beerstar.R;
import com.example.beerstar.response.responseLotes;

import java.util.ArrayList;
import java.util.List;

public class fragmentLotes extends Fragment {

    private RecyclerView recyclerView;
    private LotesAdapter lotesAdapter;

    public fragmentLotes() {
        // Constructor público vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_lotes, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewLotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear una lista de lotes (datos de ejemplo)
        List<responseLotes> lotes = new ArrayList<>();
        lotes.add(new responseLotes("Lote 1", "Descripción del lote 1", 100.00));
        lotes.add(new responseLotes("Lote 2", "Descripción del lote 2", 150.00));

        // Configurar el adaptador
        lotesAdapter = new LotesAdapter(lotes, this::añadirAlCarrito);
        recyclerView.setAdapter(lotesAdapter);

        return view;
    }


    private void añadirAlCarrito(responseLotes lote) {
        // Aquí puedes agregar la lógica para añadir el lote al carrito
        Toast.makeText(getContext(), "Añadido al carrito: " + lote.getNombre(), Toast.LENGTH_SHORT).show();
    }
}