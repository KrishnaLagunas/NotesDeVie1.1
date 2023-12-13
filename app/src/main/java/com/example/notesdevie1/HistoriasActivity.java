package com.example.notesdevie1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;

public class HistoriasActivity extends AppCompatActivity {

    private EditText etTitulo, etContenido;
    private Button btnGuardar, btnBorrar, btnEditar;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historias);

        etTitulo = findViewById(R.id.etTitulo);
        etContenido = findViewById(R.id.etContenido);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBorrar = findViewById(R.id.btnBorrar);
        btnEditar = findViewById(R.id.btnEditar);

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarHistoria();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // El usuario no está autenticado, manejar según sea necesario
            finish(); // Finalizar actividad si el usuario no está autenticado
        }

        // Obtener la referencia a la base de datos usando la ID del usuario
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios")
                .child(currentUser.getUid())
                .child("historias");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarHistoria();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar la historia actual
                borrarHistoriaActual();
            }
        });
    }

    private void borrarHistoriaActual() {
        // Obtener la clave de la historia actual
        // (En este ejemplo, se supone que la clave está almacenada en alguna variable)
        String claveHistoria = "";

        // Eliminar la historia de la base de datos
        databaseReference.child(claveHistoria).removeValue();

        Toast.makeText(HistoriasActivity.this, "Historia borrada correctamente", Toast.LENGTH_SHORT).show();
    }



    private void editarHistoria() {

    }

    private void guardarHistoria() {
        String titulo = etTitulo.getText().toString().trim();
        String contenido = etContenido.getText().toString().trim();

        if (!titulo.isEmpty() && !contenido.isEmpty()) {
            String claveHistoria = databaseReference.push().getKey();

            HashMap<String, Object> historiaMap = new HashMap<>();
            historiaMap.put("titulo", titulo);
            historiaMap.put("contenido", contenido);

            databaseReference.child(claveHistoria).setValue(historiaMap);

            Toast.makeText(this, "Historia guardada correctamente", Toast.LENGTH_SHORT).show();

            etTitulo.setText("");
            etContenido.setText("");
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
