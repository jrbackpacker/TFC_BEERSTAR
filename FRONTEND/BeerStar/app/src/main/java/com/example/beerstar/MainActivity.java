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

import com.example.beerstar.fragment.fragmentCarrito;
import com.example.beerstar.fragment.fragmentChat;
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
        String[] options = {"Tienda", "Lotes", "Proveedores", "Ayuda y Contacto"};

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

        menuSpinner.setAdapter(adapter);

        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];

                if (selectedOption.equals("Proveedores")) {
                    loadFragment(new fragmentProveedores());
                } else if (selectedOption.equals("Tienda")) {
                    loadFragment(new fragmentTienda());
                } else if (selectedOption.equals("Lotes")) {
                    loadFragment(new fragmentLotes());
                } else if (selectedOption.equals("Ayuda y Contacto")) {
                    loadFragment(new fragmentChat());  // <- AQUI SE CARGA EL CHATBOT
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ImageButton cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(v -> loadFragment(new fragmentCarrito()));

        ImageButton userButton = findViewById(R.id.user_button);
        userButton.setOnClickListener(v -> loadFragment(new fragmentUsuario()));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
