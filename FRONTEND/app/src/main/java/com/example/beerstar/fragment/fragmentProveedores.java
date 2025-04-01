package com.example.beerstar.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beerstar.adapter.ProveedorAdapter;
import com.example.beerstar.R;
import com.example.beerstar.response.responseProveedores;

import java.util.ArrayList;
import java.util.List;

public class fragmentProveedores extends Fragment {

    private RecyclerView recyclerView;
    private ProveedorAdapter proveedorAdapter;

    public fragmentProveedores() {
        // Constructor público vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_proveedores, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewProveedores);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear una lista de proveedores simulados
        List<responseProveedores> proveedores = new ArrayList<>();
        proveedores.add(new responseProveedores("https://ejemplo.com/logo1.png", "Proveedor 1", "Descripción del proveedor 1", "+123456789"));
        proveedores.add(new responseProveedores("https://ejemplo.com/logo2.png", "Proveedor 2", "Descripción del proveedor 2", "+987654321"));
        proveedores.add(new responseProveedores("https://ejemplo.com/logo3.png", "Proveedor 3", "Descripción del proveedor 3", "+555555555"));

        // Configurar el adaptador con los datos simulados
        proveedorAdapter = new ProveedorAdapter(proveedores);
        recyclerView.setAdapter(proveedorAdapter);

        return view;
    }
}