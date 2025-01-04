package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailField;
    private Button submitButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth and UI components
        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.emailField);
        submitButton = findViewById(R.id.submitButton);

        // Set onClickListener for submit button
        submitButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();

            // Validate email input
            if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(ForgotPasswordActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send password reset email
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Show success message
                            Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();

                            // Redirect to LoginActivity
                            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Close the ForgotPasswordActivity
                        } else {
                            // Show error message
                            Toast.makeText(ForgotPasswordActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
