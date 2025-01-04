package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private MaterialButton loginButton, signupButton;
    private TextView forgotPasswordLabel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.createAccountButton);
        forgotPasswordLabel = findViewById(R.id.forgetpasswordbutton);




        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (validateInput(email, password)) {
                loginUser(email, password);
            }
        });

        signupButton.setOnClickListener(v -> {
            Toast.makeText(LoginActivity.this, "Navigating to Signup", Toast.LENGTH_SHORT).show();
            // Implement signup activity navigation logic here

            Intent intent = new Intent(LoginActivity.this, SignupActivity.class); // Your HomeScreen activity
            startActivity(intent);
            finish();
        });


        forgotPasswordLabel.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        // Navigate to HomeScreen logic here
                        Intent intent = new Intent(LoginActivity.this, HomeScreen.class); // Your HomeScreen activity
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
