package com.example.vitaplant;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vitaplant.model.Device;
import com.example.vitaplant.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextText; // Campo de nombre de usuario
    private EditText editTextTextEmailAddress; // Campo de correo electrónico
    private EditText editTextTextPassword; // Campo de contraseña
    private DatabaseReference usersRef; // Referencia a la base de datos
    private FirebaseAuth mAuth; // Instancia de FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Obtén referencias a los elementos del layout
        editTextText = findViewById(R.id.editTextText);
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        Button registerButton = findViewById(R.id.btn_register);

        // Obtén una instancia de FirebaseDatabase y la referencia a "users"
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

        // Obtén una instancia de FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // botón de registro
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Obtener datos del usuario
                String username = editTextText.getText().toString();
                String email = editTextTextEmailAddress.getText().toString();
                String password = editTextTextPassword.getText().toString();

                // 2. Validar los datos (opcional pero recomendado)
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return; // Detener el registro si faltan datos
                }

                // 3. Crear un usuario en Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // El usuario se creó correctamente, ahora guarda la información adicional en Realtime Database
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    saveUserData(firebaseUser, username); // Llama al método para guardar los datos del usuario
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish(); // Cerrar RegisterActivity
                                } else {
                                    // Error al crear el usuario
                                    Toast.makeText(RegisterActivity.this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    // Método para guardar los datos del usuario en Realtime Database
    private void saveUserData(FirebaseUser firebaseUser, String username) {
        // Crea un objeto User con los datos del usuario
        String email = firebaseUser.getEmail();
        String uid = firebaseUser.getUid(); // ID único del usuario en Firebase Authentication

        User user = new User(username, email, uid);

        // Agrega el dispositivo al objeto User
        Device device = new Device("Demostracion", 50); // Ejemplo de dispositivo
        user.addDevice(device);

        // Guarda el objeto User en Realtime Database
        usersRef.child(uid).setValue(user);
    }
}