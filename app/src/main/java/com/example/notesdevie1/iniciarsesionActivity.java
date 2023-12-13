package com.example.notesdevie1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class iniciarsesionActivity extends AppCompatActivity {

    private EditText editCorreo2;
    private EditText editTextTextPassword1;
    private TextView txtRegistrarsee;
    private Button button;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarsesion);

        editCorreo2 = findViewById(R.id.editCorreo2);
        editTextTextPassword1 = findViewById(R.id.editTextTextPassword1);
        firebaseAuth  = FirebaseAuth.getInstance();
        txtRegistrarsee= findViewById(R.id.txtRegistrarsee);

        txtRegistrarsee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciarsesionActivity.this, crearActivity.class));
            }
        });

        button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
    }

    private void iniciarSesion() {
        // Lógica para el inicio de sesión
        String correo2 = editCorreo2.getText().toString();
        String clave = editTextTextPassword1.getText().toString();

        if (correo2.isEmpty() || clave.isEmpty()){
            Toast.makeText(iniciarsesionActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else{
            // Validación de correo electrónico
            if (!Patterns.EMAIL_ADDRESS.matcher(correo2).matches()){
                // Correo electrónico no válido
                Toast.makeText(iniciarsesionActivity.this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
                return;
            }
            // Validación de contraseña
            if (clave.length() < 6) {
                // Contraseña demasiado corta
                Toast.makeText(iniciarsesionActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }
            // Inicia sesión con Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(correo2, clave)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            // Inicio de sesión exitoso
                            Toast.makeText(iniciarsesionActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Redirecciona a la actividad principal o realiza cualquier otra acción necesaria
                            // Ejemplo:
                            startActivity(new Intent(iniciarsesionActivity.this, crearideasActivity.class));
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Error en el inicio de sesión
                            Toast.makeText(iniciarsesionActivity.this, "Error en el inicio de sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
