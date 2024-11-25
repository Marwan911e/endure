package com.example.endure;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the LogoutButton
        MaterialButton logoutButton = findViewById(R.id.LogoutButton);

        // Set up the OnClickListener for the logout button
        logoutButton.setOnClickListener(v -> {
            // Redirect to LoginActivity
            Intent intent = new Intent(HomeScreen.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        // Retrieve the username from the Intent
        String username = getIntent().getStringExtra("USERNAME");

        // Find the TextView for the welcome message
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("Welcome, " + username + "!"); // Display the username
    }
}


