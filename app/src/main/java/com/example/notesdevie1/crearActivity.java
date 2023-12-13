package com.example.notesdevie1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class crearActivity extends AppCompatActivity {
    private EditText txtescriibirnom1;
    private EditText editApellido;
    private EditText editCorreo;
    private EditText editTextTextPassword;
    private Button btnRegistro;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craersesion);

        txtescriibirnom1 = findViewById(R.id.txtescriibirnom1);
        editApellido = findViewById(R.id.editApellido);
        editCorreo = findViewById(R.id.editCorreo);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btnRegistro = findViewById(R.id.btnRegistro);
        firebaseAuth = FirebaseAuth.getInstance();

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ValidarDatos();}
        });


    }
    private void ValidarDatos(){
        String nombre = txtescriibirnom1.getText().toString();
        String apellido = editApellido.getText().toString();
        String correo= editCorreo.getText().toString();
        String contraseña = editTextTextPassword.getText().toString();

        if(TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) ||
        TextUtils.isEmpty(correo) || TextUtils.isEmpty(contraseña)){
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return;
        }
        RegistroUsuario(nombre, apellido, correo, contraseña);

    }

    private void RegistroUsuario(String nombre, String apellido, String correo, String contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // Registro exitoso
                        Toast.makeText(crearActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                        // Si deseas guardar la información del usuario en Firebase Realtime Database:
                        GuardarInformacionEnDatabase(authResult.getUser().getUid(), nombre, apellido, correo, contraseña);

                        // Redirige a la actividad de inicio o realiza cualquier otra acción necesaria
                        // Ejemplo:
                        startActivity(new Intent(crearActivity.this, iniciarsesionActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error en el registro
                        Toast.makeText(crearActivity.this, "Error en el registro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void GuardarInformacionEnDatabase(String uid, String nombre, String apellido, String correo, String contraseña) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarioa");
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("nombre", nombre);
        userData.put("apellido", apellido);
        userData.put("correo", correo);
        userData.put("contraseña", contraseña);

        databaseReference.child(uid).setValue(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Información del usuario guardada exitosamente
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al guardar la información del usuario
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();

    }
}
