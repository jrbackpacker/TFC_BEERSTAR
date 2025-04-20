package com.example.beerstar;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.beerstar.fragment.fragmentAyudaContacto;
import com.example.beerstar.fragment.fragmentCarrito;
import com.example.beerstar.fragment.fragmentLotes;
import com.example.beerstar.fragment.fragmentProveedores;
import com.example.beerstar.fragment.fragmentTienda;
import com.example.beerstar.fragment.fragmentUsuario;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Spinner menuSpinner = findViewById(R.id.menu_spinner);

        // Define las opciones del menú
        String[] options = {"Tienda", "Lotes", "Proveedores", "Ayuda y Contacto"};

        // Crea un ArrayAdapter usando el array de opciones y un layout personalizado
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_custom_layout,
                R.id.spinner_text,
                options
        ) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ImageView icon = view.findViewById(R.id.spinner_icon);
                icon.setImageResource(R.drawable.menu);
                TextView text = view.findViewById(R.id.spinner_text);
                text.setVisibility(View.GONE);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ImageView icon = view.findViewById(R.id.spinner_icon);
                icon.setVisibility(View.GONE);
                TextView text = view.findViewById(R.id.spinner_text);
                text.setVisibility(View.VISIBLE);
                return view;
            }
        };

        // Aplica el adapter al Spinner
        menuSpinner.setAdapter(adapter);

        // Maneja la selección de opciones
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];

                // Si la opción seleccionada es "Proveedores", carga el fragmento de proveedores
                if (selectedOption.equals("Proveedores")) {
                    loadFragment(new fragmentProveedores());
                }
                // Si la opción seleccionada es "Tienda", carga el fragmento de la tienda
                else if (selectedOption.equals("Tienda")) {
                    loadFragment(new fragmentTienda());
                }
                // Si la opción seleccionada es "Lotes", carga el fragmento de lotes
                else if (selectedOption.equals("Lotes")) {
                    loadFragment(new fragmentLotes());
                }
                // Si la opción seleccionada es "Ayuda y Contacto", carga el fragmento de ayuda y contacto
                else if (selectedOption.equals("Ayuda y Contacto")) {
                    loadFragment(new fragmentAyudaContacto());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona nada
            }
        });

        // Obtén referencia al botón del carrito
        ImageButton cartButton = findViewById(R.id.cart_button);

        // Configurar el clic en el botón del carrito
        cartButton.setOnClickListener(v -> {
            // Cargar el fragmento del carrito
            loadFragment(new fragmentCarrito());
        });

        // Obtén referencia al botón de usuario
        ImageButton userButton = findViewById(R.id.user_button);

        // Configurar el clic en el botón de usuario
        userButton.setOnClickListener(v -> {
            // Cargar el fragmento de usuario
            loadFragment(new fragmentUsuario());
        });
    }

    // Método para cargar un fragmento
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Reemplaza el contenido del contenedor
        transaction.addToBackStack(null); // Opcional: permite volver al fragmento anterior
        transaction.commit();
    }
}