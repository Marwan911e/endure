package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameField, emailField, passwordField;
    private MaterialButton signupButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize the fields and buttons
        usernameField = findViewById(R.id.usernameField);
        emailField = findViewById(R.id.emailField2);
        passwordField = findViewById(R.id.passwordField);
        signupButton = findViewById(R.id.signupButton2);
        loginButton = findViewById(R.id.loginButton);

        // Set window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the signup button click listener
        signupButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Perform validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
            } else if (!isValidEmail(email)) {
                Toast.makeText(SignupActivity.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
            } else if (!isValidPassword(password)) {
                Toast.makeText(SignupActivity.this, "Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, and a special character!", Toast.LENGTH_SHORT).show();
            } else {
                // Simulate a signup success (you can replace this with actual signup logic)
                Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                // Optionally, proceed to the next activity after signup
                // Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                // startActivity(intent);
            }
        });

        // Set the login button click listener (navigate to the login screen)
        loginButton.setOnClickListener(v -> {
            // Optionally, navigate to the login screen if the user already has an account
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(SignupActivity.this, "Navigating to Login", Toast.LENGTH_SHORT).show();
        });
    }

    // Method to validate email format using Android's Patterns class
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Method to validate the password with specific rules using regular expressions
    private boolean isValidPassword(String password) {
        // Check if password is at least 8 characters long, contains upper, lower, and special character
        String passwordPattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}";
        return password.matches(passwordPattern);
    }
}
