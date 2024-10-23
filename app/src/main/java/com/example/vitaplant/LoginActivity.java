package com.example.vitaplant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referenciar los campos de usuario (email) y contraseña
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.btn_login);
        Button registerButton = findViewById(R.id.btn_register);

        // Lógica del botón de "Iniciar Sesión"
        //fireBase
        mAuth = FirebaseAuth.getInstance();

        inicializarFirebase();



        loginButton.setOnClickListener(v -> {
            try {
                String email = user.getText().toString();
                String password = pass.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Por favor ingresa un correo electrónico válido", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor ingresa tu contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            } catch (Exception e) {
                // Manejar la excepción
                Log.e("LoginActivity", "Error al iniciar sesión", e);
                Toast.makeText(LoginActivity.this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Lógica del botón de "Registrar"
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    public void onStart(){
        super.onStart();
        FirebaseUser ll = mAuth.getCurrentUser();

    }

    private void loginUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(LoginActivity.this, "Bienvenido",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para verificar si el email es válido
    private boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
