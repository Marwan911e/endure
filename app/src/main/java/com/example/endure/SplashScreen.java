package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    private static WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), SplashScreen::onApplyWindowInsets);

        // Find your ImageViews
        View imageView1 = findViewById(R.id.imageView);
        View imageView2 = findViewById(R.id.imageView2);

        // Apply animations
        animateImageView(imageView1, 0); // Start animating immediately
        animateImageView(imageView2, 500); // Start animating with a delay
    }

    private void animateImageView(View view, long startDelay) {
        // Fade in and scale up
        view.setAlpha(0f); // Start invisible
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1f);

        // Set animation properties
        fadeIn.setDuration(3000); // 3 second duration
        scaleX.setDuration(3000);
        scaleY.setDuration(3000);

        fadeIn.setStartDelay(startDelay); // Add delay
        scaleX.setStartDelay(startDelay);
        scaleY.setStartDelay(startDelay);

        fadeIn.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        // Start the animations
        fadeIn.start();
        scaleX.start();
        scaleY.start();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close Splash Screen
        }, 2000); // 2 seconds delay

    }
}

