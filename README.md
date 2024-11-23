# Endure - Your Journey to Strength and Resilience 💪

Welcome to **Endure**, a mobile app designed to help you stay motivated, track progress, and build resilience. Whether you're focused on fitness, personal growth, or overcoming life's challenges, **Endure** is your companion every step of the way.

## 🌟 Features
- **Goal Tracking**: Set and track your personal goals.
- **Real-time Sync**: Seamless progress sync with Firebase across devices.
- **Motivational Reminders**: Daily push notifications to keep you on track.
- **Personalized Insights**: Get personalized tips and insights based on your activity.
- **Community Engagement**: Connect and share with a community of like-minded individuals.

## 🛠️ Technologies Used
- **Java**: Core programming language for Android development.
- **Firebase**: Real-time database, authentication, and cloud messaging.
- **Android Studio**: Official IDE for building Android applications.

## 🚀 Getting Started
To get started with **Endure**, follow the steps below:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/marwan911e/endure.git
Set up your environment:

Install Android Studio if you haven’t already.
Set up Firebase for your project by following the instructions on the Firebase console. Make sure to enable Firebase Authentication, Realtime Database, and Cloud Messaging.
Configure Firebase in the app:

Download the google-services.json file from the Firebase console and place it in the app/ folder of your project.
Add the Firebase SDK to your build.gradle files:
In the project-level build.gradle:
gradle
Copy code
classpath 'com.google.gms:google-services:4.3.15'
In the app-level build.gradle:
gradle
Copy code
apply plugin: 'com.google.gms.google-services'
dependencies {
    implementation 'com.google.firebase:firebase-auth:21.1.0'
    implementation 'com.google.firebase:firebase-database:20.2.3'
    implementation 'com.google.firebase:firebase-messaging:23.0.0'
}
Run the app:

Open the project in Android Studio.
Build and run the app on an emulator or physical device.
You will be prompted to sign in with your Firebase credentials to sync data across devices.
