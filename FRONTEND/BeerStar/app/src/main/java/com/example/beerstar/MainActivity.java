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

        // Habilita el diseño Edge-to-Edge, asegurando que el contenido se extienda hasta los bordes de la pantalla
        EdgeToEdge.enable(this);

        // Establece el layout de la actividad
        setContentView(R.layout.activity_main);

        // Ajusta los márgenes de la vista principal para evitar que el contenido se solape con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            // Obtenemos los márgenes para las barras del sistema (barra de estado y navegación)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Establecemos el padding adecuado para evitar que el contenido se solape con las barras
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración del Spinner (menú desplegable) para seleccionar opciones
        Spinner menuSpinner = findViewById(R.id.menu_spinner);

        // Define las opciones que estarán disponibles en el Spinner
        String[] options = {"Tienda", "Lotes", "Proveedores", "Ayuda y Contacto"};

        // Crea un ArrayAdapter que asocia las opciones con un diseño personalizado
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.spinner_custom_layout,  // Layout personalizado para el spinner
                R.id.spinner_text,              // ID del TextView para mostrar las opciones
                options                          // Opciones que se mostrarán en el spinner
        ) {
            // Personaliza la vista para mostrar un icono junto al texto en la vista del Spinner (cuando no está desplegado)
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                // Se obtiene el ImageView del layout del Spinner
                ImageView icon = view.findViewById(R.id.spinner_icon);
                // Asignamos un ícono común a todas las opciones del spinner
                icon.setImageResource(R.drawable.menu);
                // Se obtiene el TextView y se oculta
                TextView text = view.findViewById(R.id.spinner_text);
                text.setVisibility(View.GONE);  // Oculta el texto cuando el spinner no está desplegado
                return view;
            }

            // Personaliza la vista cuando se despliega el menú (mostrar solo el texto, sin íconos)
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                // Se obtiene el ImageView y se oculta en el desplegable
                ImageView icon = view.findViewById(R.id.spinner_icon);
                icon.setVisibility(View.GONE);
                // Se obtiene el TextView y se muestra
                TextView text = view.findViewById(R.id.spinner_text);
                text.setVisibility(View.VISIBLE);
                return view;
            }
        };

        // Asocia el ArrayAdapter con el Spinner
        menuSpinner.setAdapter(adapter);

        // Configura un Listener para manejar cuando el usuario selecciona una opción en el Spinner
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = options[position];  // Opción seleccionada por el usuario

                // Dependiendo de la opción seleccionada, cargamos el fragmento correspondiente
                if (selectedOption.equals("Proveedores")) {
                    loadFragment(new fragmentProveedores());  // Carga el fragmento de proveedores
                } else if (selectedOption.equals("Tienda")) {
                    loadFragment(new fragmentTienda());  // Carga el fragmento de tienda
                } else if (selectedOption.equals("Lotes")) {
                    loadFragment(new fragmentLotes());  // Carga el fragmento de lotes
                } else if (selectedOption.equals("Ayuda y Contacto")) {
                    loadFragment(new fragmentAyudaContacto());  // Carga el fragmento de ayuda y contacto
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción cuando no se selecciona nada (no es necesario en este caso, pero está aquí por completitud)
            }
        });

        // Configura el botón del carrito para cargar el fragmento de carrito cuando se haga clic
        ImageButton cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(v -> {
            // Carga el fragmento del carrito
            loadFragment(new fragmentCarrito());
        });

        // Configura el botón de usuario para cargar el fragmento de usuario cuando se haga clic
        ImageButton userButton = findViewById(R.id.user_button);
        userButton.setOnClickListener(v -> {
            // Carga el fragmento de usuario
            loadFragment(new fragmentUsuario());
        });
    }

    /**
     * Método para cargar un fragmento en el contenedor de fragmentos.
     * @param fragment El fragmento a cargar
     */
    private void loadFragment(Fragment fragment) {
        // Obtiene el FragmentManager para gestionar los fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Inicia una nueva transacción de fragmento
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // Reemplaza el fragmento actual con el nuevo
        transaction.replace(R.id.fragment_container, fragment);  // R.id.fragment_container es el contenedor de fragmentos
        // Añade la transacción a la pila de retroceso (permite volver al fragmento anterior)
        transaction.addToBackStack(null);
        // Realiza la transacción
        transaction.commit();
    }
}
