import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.composefusion"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.composefusion"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        val timeStamp = LocalDateTime.now()
            .format(
                DateTimeFormatter
                    .ofPattern(
                        "dd-MMM-yyyy hh-mm a",
                        Locale.getDefault()
                    )
            )

        val nameApk = "Compose Fusion App $timeStamp"
        setProperty("archivesBaseName", nameApk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)


    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose)
    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Material theme 3
    implementation(libs.androidx.material3)
    // Material theme 3 window size class
    implementation(libs.androidx.material3.window.size.class1)
    // Material theme 3 adaptive navigation suite
    implementation(libs.androidx.material3.adaptive.navigation.suite)

    // Koin for Android
    implementation(libs.koin.androidx.compose)

    // room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}