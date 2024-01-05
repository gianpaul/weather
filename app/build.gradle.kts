@file:Suppress("UnstableApiUsage")
import com.android.build.api.dsl.ApplicationProductFlavor

plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.application")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.weathercoroutines"
    compileSdk = BuildVersions.Android.targetSdk

    defaultConfig {
        applicationId = "com.example.weathercoroutines"
        minSdk = BuildVersions.Android.minSdk
        targetSdk = BuildVersions.Android.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        addManifestPlaceholders(mapOf("MAPS_API_KEY" to ""))
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

    viewBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("production") {
            setupCommonFields(GradleProperties.ProjectProperties.PROD)
        }

        create("dev") {
            setupCommonFields(GradleProperties.ProjectProperties.DEV)
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

fun ApplicationProductFlavor.setupCommonFields(props: GradleProperties.ProjectProperties) {
    fun buildConfigFieldFrom(key: String, type: String = "String") {
        return buildConfigField(type, key, "\"${props.get(key)!!}\"")
    }

    fun resValueFieldFrom(key: String, type: String = "string") {
        return resValue(type, key, "\"${props.get(key)!!}\"")
    }

    if (props.exists()) {
        buildConfigFieldFrom("API_KEY")
        buildConfigFieldFrom("API_URL")
        buildConfigFieldFrom("MAPS_API_KEY")
        addManifestPlaceholders(mapOf("MAPS_API_KEY" to props.get("MAPS_API_KEY") as String))
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(Dependencies.AndroidX.fragment)
    implementation(Dependencies.AndroidX.viewModel)
    implementation(Dependencies.AndroidX.lifecycleExtensions)
    implementation(Dependencies.AndroidX.lifecycleViewmodel)
    implementation(Dependencies.AndroidX.liveData)
    implementation(Dependencies.AndroidX.material)
    navigation()
    hilt()
    maps()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}