plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    compileSdk = BuildVersions.Android.targetSdk

    defaultConfig {
        minSdk = BuildVersions.Android.minSdk
        @Suppress("DEPRECATION")
        targetSdk = BuildVersions.Android.targetSdk
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(Dependencies.Build.kotlin)
    implementation(Dependencies.Build.coroutines)

    hilt()
}
