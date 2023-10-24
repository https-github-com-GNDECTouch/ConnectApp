@file:Suppress("UNUSED_EXPRESSION", "UNUSED_EXPRESSION")

plugins {
    id("com.android.application")
    //apply plugin of realm
    id("realm-android")
}
//for mongodb realmio
//apply
realm{
    isSyncEnabled=true;
}
android {
    namespace = "com.example.gndectouch"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.gndectouch"
        minSdk= 19
        targetSdk= 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding=true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.mongodb:mongodb-driver-sync:4.4.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-inappmessaging:20.3.5")
    testImplementation("junit:junit:4.13.2")
    implementation("com.opencsv:opencsv:5.5")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0");
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0");

}