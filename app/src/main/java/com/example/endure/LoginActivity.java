package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private MaterialButton loginButton, signupButton;
    private FirebaseAuth mAuth; // FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Initialize fields and buttons
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.createAccountButton);

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Validate the email and password
            if (validateInput(email, password)) {
                // Proceed with login logic
                loginUser(email, password);
            }
        });

        // Handle signup button click (navigate to SignupActivity)
        signupButton.setOnClickListener(v -> {
            // Navigate to signup activity if the user does not have an account yet
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Navigating to Signup", Toast.LENGTH_SHORT).show();
        });
    }

    // Validate email and password
    private boolean validateInput(String email, String password) {
        // Validate email format
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate password (at least 8 characters, including uppercase, lowercase, and special character)
        if (TextUtils.isEmpty(password) || password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[!@#\\$%\\^&\\*].*")) {
            Toast.makeText(this, "Password must be at least 8 characters, including uppercase, lowercase, and a special character", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Firebase login method
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful, navigate to HomeScreen
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                            startActivity(intent);
                            finish(); // Close LoginActivity
                        }
                    } else {
                        // If login fails, show a message to the user
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
