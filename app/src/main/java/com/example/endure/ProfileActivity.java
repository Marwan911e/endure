package com.example.endure;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private EditText nameField, addressField, ageField, heightField, weightField, targetWeightField;
    private Button saveButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Check if the user is authenticated
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if the user is not authenticated
            return;
        }

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Find views
        nameField = findViewById(R.id.nameField);
        addressField = findViewById(R.id.addressField);
        ageField = findViewById(R.id.ageField);
        heightField = findViewById(R.id.heightField);
        weightField = findViewById(R.id.weightField);
        targetWeightField = findViewById(R.id.targetWeightField);
        saveButton = findViewById(R.id.saveButton);

        // Load existing user data from Firebase
        loadUserData();

        // Handle Save button click
        saveButton.setOnClickListener(v -> saveUserData());
    }

    private void loadUserData() {
        String userId = auth.getCurrentUser().getUid();

        databaseReference.child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                // Populate fields with user data
                nameField.setText(task.getResult().child("name").getValue(String.class));
                addressField.setText(task.getResult().child("address").getValue(String.class));
                ageField.setText(task.getResult().child("age").getValue(String.class));
                heightField.setText(task.getResult().child("height").getValue(String.class));
                weightField.setText(task.getResult().child("weight").getValue(String.class));
                targetWeightField.setText(task.getResult().child("targetWeight").getValue(String.class));
            } else {
                Toast.makeText(this, "No user data found.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to load data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    private void saveUserData() {
        // Validate inputs
        String name = nameField.getText().toString().trim();
        String address = addressField.getText().toString().trim();
        String age = ageField.getText().toString().trim();
        String height = heightField.getText().toString().trim();
        String weight = weightField.getText().toString().trim();
        String targetWeight = targetWeightField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(address) || TextUtils.isEmpty(age) ||
                TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(targetWeight)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a UserProfile object
        UserProfile userProfile = new UserProfile(name, address, age, height, weight, targetWeight);

        // Save data to Firebase
        String userId = auth.getCurrentUser().getUid();
        databaseReference.child(userId).setValue(userProfile).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Failed to save profile: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Error saving data: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    // Define a model class for user profile
    public static class UserProfile {
        public String name, address, age, height, weight, targetWeight;

        public UserProfile() {
            // Default constructor required for calls to DataSnapshot.getValue(UserProfile.class)
        }

        public UserProfile(String name, String address, String age, String height, String weight, String targetWeight) {
            this.name = name;
            this.address = address;
            this.age = age;
            this.height = height;
            this.weight = weight;
            this.targetWeight = targetWeight;
        }
    }
}
