package com.example.endure;

import java.util.concurrent.TimeUnit;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);

        // Trigger the NotificationWorker when the HomeScreen activity starts
        triggerNotificationWorker();

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the LogoutButton
        MaterialButton logoutButton = findViewById(R.id.LogoutButton);
        logoutButton.setOnClickListener(v -> {
            // Redirect to LoginActivity
            Intent intent = new Intent(HomeScreen.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close the current activity
        });

        // Find the Profile button
        MaterialButton profileButton = findViewById(R.id.ProfileButton);
        profileButton.setOnClickListener(v -> {
            // Redirect to ProfileActivity
            Intent intent = new Intent(HomeScreen.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Retrieve the username from the Intent
        String username = getIntent().getStringExtra("USERNAME");

        // Find the TextView for the welcome message
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView welcomeMessage = findViewById(R.id.welcomeMessage);
        welcomeMessage.setText("Welcome, " + username + "!"); // Display the username
    }

    private void triggerNotificationWorker() {
        // Add constraints for the Worker if needed (e.g., only run when connected to Wi-Fi)
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(false) // Modify based on your requirements
                .build();

        // Create the PeriodicWorkRequest with a 5-second repeat interval
        PeriodicWorkRequest notificationWorkRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build();

        // Enqueue the PeriodicWorkRequest
        WorkManager.getInstance(this).enqueue(notificationWorkRequest);
    }
}
