plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
}

dependencies {
    // android gradle plugin, required by custom plugin
    implementation("com.android.tools.build:gradle:8.0.2")

    // kotlin plugin, required by custom plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.45")
}
