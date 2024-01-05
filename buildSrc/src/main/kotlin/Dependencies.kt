import org.gradle.api.artifacts.dsl.DependencyHandler

object BuildVersions {
    object Android {
        const val minSdk = 26
        const val targetSdk = 34
    }
}

object Classpath {
    object Dependencies {
        internal object Versions {
            const val gradle = "8.1.1"
            const val kotlin = "1.8.0"
            const val hilt = "2.45"
            const val ktlint = "0.47.0"
            const val detekt = "1.14.2"
            const val navComp = "2.7.6"
        }

        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:8.0.2"
        const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    }

}

object Dependencies {
    object Versions {
        const val kotlin = Classpath.Dependencies.kotlin
    }

    object Build {
        const val kotlin =
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Classpath.Dependencies.Versions.kotlin}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"

    }

    object AndroidX {
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.2"
        const val fragment = "androidx.fragment:fragment-ktx:1.6.2"
        const val core = "androidx.core:core-ktx:1.9.0"
        const val constraint = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val appCompat = "androidx.appcompat:appcompat:1.6.0"
        const val material = "com.google.android.material:material:1.11.0"
    }

    object Navigation {
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Classpath.Dependencies.Versions.navComp}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Classpath.Dependencies.Versions.navComp}"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object Imaging {
        const val glideAnnotations = "com.github.bumptech.glide:annotations:4.13.2"
        const val glideCompiler = "com.github.bumptech.glide:compiler:4.12.0"
        const val glide = "com.github.bumptech.glide:glide:4.13.2"
        const val transformations = "jp.wasabeef:glide-transformations:4.3.0"
    }

    object Hilt {
        val hiltVersion = Classpath.Dependencies.Versions.hilt
        val android = "com.google.dagger:hilt-android:$hiltVersion"
        val kapt = "com.google.dagger:hilt-compiler:$hiltVersion"
    }

    object Room {
        val room = "2.5.1"
        val compiler = "androidx.room:room-compiler:$room"
        val runtime = "androidx.room:room-runtime:$room"
        val kts = "androidx.room:room-ktx:$room"
    }

    object Google {
        val places = "com.google.android.libraries.places:places:3.3.0"
        val maps = "com.google.android.gms:play-services-maps:18.1.0"
        val location = "com.google.maps:google-maps-services:0.18.0"
        val mapsKtx = "com.google.maps.android:maps-ktx:3.2.1"
        val placesKtx = "com.google.maps.android:places-ktx:0.4.0"
    }
}

fun DependencyHandler.glide() {
    implementation(Dependencies.Imaging.glide)
    implementation(Dependencies.Imaging.glideAnnotations)
    implementation(Dependencies.Imaging.transformations)
    kapt(Dependencies.Imaging.glideCompiler)
}

fun DependencyHandler.maps() {
    implementation(Dependencies.Google.places)
    implementation(Dependencies.Google.maps)
    implementation(Dependencies.Google.location)
    implementation(Dependencies.Google.mapsKtx)
    implementation(Dependencies.Google.placesKtx)
}

fun DependencyHandler.hilt() {
    api(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.kapt)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.interceptor)
    implementation(Dependencies.Square.gsonConverter)
}

fun DependencyHandler.room() {
    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.kts)
    kapt(Dependencies.Room.compiler)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)
}

fun DependencyHandler.debugImplementation(depName: String) {
    add("debugImplementation", depName)
}

fun DependencyHandler.releaseImplementation(depName: String) {
    add("releaseImplementation", depName)
}

fun DependencyHandler.testImplementation(depName: String) {
    add("testImplementation", depName)
}

fun DependencyHandler.androidTestImplementation(depName: String) {
    add("androidTestImplementation", depName)
}

fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}

private fun DependencyHandler.ksp(depName: String) {
    add("ksp", depName)
}

private fun DependencyHandler.api(depName: String) {
    add("api", depName)
}
