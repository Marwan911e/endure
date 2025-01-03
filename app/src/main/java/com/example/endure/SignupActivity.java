package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameField, emailField, passwordField;
    private MaterialButton signupButton, loginButton;
    private FirebaseAuth mAuth; // FirebaseAuth instance
    private FirebaseDatabase database; // Firebase Realtime Database instance
    private DatabaseReference usersRef; // Reference to "users" node in Firebase Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        // Initialize FirebaseAuth and Firebase Database
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");

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
                // Firebase sign up
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Create a new user object with the necessary details
                                    User newUser = new User(username, email);

                                    // Save the user data in Firebase Database under "users" node
                                    usersRef.child(user.getUid()).setValue(newUser)
                                            .addOnCompleteListener(databaseTask -> {
                                                if (databaseTask.isSuccessful()) {
                                                    Toast.makeText(SignupActivity.this, "Signup successful!", Toast.LENGTH_SHORT).show();
                                                    // Navigate to the next activity
                                                    Intent intent = new Intent(SignupActivity.this, HomeScreen.class);
                                                    intent.putExtra("USERNAME", username); // Pass the username
                                                    startActivity(intent);
                                                    finish(); // Close SignupActivity
                                                } else {
                                                    Toast.makeText(SignupActivity.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else {
                                    Toast.makeText(SignupActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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
