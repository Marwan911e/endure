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
# Firebase Integration for Android App

This guide will walk you through setting up Firebase in your Android project and running the app with Firebase Authentication, Realtime Database, and Cloud Messaging.

## Prerequisites
- Android Studio installed on your machine.
- A Firebase project set up in the Firebase console.

## Step 1: Install Android Studio
If you haven’t already, install Android Studio by following the [official installation guide](https://developer.android.com/studio).

## Step 2: Set up Firebase for your project
Follow these instructions to set up Firebase for your Android project:
1. Go to the [Firebase Console](https://console.firebase.google.com/).
2. Create a new Firebase project or select an existing one.
3. Add your Android app to the Firebase project by registering your app's package name.
4. Enable the following Firebase services:
   - Firebase Authentication
   - Firebase Realtime Database
   - Firebase Cloud Messaging

## Step 3: Configure Firebase in the App

### 1. Download the `google-services.json` file
- Go to the Firebase console, navigate to your project settings, and download the `google-services.json` file.
- Place the `google-services.json` file in the `app/` folder of your Android project.

### 2. Update the `build.gradle` files

#### Project-level `build.gradle` (located in the root of your project)

```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add the following classpath in the dependencies block
        classpath 'com.google.gms:google-services:4.3.15' 
    }
}

