package com.example.endure;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddWorkout extends AppCompatActivity {

    private EditText workoutName, repsAndSets, maxWeight;
    private Button addButton, goToHomeButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        // Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("workouts");

        // Initialize Views
        workoutName = findViewById(R.id.editTextText2);
        repsAndSets = findViewById(R.id.editTextText7);
        maxWeight = findViewById(R.id.editTextText8);
        addButton = findViewById(R.id.button);
        goToHomeButton = findViewById(R.id.goToHomeButton);  // New Button

        // UI Adjustment for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Add workout to Firebase
        addButton.setOnClickListener(v -> addWorkoutToDatabase());

        // Go to Home screen when the button is clicked
        goToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(AddWorkout.this, HomeScreen.class);
            startActivity(intent);  // Start HomeScreen activity
            finish();  // Close the current activity
        });
    }

    private void addWorkoutToDatabase() {
        // Get data from EditTexts
        String name = workoutName.getText().toString().trim();
        String reps = repsAndSets.getText().toString().trim();
        String weight = maxWeight.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(reps) || TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a workout object
        Workout workout = new Workout(name, reps, weight);

        // Push workout data to Firebase
        String workoutId = databaseReference.push().getKey();
        if (workoutId != null) {
            databaseReference.child(workoutId).setValue(workout)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddWorkout.this, "Workout added successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddWorkout.this, "Failed to add workout", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Model class for Workout
    public static class Workout {
        public String name;
        public String repsAndSets;
        public String maxWeight;

        public Workout(String name, String repsAndSets, String maxWeight) {
            this.name = name;
            this.repsAndSets = repsAndSets;
            this.maxWeight = maxWeight;
        }
    }
}
